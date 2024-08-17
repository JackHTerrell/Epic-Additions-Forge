package com.jackbusters.epicjourney.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PocketDimensionWarpKey extends Item {
    public PocketDimensionWarpKey(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack usedItemStack, @NotNull Level level, @NotNull LivingEntity entityUsing, int timeCharged) {
        super.releaseUsing(usedItemStack, level, entityUsing, timeCharged);
    }
}
