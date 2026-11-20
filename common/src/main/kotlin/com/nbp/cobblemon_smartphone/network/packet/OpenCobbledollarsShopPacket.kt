package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class OpenCobbledollarsShopPacket : CobblemonSmartphoneNetworkPacket<OpenCobbledollarsShopPacket> {
    companion object {
        val ID = smartphoneResource("open_cobbledollars_shop")
        fun decode(buffer: FriendlyByteBuf): OpenCobbledollarsShopPacket = OpenCobbledollarsShopPacket()
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        // No payload required.
    }
}