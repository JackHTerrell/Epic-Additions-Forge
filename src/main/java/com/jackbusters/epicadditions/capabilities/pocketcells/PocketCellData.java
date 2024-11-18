package com.jackbusters.epicadditions.capabilities.pocketcells;

import com.jackbusters.epicadditions.packets.EpicPacketHandler;
import com.jackbusters.epicadditions.packets.S2CSyncPocketData;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;


public class PocketCellData {
    private boolean hasPocketCell; // A quick value to state whether or not this player has a pocket cell yet
    private int pocketCellLevel; // The pocket cell level of the player. Each level expands the height by 17.
    private int pocketCellIndex; // The index will denote which pocket cell is associated with this player.
    private Vec3 leftPos = Vec3.ZERO;// The position the player left before warping to their cell.
    private Vec3 leftPocketCellPos = Vec3.ZERO;
    private ResourceKey<Level> leftDimensionId = Level.OVERWORLD; // The Resource Key for the dimension the player left before warping to their cell.
    private float leftYaw;
    private float leftPitch;
    private float leftPocketCellYaw;
    private float leftPocketCellPitch;
    private float wasFallingDistance;
    private Vec3 wasDeltaMovement = Vec3.ZERO;
    private final int MAX_CELL_LEVEL = 6000; // The max cell level allowed.

    public void setLeftPocketCellPos(Vec3 leftPocketCellPos){
        this.leftPocketCellPos = leftPocketCellPos;
    }

    public Vec3 getLeftPocketCellPos(){
        return this.leftPocketCellPos;
    }

    public void setWasDeltaMovement(Vec3 wasDeltaMovement){
        this.wasDeltaMovement = wasDeltaMovement;
    }

    public Vec3 getWasDeltaMovement(){
        return this.wasDeltaMovement;
    }

    public void setWasFallingDistance(float wasFallingDistance){
        this.wasFallingDistance=wasFallingDistance;
    }

    public float getWasFallingDistance(){
        return this.wasFallingDistance;
    }

    public void setLeftPocketCellYaw(float leftPocketCellYaw){
        this.leftPocketCellYaw=leftPocketCellYaw;
    }

    public float getLeftPocketCellYaw(){
        return this.leftPocketCellYaw;
    }

    public void setLeftPocketCellPitch(float leftPocketCellPitch){
        this.leftPocketCellPitch=leftPocketCellPitch;
    }

    public float getLeftPocketCellPitch(){
        return this.leftPocketCellPitch;
    }

    public void setLeftYaw(float leftYaw){
        this.leftYaw=leftYaw;
    }

    public float getLeftYaw(){
        return this.leftYaw;
    }

    public void setLeftPitch(float leftPitch){
        this.leftPitch=leftPitch;
    }

    public float getLeftPitch(){
        return this.leftPitch;
    }

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

    public void addPocketCellLevel(LivingEntity player){
        pocketCellLevel = Math.min(pocketCellLevel+1, MAX_CELL_LEVEL);
        if(player instanceof ServerPlayer serverPlayer){
            player.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data ->
                    EpicPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new S2CSyncPocketData(data.getPocketCellLevel()))
            );
        }
    }

    public void setPocketCellLevel(int pocketCellLevel){
        this.pocketCellLevel=pocketCellLevel;
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
        this.leftPitch = source.leftPitch;
        this.leftYaw = source.leftYaw;
        this.wasFallingDistance = source.wasFallingDistance;
        this.wasDeltaMovement = source.wasDeltaMovement;
        this.leftPocketCellPos = source.leftPocketCellPos;
        this.leftPocketCellPitch = source.leftPocketCellPitch;
        this.leftPocketCellYaw = source.leftPocketCellYaw;
    }

    public void saveCompoundData(CompoundTag compoundTag){
        compoundTag.putBoolean("has_pocket_cell", doesHavePocketCell());
        compoundTag.putInt("pocket_cell_index", getPocketCellIndex());
        compoundTag.putInt("pocket_cell_level", getPocketCellLevel());
        compoundTag.putFloat("from_left_pitch", getLeftPitch());
        compoundTag.putFloat("from_left_yaw", getLeftYaw());
        compoundTag.putFloat("from_cell_pitch", getLeftPocketCellPitch());
        compoundTag.putFloat("from_cell_yaw", getLeftPocketCellYaw());
        compoundTag.putFloat("was_falling_distance", getWasFallingDistance());

        // Vec3 data
        compoundTag.putDouble("from_pos_x", getLeftPos().x());
        compoundTag.putDouble("from_pos_y", getLeftPos().y());
        compoundTag.putDouble("from_pos_z", getLeftPos().z());
        compoundTag.putDouble("was_delta_movement_x", getWasDeltaMovement().x());
        compoundTag.putDouble("was_delta_movement_y", getWasDeltaMovement().y());
        compoundTag.putDouble("was_delta_movement_z", getWasDeltaMovement().z());
        compoundTag.putDouble("from_cell_pos_x", getLeftPocketCellPos().x());
        compoundTag.putDouble("from_cell_pos_y", getLeftPocketCellPos().y());
        compoundTag.putDouble("from_cell_pos_z", getLeftPocketCellPos().z());

        // Resource Key data
        compoundTag.putString("left_dimension_resource_location", getLeftDimensionId().location().toString());
    }

    public void loadCompoundData(CompoundTag compoundTag){
        setHasPocketCell(compoundTag.getBoolean("has_pocket_cell"));
        setPocketCellIndex(compoundTag.getInt("pocket_cell_index"));
        this.pocketCellLevel=compoundTag.getInt("pocket_cell_level");
        setLeftPos(new Vec3(compoundTag.getDouble("from_pos_x"), compoundTag.getDouble("from_pos_y"), compoundTag.getDouble("from_pos_z")));
        setLeftDimensionId(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(compoundTag.getString("left_dimension_resource_location"))));
        setLeftPitch(compoundTag.getFloat("from_left_pitch"));
        setLeftYaw(compoundTag.getFloat("from_left_yaw"));
        setWasFallingDistance(compoundTag.getFloat("was_falling_distance"));
        setWasDeltaMovement(new Vec3(compoundTag.getDouble("was_delta_movement_x"), compoundTag.getDouble("was_delta_movement_y"), compoundTag.getDouble("was_delta_movement_z")));
        setLeftPocketCellPos(new Vec3(compoundTag.getDouble("from_cell_pos_x"), compoundTag.getDouble("from_cell_pos_y"), compoundTag.getDouble("from_cell_pos_z")));
        setLeftPocketCellPitch(compoundTag.getFloat("from_cell_pitch"));
        setLeftPocketCellYaw(compoundTag.getFloat("from_cell_yaw"));
    }
}
