package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class SaveThemePacket(val themeId: String) : CobblemonSmartphoneNetworkPacket<SaveThemePacket> {
    companion object {
        val ID = smartphoneResource("save_theme")
        private const val MAX_STRING_LENGTH = 256

        fun decode(buffer: FriendlyByteBuf): SaveThemePacket {
            val themeId = buffer.readUtf(MAX_STRING_LENGTH)
            return SaveThemePacket(themeId)
        }
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUtf(themeId, MAX_STRING_LENGTH)
    }
}
