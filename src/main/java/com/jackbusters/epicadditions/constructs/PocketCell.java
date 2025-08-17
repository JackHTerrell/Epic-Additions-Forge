package com.jackbusters.epicadditions.constructs;

import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellLevelDataProvider;
import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Pocket Cell</h1>
 * <h3>Contains the functions & logic pertaining to the construction of Pocket Cells</h3>
 */
public class PocketCell {

    private static final int commonDimension = 17; // Length, width, and start height of cells (i.e. 17x17x17). Only works with odd numbers.

    /**
     * Finds an empty spot for a Pocket Cell and places one in that location based on a Player's data.
     * Adds that player to the players with cells list and sets that cell location as occupied.
     * @param buildingBlock The building block of which to create the cell.
     * @param pocketCellLevel The level the player's cell should be.
     * @param pocketDimension The Pocket Dimension.
     * @param entity The entity to create a cell for.
     */
    public static void buildNewPocketCell(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension, Entity entity){
        pocketDimension.getCapability(PocketCellLevelDataProvider.POCKET_CELL_LEVEL_DATA).ifPresent(data -> {
            if(data.getOccupiedCellLocations().isEmpty()) {

                BlockPos initialStartPos = new BlockPos(0, -2000, 0);

                List<BlockPos> startBlock = new ArrayList<>();
                startBlock.add(initialStartPos);

                data.setOccupiedCellLocations(startBlock);

                List<UUID> startPlayer = new ArrayList<>();
                startPlayer.add(entity.getUUID());

                data.setPlayersWithCells(startPlayer);
                forceBuildCell(buildingBlock, pocketCellLevel, pocketDimension, initialStartPos);
                entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(pData -> {
                    pData.setHasPocketCell(true);
                    pData.setPocketCellIndex(0);
                });
            }
            else {
                int distanceBetweenCellCenters = 36;
                BlockPos lastAddedCell = data.getOccupiedCellLocations().get(data.getOccupiedCellLocations().size() - 1);

                BlockPos potentialNorthCell = lastAddedCell.north(distanceBetweenCellCenters);
                BlockPos potentialEastCell = lastAddedCell.east(distanceBetweenCellCenters);
                BlockPos potentialSouthCell = lastAddedCell.south(distanceBetweenCellCenters);
                BlockPos potentialWestCell = lastAddedCell.west(distanceBetweenCellCenters);


                // 1 = North, 2 = East, 3 = South, 4 = West
                boolean added = false;
                int iteration = 0;
                while(!added) {
                    // Essentially, if an open cell slot somehow fails to be located after 4 attempts, game will continuously check north until an open location is found.
                    if (iteration > 4) {
                        boolean success = false;
                        int forcedSuccessIteration = 2;
                        while (!success) {
                            if (!data.getOccupiedCellLocations().contains(lastAddedCell.north(distanceBetweenCellCenters * forcedSuccessIteration))) {
                                success = true;
                                added = true;

                                List<BlockPos> tempLi = data.getOccupiedCellLocations();
                                List<UUID> list = data.getPlayersWithCells();
                                tempLi.add(lastAddedCell.north(distanceBetweenCellCenters * forcedSuccessIteration));
                                list.add(entity.getUUID());
                                data.setOccupiedCellLocations(tempLi);
                                data.setPlayersWithCells(list);
                                entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(pData -> {
                                    pData.setHasPocketCell(true);
                                    pData.setPocketCellIndex(data.getOccupiedCellLocations().size() - 1);
                                });
                                forceBuildCell(buildingBlock, pocketCellLevel, pocketDimension, lastAddedCell.north(distanceBetweenCellCenters * forcedSuccessIteration));
                            }
                            forcedSuccessIteration++;
                        }
                    } else {
                        int random = (int) ((Math.random() * 4) + 1); // Random value [1, 4]. 1 = North, 2 = East, 3 = South, 4 = West
                        if (random == 1 && !data.getOccupiedCellLocations().contains(potentialNorthCell)) {
                            added = true;
                            List<BlockPos> tempLi = data.getOccupiedCellLocations();
                            List<UUID> list = data.getPlayersWithCells();
                            tempLi.add(potentialNorthCell);
                            list.add(entity.getUUID());
                            data.setOccupiedCellLocations(tempLi);
                            data.setPlayersWithCells(list);
                            entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(pData -> {
                                pData.setHasPocketCell(true);
                                pData.setPocketCellIndex(data.getOccupiedCellLocations().size() - 1);
                            });
                            forceBuildCell(buildingBlock, pocketCellLevel, pocketDimension, potentialNorthCell);
                        } else if (random == 2 && !data.getOccupiedCellLocations().contains(potentialEastCell)) {
                            added = true;
                            List<BlockPos> tempLi = data.getOccupiedCellLocations();
                            List<UUID> list = data.getPlayersWithCells();
                            tempLi.add(potentialEastCell);
                            list.add(entity.getUUID());
                            data.setOccupiedCellLocations(tempLi);
                            data.setPlayersWithCells(list);
                            entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(pData -> {
                                pData.setHasPocketCell(true);
                                pData.setPocketCellIndex(data.getOccupiedCellLocations().size() - 1);
                            });
                            forceBuildCell(buildingBlock, pocketCellLevel, pocketDimension, potentialEastCell);
                        } else if (random == 3 && !data.getOccupiedCellLocations().contains(potentialSouthCell)) {
                            added = true;
                            List<BlockPos> tempLi = data.getOccupiedCellLocations();
                            List<UUID> list = data.getPlayersWithCells();
                            tempLi.add(potentialSouthCell);
                            list.add(entity.getUUID());
                            data.setOccupiedCellLocations(tempLi);
                            data.setPlayersWithCells(list);
                            entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(pData -> {
                                pData.setHasPocketCell(true);
                                pData.setPocketCellIndex(data.getOccupiedCellLocations().size() - 1);
                            });
                            forceBuildCell(buildingBlock, pocketCellLevel, pocketDimension, potentialSouthCell);
                        } else if (random == 4 && !data.getOccupiedCellLocations().contains(potentialWestCell)) {
                            added = true;
                            List<BlockPos> tempLi = data.getOccupiedCellLocations();
                            List<UUID> list = data.getPlayersWithCells();
                            tempLi.add(potentialWestCell);
                            list.add(entity.getUUID());
                            data.setOccupiedCellLocations(tempLi);
                            data.setPlayersWithCells(list);
                            entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(pData -> {
                                pData.setHasPocketCell(true);
                                pData.setPocketCellIndex(data.getOccupiedCellLocations().size() - 1);
                            });
                            forceBuildCell(buildingBlock, pocketCellLevel, pocketDimension, potentialWestCell);
                        }
                        iteration++;
                    }
                }
            }
        });
    }

    /**
     * Generates Cell at location. Assigns no player or level data, just builds the blocks.
     * @param buildingBlock The block to make the cell out of
     * @param pocketCellLevel The level of the Pocket Cell
     * @param pocketDimension The dimension to create the cell in
     * @param center The Center position of where to create the cell
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

    /**
     * Regenerates the roof and walls of a given Pocket Cell. Used for updating cells to the appropriate level.
     * @param buildingBlock The block to make the walls and roof with
     * @param pocketCellLevel The new level of the Pocket Cell
     * @param pocketDimension The Pocket Dimension
     * @param center The location of the cell
     */
    public static void updateRoofAndWallHeight(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension, BlockPos center){
        BlockPos builtCenterEast = center.east(commonDimension/2);
        BlockPos builtCenterWest = center.west(commonDimension/2);
        BlockPos builtCenterNorth = center.north(commonDimension/2);
        BlockPos builtCenterSouth = center.south(commonDimension/2);
        
        removePreviousRoof(pocketCellLevel, pocketDimension, center);
        generateWallsEastWest(buildingBlock, pocketCellLevel, pocketDimension, builtCenterEast);
        generateWallsEastWest(buildingBlock, pocketCellLevel, pocketDimension, builtCenterWest);
        generateWallsNorthSouth(buildingBlock, pocketCellLevel, pocketDimension, builtCenterNorth);
        generateWallsNorthSouth(buildingBlock, pocketCellLevel, pocketDimension, builtCenterSouth);
        generateRoof(buildingBlock, pocketCellLevel, pocketDimension, center);
    }

    private static void removePreviousRoof(int pocketCellLevel, ServerLevel pocketDimension, BlockPos builtCenter){
        BlockPos builtCenterEast = builtCenter.east((commonDimension / 2) - 1);
        BlockPos builtCenterWest = builtCenter.west((commonDimension / 2) - 1);

        int height = (builtCenter.getY()+((pocketCellLevel)+commonDimension))-2;

        BoundingBox boundingBox = BoundingBox.fromCorners(new Vec3i(builtCenterEast.getX(), height, builtCenterEast.north(commonDimension / 2).getZ()+1),
                new Vec3i(builtCenterWest.getX(), (builtCenter.getY()+(pocketCellLevel+commonDimension))-1, builtCenterWest.south(commonDimension / 2).getZ()-1));

        Iterable<BlockPos> iteratable = BlockPos.betweenClosed(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ());
        for (BlockPos currentBlockPos : iteratable) {
            pocketDimension.setBlockAndUpdate(currentBlockPos, Blocks.AIR.defaultBlockState());
        }
    }

    private static void generateWallsEastWest(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension, BlockPos builtCenter){
        int dis = commonDimension/2;
        BoundingBox boundingBox = BoundingBox.fromCorners(new Vec3i(builtCenter.getX(), (builtCenter.getY()+(pocketCellLevel+commonDimension))-1, builtCenter.north(dis).getZ()),
                new Vec3i(builtCenter.getX(), builtCenter.getY(), builtCenter.south(dis).getZ()));

        Iterable<BlockPos> iteratable = BlockPos.betweenClosed(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ());
        for(BlockPos currentBlockPos : iteratable){
            pocketDimension.setBlockAndUpdate(currentBlockPos, buildingBlock.defaultBlockState());
        }
    }

    private static void generateWallsNorthSouth(Block buildingBlock, int pocketCellLevel, ServerLevel pocketDimension, BlockPos builtCenter){
        int dis = commonDimension/2;
        BoundingBox boundingBox = BoundingBox.fromCorners(new Vec3i(builtCenter.east(dis).getX(), (builtCenter.getY()+(pocketCellLevel+commonDimension))-1, builtCenter.getZ()),
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
        BoundingBox boundingBox = BoundingBox.fromCorners(new Vec3i(builtCenterEast.getX(), (builtCenter.getY()+((pocketCellLevel)+commonDimension))-1, builtCenterEast.north(commonDimension / 2).getZ()+1),
                new Vec3i(builtCenterWest.getX(), (builtCenter.getY()+(pocketCellLevel+commonDimension))-1, builtCenterWest.south(commonDimension / 2).getZ()-1));

        Iterable<BlockPos> iteratable = BlockPos.betweenClosed(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ());
        for (BlockPos currentBlockPos : iteratable) {
            pocketDimension.setBlockAndUpdate(currentBlockPos, buildingBlock.defaultBlockState());
        }
    }
}
