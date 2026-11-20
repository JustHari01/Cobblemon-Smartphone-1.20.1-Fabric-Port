package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.social.CallStatus
import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf
import java.util.UUID

/**
 * Everything the client asks the server to do about a call: start ringing someone, accept/decline
 * an incoming ring, or hang up an active/outgoing call. The server decides what is valid based on
 * the current session — the client only expresses intent.
 */
class CallActionPacket(val action: Action, val targetUuid: UUID) : CobblemonSmartphoneNetworkPacket<CallActionPacket> {
    enum class Action { START, ACCEPT, DECLINE, HANGUP;
        companion object {
            fun byId(id: Int): Action = entries.getOrElse(id) { HANGUP }
        }
    }

    companion object {
        val ID = smartphoneResource("call_action")
        fun decode(buffer: FriendlyByteBuf) =
            CallActionPacket(Action.byId(buffer.readVarInt()), buffer.readUUID())
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarInt(action.ordinal)
        buffer.writeUUID(targetUuid)
    }
}

/**
 * The server's authoritative view of this client's call: which phase, and who the other party is.
 * [otherUuid]/[otherName] are meaningless when [status] is IDLE.
 */
class CallStatePacket(
    val status: CallStatus,
    val otherUuid: UUID,
    val otherName: String
) : CobblemonSmartphoneNetworkPacket<CallStatePacket> {
    companion object {
        val ID = smartphoneResource("call_state")
        fun decode(buffer: FriendlyByteBuf) =
            CallStatePacket(CallStatus.byId(buffer.readVarInt()), buffer.readUUID(), buffer.readUtf())
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeVarInt(status.ordinal)
        buffer.writeUUID(otherUuid)
        buffer.writeUtf(otherName)
    }
}

/** Server-authoritative feedback for a call target that is not currently online. */
class CallOfflinePacket(val targetUuid: UUID) : CobblemonSmartphoneNetworkPacket<CallOfflinePacket> {
    companion object {
        val ID = smartphoneResource("call_offline")
        fun decode(buffer: FriendlyByteBuf) = CallOfflinePacket(buffer.readUUID())
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUUID(targetUuid)
    }
}
