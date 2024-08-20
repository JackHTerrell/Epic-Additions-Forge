package com.jackbusters.epicadditions.constructs;

import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellLevelDataProvider;
import com.jackbusters.epicadditions.configurations.EpicServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.List;

public class PocketCell {

    private static final int commonDimension = EpicServerConfig.pocketCellDimensions.get().get(0);
    /*
        Constructs a pocket cell in the correct location.
     */
    public static void buildNewPocketCell(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension, Entity entity){
        pocketDimension.getCapability(PocketCellLevelDataProvider.POCKET_CELL_LEVEL_DATA).ifPresent(data -> {
            if(data.getTangibleCellLocations().isEmpty()) {
                BlockPos initialStartPos = new BlockPos(0, -60, 0);
                data.setTangibleCellLocations(List.of(initialStartPos));
                data.setPlayersWithCells(List.of(entity.getUUID()));
                forceBuildCell(buildingBlock, pocketCellLevel, pocketDimension, initialStartPos);
            }
            int distanceBetweenCellCenters = 33;
            BlockPos lastAddedCell = data.getTangibleCellLocations().get(data.getTangibleCellLocations().size()-1);
            BlockPos nextCell;

            // 1 = North, 2 = East, 3 = South, 4 = West
            boolean added = false;
            if(!added){

            }
        });
    }

    /*
        Will generate a cell where commanded.
     */
    public static void forceBuildCell(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension, BlockPos center){
        BlockPos builtCenterEast = center.east(commonDimension/2);
        BlockPos builtCenterWest = center.west(commonDimension/2);
        BlockPos builtCenterNorth = center.north(commonDimension/2);
        BlockPos builtCenterSouth = center.south(commonDimension/2);

        generateWallsEastWest(buildingBlock, pocketCellLevel, pocketDimension, builtCenterEast);
        generateWallsEastWest(buildingBlock, pocketCellLevel, pocketDimension, builtCenterWest);
        generateWallsNorthSouth(buildingBlock, pocketCellLevel, pocketDimension, builtCenterNorth);
        generateWallsNorthSouth(buildingBlock, pocketCellLevel, pocketDimension, builtCenterSouth);
        generateFloor(buildingBlock, pocketDimension, center);
        generateRoof(buildingBlock, pocketCellLevel, pocketDimension, center);
    }

    private static void generateWallsEastWest(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension, BlockPos builtCenter){
        int dis = commonDimension/2;
        BoundingBox boundingBox = BoundingBox.fromCorners(new Vec3i(builtCenter.getX(), (builtCenter.getY()+((commonDimension*pocketCellLevel)+commonDimension))-1, builtCenter.north(dis).getZ()),
                new Vec3i(builtCenter.getX(), builtCenter.getY(), builtCenter.south(dis).getZ()));

        Iterable<BlockPos> iteratable = BlockPos.betweenClosed(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ());
        for(BlockPos currentBlockPos : iteratable){
            pocketDimension.setBlockAndUpdate(currentBlockPos, buildingBlock.defaultBlockState());
        }
    }

    private static void generateWallsNorthSouth(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension, BlockPos builtCenter){
        int dis = commonDimension/2;
        BoundingBox boundingBox = BoundingBox.fromCorners(new Vec3i(builtCenter.east(dis).getX(), (builtCenter.getY()+((commonDimension*pocketCellLevel)+commonDimension))-1, builtCenter.getZ()),
                new Vec3i(builtCenter.west(dis).getX(), builtCenter.getY(), builtCenter.getZ()));

        Iterable<BlockPos> iteratable = BlockPos.betweenClosed(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ());
        for(BlockPos currentBlockPos : iteratable){
            pocketDimension.setBlockAndUpdate(currentBlockPos, buildingBlock.defaultBlockState());
        }
    }

    private static void generateFloor(Block buildingBlock, ServerLevel pocketDimension, BlockPos builtCenter) {
        BlockPos builtCenterEast = builtCenter.east((commonDimension / 2) - 1);
        BlockPos builtCenterWest = builtCenter.west((commonDimension / 2) - 1);
        BoundingBox boundingBox = BoundingBox.fromCorners(new Vec3i(builtCenterEast.getX(), builtCenterEast.getY(), builtCenterEast.north(commonDimension / 2).getZ()+1),
                new Vec3i(builtCenterWest.getX(), builtCenterWest.getY(), builtCenterWest.south(commonDimension / 2).getZ()-1));

        Iterable<BlockPos> iteratable = BlockPos.betweenClosed(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ());
        for (BlockPos currentBlockPos : iteratable) {
            pocketDimension.setBlockAndUpdate(currentBlockPos, buildingBlock.defaultBlockState());
        }
    }

    private static void generateRoof(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension, BlockPos builtCenter) {
        BlockPos builtCenterEast = builtCenter.east((commonDimension / 2) - 1);
        BlockPos builtCenterWest = builtCenter.west((commonDimension / 2) - 1);
        BoundingBox boundingBox = BoundingBox.fromCorners(new Vec3i(builtCenterEast.getX(), (builtCenter.getY()+((commonDimension*pocketCellLevel)+commonDimension))-1, builtCenterEast.north(commonDimension / 2).getZ()+1),
                new Vec3i(builtCenterWest.getX(), (builtCenter.getY()+((commonDimension*pocketCellLevel)+commonDimension))-1, builtCenterWest.south(commonDimension / 2).getZ()-1));

        Iterable<BlockPos> iteratable = BlockPos.betweenClosed(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ());
        for (BlockPos currentBlockPos : iteratable) {
            pocketDimension.setBlockAndUpdate(currentBlockPos, buildingBlock.defaultBlockState());
        }
    }
}
