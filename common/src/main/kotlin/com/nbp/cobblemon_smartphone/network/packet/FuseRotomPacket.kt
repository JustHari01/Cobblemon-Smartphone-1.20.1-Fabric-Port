package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf
import java.util.UUID

class FuseRotomPacket(val entityUuid: UUID) : CobblemonSmartphoneNetworkPacket<FuseRotomPacket> {
    companion object {
        val ID = smartphoneResource("fuse_rotom")

        fun decode(buffer: FriendlyByteBuf): FuseRotomPacket {
            val entityUuid = buffer.readUUID()
            return FuseRotomPacket(entityUuid)
        }
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUUID(entityUuid)
    }
}
