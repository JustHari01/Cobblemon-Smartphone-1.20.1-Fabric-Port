package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

/**
 * Requests deletion of a post. The server authorises: the author may delete their own post,
 * operators may delete any. The client never decides.
 */
class DeletePostPacket(val postId: Long) : CobblemonSmartphoneNetworkPacket<DeletePostPacket> {
    companion object {
        val ID = smartphoneResource("delete_post")
        fun decode(buffer: FriendlyByteBuf) = DeletePostPacket(buffer.readVarLong())
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarLong(postId)
    }
}
