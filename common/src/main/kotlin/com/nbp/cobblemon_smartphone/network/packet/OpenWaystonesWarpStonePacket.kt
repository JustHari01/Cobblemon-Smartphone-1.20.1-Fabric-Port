package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.CobblemonSmartphone
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

class OpenWaystonesWarpStonePacket : CobblemonSmartphoneNetworkPacket<OpenWaystonesWarpStonePacket> {

    companion object {
        val ID = ResourceLocation(CobblemonSmartphone.ID, "open_waystones_warp_stone")
        fun decode(buffer: FriendlyByteBuf): OpenWaystonesWarpStonePacket = OpenWaystonesWarpStonePacket()
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        // No payload required.
    }
}
