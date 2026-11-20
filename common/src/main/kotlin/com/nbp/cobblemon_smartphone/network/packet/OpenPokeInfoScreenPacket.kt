package com.nbp.cobblemon_smartphone.network.packet

import com.nbp.cobblemon_smartphone.util.smartphoneResource
import net.minecraft.network.FriendlyByteBuf

/**
 * Server's authorization for the client to open the PokeInfo screen. The screen itself is
 * client-side only, so the server cannot open it directly — it validates the request and signals
 * the client to proceed.
 */
class OpenPokeInfoScreenPacket : CobblemonSmartphoneNetworkPacket<OpenPokeInfoScreenPacket> {
    companion object {
        val ID = smartphoneResource("open_pokeinfo_screen")
        fun decode(buffer: FriendlyByteBuf): OpenPokeInfoScreenPacket = OpenPokeInfoScreenPacket()
    }

    override val id = ID

    override fun encode(buffer: FriendlyByteBuf) {
        // Nenhum dado adicional é enviado
    }
}
