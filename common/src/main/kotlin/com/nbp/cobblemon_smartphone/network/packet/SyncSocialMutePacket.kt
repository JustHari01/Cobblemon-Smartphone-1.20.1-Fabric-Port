package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

/** Server -> client: the player's persisted Social Do Not Disturb flag, sent on join. */
class SyncSocialMutePacket(val muted: Boolean) : CobblemonSmartphoneNetworkPacket<SyncSocialMutePacket> {
    companion object {
        val ID = smartphoneResource("sync_social_mute")
        fun decode(buffer: FriendlyByteBuf) = SyncSocialMutePacket(buffer.readBoolean())
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeBoolean(muted)
    }
}
