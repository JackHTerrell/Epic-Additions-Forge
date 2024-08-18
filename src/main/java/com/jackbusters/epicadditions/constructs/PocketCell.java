package com.jackbusters.epicadditions.constructs;

import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellLevelDataProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

public class PocketCell {
    /*
        Constructs a pocket cell in the correct location.
     */
    public static void buildPocketCell(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension){
        System.out.println("Pocket Cell Level for player: "+pocketCellLevel);
        pocketDimension.getCapability(PocketCellLevelDataProvider.POCKET_CELL_LEVEL_DATA).ifPresent(data -> {
        });
    }
}
