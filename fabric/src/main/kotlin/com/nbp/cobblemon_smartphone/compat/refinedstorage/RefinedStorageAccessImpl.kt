package com.nbp.cobblemon_smartphone.compat.refinedstorage

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

/**
 * Refined Storage has no Fabric release for Minecraft 1.20.1. Keep the optional bridge inert
 * on this target so the rest of the smartphone remains loadable without linking 1.21-only APIs.
 */
object RefinedStorageAccessImpl : RefinedStorageAccess {
    override fun initializeClient() = Unit
    override fun isBound(stack: ItemStack): Boolean = false
    override fun isReachable(player: ServerPlayer, stack: ItemStack): Boolean = false
    override fun openGrid(player: ServerPlayer, stack: ItemStack) = Unit
}
