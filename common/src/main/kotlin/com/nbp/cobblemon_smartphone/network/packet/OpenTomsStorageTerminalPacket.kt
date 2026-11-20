package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class OpenTomsStorageTerminalPacket : CobblemonSmartphoneNetworkPacket<OpenTomsStorageTerminalPacket> {
    companion object {
        val ID = smartphoneResource("open_toms_storage_terminal")
        fun decode(buffer: FriendlyByteBuf): OpenTomsStorageTerminalPacket = OpenTomsStorageTerminalPacket()
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        // No payload required.
    }
}
