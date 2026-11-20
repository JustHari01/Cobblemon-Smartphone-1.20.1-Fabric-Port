package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class OpenAE2TerminalPacket : CobblemonSmartphoneNetworkPacket<OpenAE2TerminalPacket> {
    companion object {
        val ID = smartphoneResource("open_ae2_terminal")
        fun decode(buffer: FriendlyByteBuf): OpenAE2TerminalPacket = OpenAE2TerminalPacket()
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        // No payload required.
    }
}
