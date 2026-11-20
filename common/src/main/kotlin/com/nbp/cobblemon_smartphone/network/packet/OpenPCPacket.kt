package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.network.packet.CobblemonSmartphoneNetworkPacket
import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class OpenPCPacket : CobblemonSmartphoneNetworkPacket<OpenPCPacket> {
    companion object {
        val ID = smartphoneResource("open_pc")
        fun decode(buffer: FriendlyByteBuf): OpenPCPacket = OpenPCPacket()
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        // Nenhum dado adicional é enviado
    }
}
