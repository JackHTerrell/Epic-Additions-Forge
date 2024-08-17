package com.jackbusters.epicadditions.capabilities.pocketcells;

import net.minecraft.nbt.CompoundTag;

public class PocketCellData {
    private boolean hasPocketCell;
    private int pocketCellLevel;
    private int pocketCellIndex;
    private final int MAX_CELL_LEVEL = 4;

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
