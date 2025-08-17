package com.jackbusters.epicadditions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

/**
 * Perhaps this can be done with Block States to save on resources. Converting to BlockEntity to use NBT would be more complex and take more memory.
 */
public class DimensionalDoorBlock extends DoorBlock {
    public DimensionalDoorBlock(Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
