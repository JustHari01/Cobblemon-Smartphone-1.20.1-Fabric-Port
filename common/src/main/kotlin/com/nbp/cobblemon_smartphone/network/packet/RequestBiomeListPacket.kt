package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class RequestBiomeListPacket(val requestId: Long) :
    CobblemonSmartphoneNetworkPacket<RequestBiomeListPacket> {
    companion object {
        val ID = smartphoneResource("request_biome_list")
        fun decode(buffer: FriendlyByteBuf) = RequestBiomeListPacket(buffer.readVarLong())
    }

    override val id = ID
    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarLong(requestId)
    }
}
