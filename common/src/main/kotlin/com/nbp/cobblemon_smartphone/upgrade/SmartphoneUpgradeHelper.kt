package com.nbp.cobblemon_smartphone.upgrade

import com.nbp.cobblemon_smartphone.item.SmartphoneItem
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack

private const val UPGRADES_TAG = "cobblemon_smartphone:upgrades"

fun ItemStack.hasUpgrade(nbtKey: String): Boolean {
    val customData = this.getTag() ?: return false
    val tag = customData.copy()
    if (!tag.contains(UPGRADES_TAG, CompoundTag.TAG_COMPOUND.toInt())) return false
    val upgrades = tag.getCompound(UPGRADES_TAG)
    return upgrades.getBoolean(nbtKey)
}

fun ItemStack.addUpgrade(nbtKey: String) {
    val existingData = this.getTag()
    val tag = existingData?.copy() ?: CompoundTag()
    val upgrades = if (tag.contains(UPGRADES_TAG, CompoundTag.TAG_COMPOUND.toInt())) {
        tag.getCompound(UPGRADES_TAG)
    } else {
        CompoundTag()
    }
    upgrades.putBoolean(nbtKey, true)
    tag.put(UPGRADES_TAG, upgrades)
    this.setTag(tag)
}

fun ItemStack.isSmartphone(): Boolean = this.item is SmartphoneItem
