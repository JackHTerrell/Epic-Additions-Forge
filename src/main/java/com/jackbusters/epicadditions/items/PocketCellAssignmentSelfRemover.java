package com.jackbusters.epicadditions.items;

import com.jackbusters.epicadditions.EpicRegistry;
import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellProvider;
import com.jackbusters.epicadditions.constructs.PocketCell;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PocketCellAssignmentSelfRemover extends Item {
    public PocketCellAssignmentSelfRemover(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if(pLevel instanceof ServerLevel)
            pPlayer.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> {
                data.setHasPocketCell(false);
            });
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> tooltip, @NotNull TooltipFlag pIsAdvanced) {
        tooltip.add(Component.translatable("hovertext.item.pocket_cell_assignment_remover"));
        super.appendHoverText(pStack, pLevel, tooltip, pIsAdvanced);
    }
}
