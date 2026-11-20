package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class RequestSpeciesListPacket(val requestId: Long) :
    CobblemonSmartphoneNetworkPacket<RequestSpeciesListPacket> {
    companion object {
        val ID = smartphoneResource("request_species_list")
        fun decode(buffer: FriendlyByteBuf) = RequestSpeciesListPacket(buffer.readVarLong())
    }

    override val id = ID
    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarLong(requestId)
    }
}
