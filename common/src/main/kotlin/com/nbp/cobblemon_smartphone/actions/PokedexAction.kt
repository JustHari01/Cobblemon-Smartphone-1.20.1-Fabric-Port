package com.nbp.cobblemon_smartphone.actions

import com.cobblemon.mod.common.CobblemonSounds
import com.nbp.cobblemon_smartphone.CobblemonSmartphone
import com.nbp.cobblemon_smartphone.api.SmartphoneAction
import com.nbp.cobblemon_smartphone.item.SmartphoneColor
import com.nbp.cobblemon_smartphone.network.packet.OpenPokedexPacket
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation

object PokedexAction : SmartphoneAction {
    override val id = "${CobblemonSmartphone.ID}:pokedex"
    override val texture = ResourceLocation(CobblemonSmartphone.ID, "textures/gui/buttons/pokedex.png")
    override val hoverTexture = ResourceLocation(CobblemonSmartphone.ID, "textures/gui/buttons/pokedex_hover.png")

    var requestedColor: SmartphoneColor = SmartphoneColor.RED

    override fun onClick() {
        val player = Minecraft.getInstance().player ?: return
        player.playSound(CobblemonSounds.GUI_CLICK, 0.5f, 1f)
        OpenPokedexPacket(requestedColor).sendToServer()
        Minecraft.getInstance().setScreen(null)
    }

    override fun isEnabled(): Boolean {
        return CobblemonSmartphone.config.features.enablePokedex
    }
}
