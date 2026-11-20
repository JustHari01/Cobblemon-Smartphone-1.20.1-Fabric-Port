package com.nbp.cobblemon_smartphone

import com.nbp.cobblemon_smartphone.network.CobblemonSmartphoneNetwork

/** Registers the mod's packets through Cobblemon's Fabric network manager. */
object CobblemonSmartphoneFabricNetworkManager {
    fun registerClientHandlers() {
        CobblemonSmartphoneNetwork.s2cPayloads.forEach { it.registerClientHandler() }
    }

    fun registerServerHandlers() {
        CobblemonSmartphoneNetwork.c2sPayloads.forEach { it.registerServerHandler() }
    }
}
