package com.nbp.cobblemon_smartphone.network.handler
import com.nbp.cobblemon_smartphone.util.withColor

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import com.nbp.cobblemon_smartphone.CobblemonSmartphone
import com.nbp.cobblemon_smartphone.item.SmartphoneColor
import com.nbp.cobblemon_smartphone.network.packet.OpenPokedexPacket
import com.nbp.cobblemon_smartphone.network.packet.OpenPokeInfoScreenPacket
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import java.util.UUID

object OpenPokedexHandler : ServerNetworkPacketHandler<OpenPokedexPacket> {
    private val buttonCooldowns = mutableMapOf<UUID, Long>()

    override fun handle(packet: OpenPokedexPacket, server: MinecraftServer, player: ServerPlayer) {
        server.execute {
            execute(player, packet.color, isNativeAction = true)
        }
    }

    fun execute(player: ServerPlayer, color: SmartphoneColor, isNativeAction: Boolean) {
        // Feature toggle and cooldown only apply to the native smartphone action;
        // datapack actions are independent and manage their own cooldown/requirements.
        if (isNativeAction && !CobblemonSmartphone.config.features.enablePokedex) {
            player.displayClientMessage(
                Component.translatable("message.nbp.pokedex.disabled").withColor(0xfd0100),
                true
            )
            return
        }

        if (isNativeAction) {
            val cooldown = CobblemonSmartphone.config.cooldowns.pokedexButton
            val now = System.currentTimeMillis()
            val lastClick = buttonCooldowns[player.uuid] ?: 0L

            if (now - lastClick < cooldown * 1000L) {
                val remaining = ((cooldown * 1000L - (now - lastClick)) / 1000L).toInt() + 1
                player.displayClientMessage(
                    Component.translatable("message.nbp.pokedex.cooldown", remaining).withColor(0xfd0100),
                    true
                )
                return
            }

            if (!com.nbp.cobblemon_smartphone.energy.SmartphoneEnergyHelper.checkAndConsumeEnergy(player, "cobblemon_smartphone:pokedex")) {
                return
            }

            buttonCooldowns[player.uuid] = now
        }

        OpenPokeInfoScreenPacket().sendToPlayer(player)
    }
}
