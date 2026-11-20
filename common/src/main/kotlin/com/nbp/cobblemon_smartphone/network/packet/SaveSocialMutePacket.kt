package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

/** Client -> server: persist the player's Social Do Not Disturb flag. */
class SaveSocialMutePacket(val muted: Boolean) : CobblemonSmartphoneNetworkPacket<SaveSocialMutePacket> {
    companion object {
        val ID = smartphoneResource("save_social_mute")
        fun decode(buffer: FriendlyByteBuf) = SaveSocialMutePacket(buffer.readBoolean())
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeBoolean(muted)
    }
}
