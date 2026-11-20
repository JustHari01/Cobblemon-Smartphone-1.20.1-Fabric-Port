package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class OpenCobblenavPokenavPacket : CobblemonSmartphoneNetworkPacket<OpenCobblenavPokenavPacket> {
    companion object {
        val ID = smartphoneResource("open_cobblenav_pokenav")
        fun decode(buffer: FriendlyByteBuf): OpenCobblenavPokenavPacket = OpenCobblenavPokenavPacket()
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        // No payload needed
    }
}