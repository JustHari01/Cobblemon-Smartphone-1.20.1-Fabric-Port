package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class ExecuteDatapackActionPacket(val actionId: String) : CobblemonSmartphoneNetworkPacket<ExecuteDatapackActionPacket> {
    companion object {
        val ID = smartphoneResource("execute_datapack_action")
        fun decode(buffer: FriendlyByteBuf): ExecuteDatapackActionPacket {
            val actionId = buffer.readUtf(128)
            return ExecuteDatapackActionPacket(actionId)
        }
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUtf(actionId, 128)
    }
}
