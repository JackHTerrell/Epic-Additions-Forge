package com.jackbusters.epicadditions.blocks;

import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellLevelDataProvider;
import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CellBlock extends HalfTransparentBlock {
    public CellBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull VoxelShape getVisualShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return true;
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        pEntity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> {
            pLevel.getCapability(PocketCellLevelDataProvider.POCKET_CELL_LEVEL_DATA).ifPresent(lData -> {
               BlockPos pos = lData.getTangibleCellLocations().get(data.getPocketCellIndex());
               pEntity.teleportTo(pos.getX(), pos.getY()+1, pos.getZ());
            });
        });
        super.entityInside(pState, pLevel, pPos, pEntity);
    }
}
