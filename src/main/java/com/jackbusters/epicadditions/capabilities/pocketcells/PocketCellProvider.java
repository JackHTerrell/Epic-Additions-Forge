package com.jackbusters.epicadditions.capabilities.pocketcells;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public class PocketCellProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PocketCellData> POCKET_CELL_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    private PocketCellData pocketCellData = null;
    private final LazyOptional<PocketCellData> optionalPocketCellData = LazyOptional.of(this::createPocketCellData);

    private PocketCellData createPocketCellData(){
        if(this.pocketCellData == null)
            this.pocketCellData = new PocketCellData();
        return this.pocketCellData;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == POCKET_CELL_DATA)
            return optionalPocketCellData.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        createPocketCellData().saveCompoundData(compoundTag);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        createPocketCellData().loadCompoundData(compoundTag);
    }
}
