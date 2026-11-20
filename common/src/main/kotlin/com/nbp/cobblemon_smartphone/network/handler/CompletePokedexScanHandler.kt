package com.nbp.cobblemon_smartphone.network.handler

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.nbp.cobblemon_smartphone.CobblemonSmartphone
import com.nbp.cobblemon_smartphone.network.packet.CompletePokedexScanPacket
import com.nbp.cobblemon_smartphone.social.SmartphoneDexData
import com.nbp.cobblemon_smartphone.util.withColor
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

object CompletePokedexScanHandler : ServerNetworkPacketHandler<CompletePokedexScanPacket> {
    override fun handle(packet: CompletePokedexScanPacket, server: MinecraftServer, player: ServerPlayer) {
        server.execute {
            if (!CobblemonSmartphone.config.features.enableScanner) {
                player.displayClientMessage(
                    Component.translatable("message.nbp.scanner.disabled").withColor(0xfd0100),
                    true
                )
                return@execute
            }

            if (!StartPokedexScanHandler.consumeIfComplete(player, packet.targetEntityId)) return@execute
            val target = player.serverLevel().getEntity(packet.targetEntityId) as? PokemonEntity ?: return@execute

            val config = CobblemonSmartphone.config.rotom
            val dex = SmartphoneDexData.get(server)
            val isNew = !dex.hasSeen(player.uuid, target.pokemon.species.resourceIdentifier)
            if (isNew && config.registerUnregisteredToDex) {
                dex.recordEncounter(
                    player,
                    target.pokemon.species.resourceIdentifier,
                    caught = target.owner === player
                )
            }

            val message = if (isNew && config.registerUnregisteredToDex) {
                Component.translatable("message.cobblemon_smartphone.rotom_scan.registered", target.pokemon.species.name)
                    .withStyle(ChatFormatting.AQUA)
            } else {
                Component.literal("${target.pokemon.species.name} — Lv. ${target.pokemon.level}")
                    .withStyle(ChatFormatting.AQUA)
            }
            player.displayClientMessage(message, true)
        }
    }
}
