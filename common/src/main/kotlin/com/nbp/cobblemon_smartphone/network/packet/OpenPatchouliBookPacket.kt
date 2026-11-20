package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class OpenPatchouliBookPacket(val bookId: String) : CobblemonSmartphoneNetworkPacket<OpenPatchouliBookPacket> {
    companion object {
        val ID = smartphoneResource("open_patchouli_book")

        fun decode(buffer: FriendlyByteBuf): OpenPatchouliBookPacket {
            return OpenPatchouliBookPacket(buffer.readUtf(128))
        }
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUtf(bookId, 128)
    }
}
