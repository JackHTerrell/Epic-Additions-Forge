package com.jackbusters.epicadditions.capabilities.pocketcells;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public class PocketCellLevelDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PocketCellLevelData> POCKET_CELL_LEVEL_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    private PocketCellLevelData pocketCellData = null;
    private final LazyOptional<PocketCellLevelData> optionalPocketCellData = LazyOptional.of(this::createPocketCellLevelData);

    private PocketCellLevelData createPocketCellLevelData(){
        if(this.pocketCellData == null)
            this.pocketCellData = new PocketCellLevelData();
        return this.pocketCellData;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == POCKET_CELL_LEVEL_DATA)
            return optionalPocketCellData.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        createPocketCellLevelData().saveCompoundData(compoundTag);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        createPocketCellLevelData().loadCompoundData(compoundTag);
    }
}
