package com.nbp.cobblemon_smartphone.mixin;

import com.nbp.cobblemon_smartphone.item.SmartphoneItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingTransformRecipe.class)
public class MixinSmithingTransformRecipe {

    private static final String UPGRADES_TAG = "cobblemon_smartphone:upgrades";
    private static final String EXTRA_CAPACITY_TAG = "cobblemon_smartphone:extra_energy_capacity";
    private static final String SKIN_TAG = "cobblemon_smartphone:skin";
    private static final String SHORT_SKIN_TAG = "smartphone_skin";
    private static final String UNLOCKED_SKINS_TAG = "cobblemon_smartphone:unlocked_skins";

    @Shadow
    private ItemStack result;

    @Inject(method = "assemble", at = @At("HEAD"), cancellable = true)
    public void onAssemble(Container input, RegistryAccess registries,
                           CallbackInfoReturnable<ItemStack> cir) {
        ItemStack base = input.getItem(1);

        // Only intercept if the base is a smartphone
        if (!(base.getItem() instanceof SmartphoneItem)) {
            return;
        }

        CompoundTag recipeData = getResultCustomData();
        if (recipeData == null) {
            return;
        }

        boolean hasUpgrades = recipeData.contains(UPGRADES_TAG, CompoundTag.TAG_COMPOUND);
        boolean hasCapacity = recipeData.contains(EXTRA_CAPACITY_TAG, CompoundTag.TAG_INT);
        boolean hasSkin = recipeData.contains(SKIN_TAG, CompoundTag.TAG_STRING)
                || recipeData.contains(SHORT_SKIN_TAG, CompoundTag.TAG_STRING)
                || recipeData.contains(UNLOCKED_SKINS_TAG, CompoundTag.TAG_COMPOUND);
        if (!hasUpgrades && !hasCapacity && !hasSkin) {
            return;
        }

        // Copy the base smartphone (preserves color) and add upgrades/capacity
        ItemStack output = base.copy();
        output.setCount(1);
        applyRecipeData(output, recipeData);

        cir.setReturnValue(output);
    }

    private CompoundTag getResultCustomData() {
        if (this.result == null || this.result.isEmpty()) return null;
        CompoundTag customData = this.result.getTag();
        if (customData == null) return null;
        return customData.copy();
    }

    private void applyRecipeData(ItemStack stack, CompoundTag recipeData) {
        CompoundTag existingData = stack.getTag();
        CompoundTag tag = existingData != null ? existingData.copy() : new CompoundTag();

        if (recipeData.contains(UPGRADES_TAG, CompoundTag.TAG_COMPOUND)) {
            CompoundTag recipeUpgrades = recipeData.getCompound(UPGRADES_TAG);
            CompoundTag targetUpgrades = tag.contains(UPGRADES_TAG, CompoundTag.TAG_COMPOUND)
                    ? tag.getCompound(UPGRADES_TAG) : new CompoundTag();
            for (String key : recipeUpgrades.getAllKeys()) {
                if (recipeUpgrades.getBoolean(key)) {
                    targetUpgrades.putBoolean(key, true);
                }
            }
            tag.put(UPGRADES_TAG, targetUpgrades);
        }

        if (recipeData.contains(EXTRA_CAPACITY_TAG, CompoundTag.TAG_INT)) {
            int extra = recipeData.getInt(EXTRA_CAPACITY_TAG);
            int current = tag.getInt(EXTRA_CAPACITY_TAG);
            tag.putInt(EXTRA_CAPACITY_TAG, current + extra);
        }

        if (recipeData.contains(UNLOCKED_SKINS_TAG, CompoundTag.TAG_COMPOUND)) {
            CompoundTag recipeSkins = recipeData.getCompound(UNLOCKED_SKINS_TAG);
            CompoundTag targetSkins = tag.contains(UNLOCKED_SKINS_TAG, CompoundTag.TAG_COMPOUND)
                    ? tag.getCompound(UNLOCKED_SKINS_TAG) : new CompoundTag();
            for (String key : recipeSkins.getAllKeys()) {
                if (recipeSkins.getBoolean(key)) {
                    targetSkins.putBoolean(key, true);
                }
            }
            tag.put(UNLOCKED_SKINS_TAG, targetSkins);
        }

        String recipeSkin = null;
        if (recipeData.contains(SKIN_TAG, CompoundTag.TAG_STRING)) {
            recipeSkin = recipeData.getString(SKIN_TAG);
        } else if (recipeData.contains(SHORT_SKIN_TAG, CompoundTag.TAG_STRING)) {
            recipeSkin = recipeData.getString(SHORT_SKIN_TAG);
        }
        if (recipeSkin != null && !recipeSkin.isEmpty()) {
            tag.putString(SKIN_TAG, recipeSkin);
            CompoundTag targetSkins = tag.contains(UNLOCKED_SKINS_TAG, CompoundTag.TAG_COMPOUND)
                    ? tag.getCompound(UNLOCKED_SKINS_TAG) : new CompoundTag();
            targetSkins.putBoolean(recipeSkin, true);
            tag.put(UNLOCKED_SKINS_TAG, targetSkins);
        }

        stack.setTag(tag);
    }
}
