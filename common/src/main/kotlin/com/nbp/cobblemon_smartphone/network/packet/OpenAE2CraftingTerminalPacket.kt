package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class OpenAE2CraftingTerminalPacket : CobblemonSmartphoneNetworkPacket<OpenAE2CraftingTerminalPacket> {
    companion object {
        val ID = smartphoneResource("open_ae2_crafting_terminal")
        fun decode(buffer: FriendlyByteBuf): OpenAE2CraftingTerminalPacket = OpenAE2CraftingTerminalPacket()
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        // No payload required.
    }
}
