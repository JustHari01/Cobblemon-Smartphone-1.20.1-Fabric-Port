package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class StartPokedexScanPacket(val targetEntityId: Int) :
    CobblemonSmartphoneNetworkPacket<StartPokedexScanPacket> {
    companion object {
        val ID = smartphoneResource("start_pokedex_scan")
        fun decode(buffer: FriendlyByteBuf) = StartPokedexScanPacket(buffer.readVarInt())
    }

    override val id = ID
    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarInt(targetEntityId)
    }
}
