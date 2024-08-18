package com.jackbusters.epicadditions.capabilities.pocketcells;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PocketCellLevelData {
    private List<BlockPos> tangibleCellLocations = new ArrayList<>();
    private List<UUID> playersWithCells = new ArrayList<>();

    public List<BlockPos> getTangibleCellLocations(){
        return this.tangibleCellLocations;
    }

    public List<UUID> getPlayersWithCells(){
        return this.playersWithCells;
    }

    public void setTangibleCellLocations(List<BlockPos> tangibleCellLocations){
        this.tangibleCellLocations=tangibleCellLocations;
    }

    public void setPlayersWithCells(List<UUID> playersWithCells){
        this.playersWithCells=playersWithCells;
    }

    public void saveCompoundData(CompoundTag compoundTag){
        saveBlockPosList(getTangibleCellLocations(), new ListTag(), compoundTag);
        saveUUIDList(getPlayersWithCells(), new ListTag(), compoundTag);
    }

    public void loadCompoundData(CompoundTag compoundTag){
        setTangibleCellLocations(loadedBlockPosList(compoundTag));
        setPlayersWithCells(loadedUUIDList(compoundTag));
    }

    private void saveBlockPosList(List<BlockPos> list, ListTag listTag, CompoundTag mainTag){
        list.forEach(blockPos -> {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putInt("pocket_cell_block_pos_x", blockPos.getX());
            compoundTag.putInt("pocket_cell_block_pos_y", blockPos.getY());
            compoundTag.putInt("pocket_cell_block_pos_z", blockPos.getZ());
            listTag.add(compoundTag);
        });
        mainTag.put("pocket_dimension_block_pos_list", listTag);
    }
    private void saveUUIDList(List<UUID> list, ListTag listTag, CompoundTag mainTag){
        list.forEach(uuid -> {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putUUID("uuid_with_pocket_cell", uuid);
            listTag.add(compoundTag);
        });
        mainTag.put("pocket_dimension_uuid_list", listTag);
    }
    private List<BlockPos> loadedBlockPosList(CompoundTag mainTag){
        List<BlockPos> newList = new ArrayList<>();
        mainTag.getList("pocket_dimension_block_pos_list", Tag.TAG_COMPOUND).stream().map(CompoundTag.class::cast)
                .forEach(compoundTag -> {
                    int x = compoundTag.getInt("pocket_cell_block_pos_x");
                    int y = compoundTag.getInt("pocket_cell_block_pos_y");
                    int z = compoundTag.getInt("pocket_cell_block_pos_z");
                    newList.add(new BlockPos(x, y, z));
                });
        return newList;
    }
    private List<UUID> loadedUUIDList(CompoundTag mainTag){
        List<UUID> newList = new ArrayList<>();
        mainTag.getList("pocket_dimension_uuid_list", Tag.TAG_COMPOUND).stream().map(CompoundTag.class::cast)
                .forEach(compoundTag -> {
                    UUID uuid = compoundTag.getUUID("uuid_with_pocket_cell");
                    newList.add(uuid);
                });
        return newList;
    }
}
