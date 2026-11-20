package com.nbp.cobblemon_smartphone.util

import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor

/** 1.20.1 equivalent of the newer Component.withColor(int) convenience method. */
fun MutableComponent.withColor(rgb: Int): MutableComponent =
    withStyle(Style.EMPTY.withColor(TextColor.fromRgb(rgb)))
