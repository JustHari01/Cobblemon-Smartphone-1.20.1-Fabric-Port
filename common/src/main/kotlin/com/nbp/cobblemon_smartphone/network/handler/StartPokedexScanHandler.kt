package com.nbp.cobblemon_smartphone.network.handler

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.nbp.cobblemon_smartphone.CobblemonSmartphone
import com.nbp.cobblemon_smartphone.network.packet.StartPokedexScanPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object StartPokedexScanHandler : ServerNetworkPacketHandler<StartPokedexScanPacket> {
    private const val MAX_SCAN_DISTANCE_SQUARED = 100.0
    private const val SESSION_TTL_MS = 15_000L
    private val sessions = ConcurrentHashMap<UUID, Session>()

    private data class Session(val targetEntityId: Int, val startedAt: Long)

    override fun handle(packet: StartPokedexScanPacket, server: MinecraftServer, player: ServerPlayer) {
        server.execute {
            val now = System.currentTimeMillis()
            sessions.entries.removeIf { now - it.value.startedAt > SESSION_TTL_MS }
            if (!CobblemonSmartphone.config.features.enableScanner) return@execute

            val target = player.serverLevel().getEntity(packet.targetEntityId) as? PokemonEntity ?: return@execute
            if (!isValidTarget(player, target)) return@execute
            sessions[player.uuid] = Session(target.id, now)
        }
    }

    fun consumeIfComplete(player: ServerPlayer, targetEntityId: Int): Boolean {
        val session = sessions.remove(player.uuid) ?: return false
        if (session.targetEntityId != targetEntityId) return false
        if (System.currentTimeMillis() - session.startedAt < 2_500L) return false
        val target = player.serverLevel().getEntity(targetEntityId) as? PokemonEntity ?: return false
        return isValidTarget(player, target)
    }

    private fun isValidTarget(player: ServerPlayer, target: PokemonEntity): Boolean =
        target.isAlive && !target.pokemon.isFainted() &&
            player.distanceToSqr(target) <= MAX_SCAN_DISTANCE_SQUARED && player.hasLineOfSight(target)
}
