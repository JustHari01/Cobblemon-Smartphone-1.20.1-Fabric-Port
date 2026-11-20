package com.nbp.cobblemon_smartphone.client.scanner

import com.nbp.cobblemon_smartphone.item.SmartphoneColor
import com.nbp.cobblemon_smartphone.network.packet.CompletePokedexScanPacket
import com.nbp.cobblemon_smartphone.network.packet.StartPokedexScanPacket
import com.nbp.cobblemon_smartphone.util.RotomScannerHelper
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component

/** Client side replacement for Cobblemon's scanner context, which was added after 1.20.1. */
object ScannerManager {
    private const val SCAN_DURATION_TICKS = 60

    var isActive: Boolean = false
        private set
    private var ticksActive: Int = 0
    private var targetEntityId: Int? = null
    private var lastCompletedTargetId: Int? = null
    private var ticksSinceMessage = 0
    var wantClose: Boolean = false
        private set
    var color: SmartphoneColor = SmartphoneColor.RED
        private set

    fun activate(color: SmartphoneColor = SmartphoneColor.RED) {
        if (isActive) return
        isActive = true
        this.color = color
        ticksActive = 0
        targetEntityId = null
        lastCompletedTargetId = null
        ticksSinceMessage = 0
        wantClose = false
    }

    fun deactivate() {
        if (isActive) wantClose = true
    }

    fun finishDeactivation() {
        isActive = false
        wantClose = false
        ticksActive = 0
        targetEntityId = null
        lastCompletedTargetId = null
    }

    fun tick(player: LocalPlayer) {
        if (!isActive) return
        if (wantClose) {
            finishDeactivation()
            return
        }

        val target = RotomScannerHelper.findTargetedPokemon(player, 10.0)
        if (target == null) {
            targetEntityId = null
            lastCompletedTargetId = null
            ticksActive = 0
            showActionBar(player, "Aim at a Pokémon and hold attack to scan")
            return
        }

        if (target.id != targetEntityId) {
            targetEntityId = target.id
            ticksActive = 0
        }

        if (target.id == lastCompletedTargetId) {
            showActionBar(player, "${target.pokemon.species.name} — scan complete")
            return
        }

        if (!Minecraft.getInstance().options.keyAttack.isDown) {
            ticksActive = 0
            showActionBar(player, "${target.pokemon.species.name} — hold attack to scan")
            return
        }

        if (ticksActive == 0) StartPokedexScanPacket(target.id).sendToServer()
        ticksActive++
        val progress = (ticksActive * 100 / SCAN_DURATION_TICKS).coerceAtMost(100)
        showActionBar(player, "Scanning ${target.pokemon.species.name} — $progress%")
        if (ticksActive >= SCAN_DURATION_TICKS) {
            CompletePokedexScanPacket(target.id).sendToServer()
            lastCompletedTargetId = target.id
        }
    }

    private fun showActionBar(player: LocalPlayer, text: String) {
        if (ticksSinceMessage++ % 4 == 0) player.displayClientMessage(Component.literal(text), true)
    }

    fun isInUse(): Boolean = isActive && !wantClose
}
