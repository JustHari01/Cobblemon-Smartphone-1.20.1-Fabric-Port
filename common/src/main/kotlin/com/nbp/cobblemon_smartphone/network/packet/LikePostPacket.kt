package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class LikePostPacket(val postId: Long, val liked: Boolean) : CobblemonSmartphoneNetworkPacket<LikePostPacket> {
    companion object {
        val ID = smartphoneResource("like_post")
        fun decode(buffer: FriendlyByteBuf) = LikePostPacket(buffer.readVarLong(), buffer.readBoolean())
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarLong(postId)
        buffer.writeBoolean(liked)
    }
}
