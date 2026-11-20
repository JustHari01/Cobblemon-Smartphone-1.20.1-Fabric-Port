package com.nbp.cobblemon_smartphone.network

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.net.ClientNetworkPacketHandler
import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/** Registers one typed packet with Cobblemon's 1.20.1 network manager. */
class PacketRegisterInfo<T : NetworkPacket<T>>(
    val id: ResourceLocation,
    val decoder: (FriendlyByteBuf) -> T,
    private val handler: Any
) {
    @Suppress("UNCHECKED_CAST")
    private val packetClass: KClass<T> by lazy {
        val packetType = handler.javaClass.genericInterfaces
            .filterIsInstance<ParameterizedType>()
            .firstOrNull { type ->
                val raw = type.rawType as? Class<*>
                raw == ClientNetworkPacketHandler::class.java || raw == ServerNetworkPacketHandler::class.java
            }
            ?.actualTypeArguments
            ?.firstOrNull() as? Class<T>
            ?: error("Could not infer packet type from ${handler.javaClass.name}")
        packetType.kotlin as KClass<T>
    }

    @Suppress("UNCHECKED_CAST")
    fun registerClientHandler() {
        Cobblemon.implementation.networkManager.createClientBound(
            id,
            packetClass,
            { packet, buffer -> packet.encode(buffer) },
            decoder,
            handler as ClientNetworkPacketHandler<T>
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun registerServerHandler() {
        Cobblemon.implementation.networkManager.createServerBound(
            id,
            packetClass,
            { packet, buffer -> packet.encode(buffer) },
            decoder,
            handler as ServerNetworkPacketHandler<T>
        )
    }
}
