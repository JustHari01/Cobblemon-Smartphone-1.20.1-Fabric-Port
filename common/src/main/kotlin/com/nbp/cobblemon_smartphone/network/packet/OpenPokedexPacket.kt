package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.item.SmartphoneColor
import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class OpenPokedexPacket(val color: SmartphoneColor) : CobblemonSmartphoneNetworkPacket<OpenPokedexPacket> {
    companion object {
        val ID = smartphoneResource("open_pokedex")
        fun decode(buffer: FriendlyByteBuf): OpenPokedexPacket =
            OpenPokedexPacket(buffer.readEnum(SmartphoneColor::class.java))
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeEnum(color)
    }
}
