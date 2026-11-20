package com.nbp.cobblemon_smartphone.compat.refinedstorage

import com.nbp.cobblemon_smartphone.actions.OpenRefinedStorageAction
import com.nbp.cobblemon_smartphone.api.SmartphoneStorageLink
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext

/**
 * Refined Storage has no Fabric 1.20.1 release, so its optional link action stays inactive on
 * this backport. This placeholder keeps the shared registry contract free of RS-only classes.
 */
object RefinedStorageLink : SmartphoneStorageLink {
    override val id = OpenRefinedStorageAction.MOD_ID

    override fun tryLink(context: UseOnContext, smartphoneStack: ItemStack): InteractionResult? = null
}
