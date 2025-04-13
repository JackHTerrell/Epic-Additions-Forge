package com.jackbusters.epicadditions.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DimensionalDoorItem extends BlockItem {
    public DimensionalDoorItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, @NotNull TooltipFlag pFlag) {
        tooltip.add(Component.translatable("hovertext.epicadditions.item.dimensional_door").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("hovertext.epicadditions.item.not_yet_functional").withStyle(ChatFormatting.RED));
        super.appendHoverText(pStack, pLevel, tooltip, pFlag);
    }
}
