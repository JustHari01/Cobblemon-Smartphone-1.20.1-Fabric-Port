package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class OpenEnderChestPacket : CobblemonSmartphoneNetworkPacket<OpenEnderChestPacket> {
    companion object {
        val ID = smartphoneResource("open_enderchest")
        fun decode(buffer: FriendlyByteBuf): OpenEnderChestPacket = OpenEnderChestPacket()
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        // Nenhum dado adicional é enviado
    }
}
