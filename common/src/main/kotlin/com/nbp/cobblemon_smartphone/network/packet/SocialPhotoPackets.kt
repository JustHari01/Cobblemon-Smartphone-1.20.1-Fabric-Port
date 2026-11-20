package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf
import java.util.UUID

class UploadSocialPhotoPacket(
    val photoId: UUID,
    val totalBytes: Int,
    val offset: Int,
    val data: ByteArray
) : CobblemonSmartphoneNetworkPacket<UploadSocialPhotoPacket> {
    companion object {
        const val MAX_CHUNK_BYTES = 24 * 1024
        val ID = smartphoneResource("upload_social_photo")
        fun decode(buffer: FriendlyByteBuf) = UploadSocialPhotoPacket(
            buffer.readUUID(), buffer.readVarInt(), buffer.readVarInt(), buffer.readByteArray(MAX_CHUNK_BYTES)
        )
    }
    override val id = ID
    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUUID(photoId)
        buffer.writeVarInt(totalBytes)
        buffer.writeVarInt(offset)
        buffer.writeByteArray(data)
    }
}

class SocialPhotoUploadResultPacket(
    val photoId: UUID,
    val success: Boolean,
    val width: Int,
    val height: Int
) : CobblemonSmartphoneNetworkPacket<SocialPhotoUploadResultPacket> {
    companion object {
        val ID = smartphoneResource("social_photo_upload_result")
        fun decode(buffer: FriendlyByteBuf) = SocialPhotoUploadResultPacket(
            buffer.readUUID(), buffer.readBoolean(), buffer.readVarInt(), buffer.readVarInt()
        )
    }
    override val id = ID
    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUUID(photoId)
        buffer.writeBoolean(success)
        buffer.writeVarInt(width)
        buffer.writeVarInt(height)
    }
}

class RequestSocialPhotoPacket(val photoId: UUID) : CobblemonSmartphoneNetworkPacket<RequestSocialPhotoPacket> {
    companion object {
        val ID = smartphoneResource("request_social_photo")
        fun decode(buffer: FriendlyByteBuf) = RequestSocialPhotoPacket(buffer.readUUID())
    }
    override val id = ID
    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUUID(photoId)
    }
}

class SocialPhotoChunkPacket(
    val photoId: UUID,
    val totalBytes: Int,
    val offset: Int,
    val data: ByteArray
) : CobblemonSmartphoneNetworkPacket<SocialPhotoChunkPacket> {
    companion object {
        val ID = smartphoneResource("social_photo_chunk")
        fun decode(buffer: FriendlyByteBuf) = SocialPhotoChunkPacket(
            buffer.readUUID(), buffer.readVarInt(), buffer.readVarInt(), buffer.readByteArray(UploadSocialPhotoPacket.MAX_CHUNK_BYTES)
        )
    }
    override val id = ID
    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUUID(photoId)
        buffer.writeVarInt(totalBytes)
        buffer.writeVarInt(offset)
        buffer.writeByteArray(data)
    }
}
