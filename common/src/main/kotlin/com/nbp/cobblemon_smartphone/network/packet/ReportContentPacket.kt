package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.social.ReportTargetType
import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

class ReportContentPacket(
    val targetType: ReportTargetType,
    val targetId: Long,
    val reason: String
) : CobblemonSmartphoneNetworkPacket<ReportContentPacket> {
    companion object {
        val ID = smartphoneResource("report_content")
        fun decode(buffer: FriendlyByteBuf): ReportContentPacket {
            val type = runCatching { ReportTargetType.valueOf(buffer.readUtf(32)) }.getOrDefault(ReportTargetType.POST)
            val targetId = buffer.readVarLong()
            val reason = buffer.readUtf(256)
            return ReportContentPacket(type, targetId, reason)
        }
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeUtf(targetType.name)
        buffer.writeVarLong(targetId)
        buffer.writeUtf(reason, 256)
    }
}
