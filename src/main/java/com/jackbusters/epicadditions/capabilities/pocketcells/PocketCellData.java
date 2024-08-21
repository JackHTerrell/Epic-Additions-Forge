package com.jackbusters.epicadditions.capabilities.pocketcells;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class PocketCellData {
    private boolean hasPocketCell; // A quick value to state whether or not this player has a pocket cell yet
    private int pocketCellLevel; // The pocket cell level of the player. Each level expands the height by 17.
    private int pocketCellIndex; // The index will denote which pocket cell is associated with this player.
    private Vec3 leftPos; // The position the player left before warping to their cell.
    private ResourceKey<Level> leftDimensionId; // The Resource Key for the dimension the player left before warping to their cell.
    private final int MAX_CELL_LEVEL = 100; // The max cell level allowed.

    public void setLeftPos(Vec3 leftPos){
        this.leftPos = leftPos;
    }

    public Vec3 getLeftPos(){
        return this.leftPos;
    }

    public void setLeftDimensionId(ResourceKey<Level> leftDimensionId){
        this.leftDimensionId=leftDimensionId;
    }

    public ResourceKey<Level> getLeftDimensionId(){
        return this.leftDimensionId;
    }

    public void setHasPocketCell(boolean hasPocketCell){
        this.hasPocketCell = hasPocketCell;
    }

    public boolean doesHavePocketCell(){
        return hasPocketCell;
    }

    public void setPocketCellIndex(int pocketCellIndex){
        this.pocketCellIndex=pocketCellIndex;
    }

    public int getPocketCellIndex(){
        return pocketCellIndex;
    }

    public int getPocketCellLevel(){
        return pocketCellLevel;
    }

    public void addPocketCellLevel(){
        pocketCellLevel = Math.min(pocketCellLevel+1, MAX_CELL_LEVEL);
    }

    public int getMaxCellLevel(){
        return MAX_CELL_LEVEL;
    }

    public void copyFrom(PocketCellData source){
        this.hasPocketCell = source.hasPocketCell;
        this.pocketCellIndex = source.pocketCellIndex;
        this.pocketCellLevel = source.pocketCellLevel;
        this.leftDimensionId = source.leftDimensionId;
        this.leftPos = source.leftPos;
    }

    public void saveCompoundData(CompoundTag compoundTag){
        compoundTag.putBoolean("has_pocket_cell", doesHavePocketCell());
        compoundTag.putInt("pocket_cell_index", getPocketCellIndex());
        compoundTag.putInt("pocket_cell_level", getPocketCellLevel());

        // Vec3 data
        compoundTag.putDouble("from_pos_x", getLeftPos().x());
        compoundTag.putDouble("from_pos_y", getLeftPos().y());
        compoundTag.putDouble("from_pos_z", getLeftPos().z());

        // Resource Key data
        compoundTag.putString("left_dimension_resource_location", getLeftDimensionId().location().toString());
    }

    public void loadCompoundData(CompoundTag compoundTag){
        setHasPocketCell(compoundTag.getBoolean("has_pocket_cell"));
        setPocketCellIndex(compoundTag.getInt("pocket_cell_index"));
        this.pocketCellLevel=compoundTag.getInt("pocket_cell_level");
        setLeftPos(new Vec3(compoundTag.getDouble("from_pos_x"), compoundTag.getDouble("from_pos_y"), compoundTag.getDouble("from_pos_z")));
        setLeftDimensionId(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(compoundTag.getString("left_dimension_resource_location"))));
    }
}
