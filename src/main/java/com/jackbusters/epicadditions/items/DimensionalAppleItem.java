package com.jackbusters.epicadditions.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DimensionalAppleItem extends Item {
    public DimensionalAppleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> tooltip, @NotNull TooltipFlag pIsAdvanced) {
        tooltip.add(Component.translatable("hovertext.item.dimensional_apple").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, tooltip, pIsAdvanced);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        return super.getDefaultInstance();
    }
}
