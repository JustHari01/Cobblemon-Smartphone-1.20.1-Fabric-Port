package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf
import java.util.UUID

/** Server -> client: the full set of players this player has muted, sent on join. */
class SyncMutedPlayersPacket(val players: List<UUID>) : CobblemonSmartphoneNetworkPacket<SyncMutedPlayersPacket> {
    companion object {
        val ID = smartphoneResource("sync_muted_players")
        fun decode(buffer: FriendlyByteBuf): SyncMutedPlayersPacket {
            val count = buffer.readVarInt()
            return SyncMutedPlayersPacket((0 until count).map { buffer.readUUID() })
        }
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarInt(players.size)
        players.forEach { buffer.writeUUID(it) }
    }
}
