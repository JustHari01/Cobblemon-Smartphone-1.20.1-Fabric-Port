package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class DefuseRotomPacket : CobblemonSmartphoneNetworkPacket<DefuseRotomPacket> {
    companion object {
        val ID = smartphoneResource("defuse_rotom")

        fun decode(buffer: FriendlyByteBuf): DefuseRotomPacket {
            return DefuseRotomPacket()
        }
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
    }
}
