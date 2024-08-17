package com.jackbusters.epicadditions.capabilities.pocketcells;

import net.minecraft.nbt.CompoundTag;

public class PocketCellData {
    private boolean hasPocketCell; // A quick value to state whether or not this player has a pocket cell yet
    private int pocketCellLevel; // The pocket cell level of the player. Each level expands the width, length, and height by 32. Level 1 = 32x32x32, 2 = 64x64x64, etc...
    private int pocketCellIndex; // The index will denote which pocket cell is associated with this player.
    private final int MAX_CELL_LEVEL = 4; // The max cell level allowed.

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
    }

    public void saveCompoundData(CompoundTag compoundTag){
        compoundTag.putBoolean("has_pocket_cell", doesHavePocketCell());
        compoundTag.putInt("pocket_cell_index", getPocketCellIndex());
        compoundTag.putInt("pocket_cell_level", getPocketCellLevel());
    }

    public void loadCompoundData(CompoundTag compoundTag){
        setHasPocketCell(compoundTag.getBoolean("has_pocket_cell"));
        setPocketCellIndex(compoundTag.getInt("pocket_cell_index"));
        this.pocketCellLevel=compoundTag.getInt("pocket_cell_level");
    }
}
