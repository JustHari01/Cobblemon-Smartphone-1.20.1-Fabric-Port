package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class RequestStructureListPacket(val requestId: Long) :
    CobblemonSmartphoneNetworkPacket<RequestStructureListPacket> {
    companion object {
        val ID = smartphoneResource("request_structure_list")
        fun decode(buffer: FriendlyByteBuf) = RequestStructureListPacket(buffer.readVarLong())
    }

    override val id = ID
    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarLong(requestId)
    }
}
