package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class CompletePokedexScanPacket(val targetEntityId: Int) :
    CobblemonSmartphoneNetworkPacket<CompletePokedexScanPacket> {
    companion object {
        val ID = smartphoneResource("complete_pokedex_scan")
        fun decode(buffer: FriendlyByteBuf) = CompletePokedexScanPacket(buffer.readVarInt())
    }

    override val id = ID
    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarInt(targetEntityId)
    }
}
