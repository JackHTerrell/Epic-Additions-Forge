package com.jackbusters.epicadditions.statuseffects;

import com.jackbusters.epicadditions.EpicAdditions;
import com.jackbusters.epicadditions.EpicRegistry;
import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellLevelDataProvider;
import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellProvider;
import com.jackbusters.epicadditions.constructs.PocketCell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * A hidden status effect given after eating the Dimensional Apple.<br>
 * Upgrades the cell level and sends a system message to user.
 */
public class UpgradePocketCellEffect extends MobEffect {
    public UpgradePocketCellEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }


    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> {
            data.addPocketCellLevel(pLivingEntity);
            if(pLivingEntity.level instanceof ServerLevel serverLevel) {
                pLivingEntity.sendSystemMessage(Component.translatable("hovertext.item.upgrade_level", data.getPocketCellLevel()));
                if(pLivingEntity instanceof ServerPlayer && data.doesHavePocketCell()) {
                    MinecraftServer minecraftServer = serverLevel.getServer();
                    ResourceKey<Level> pocketDimensionKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(EpicAdditions.MOD_ID, "pocket"));
                    ServerLevel pocketDimension = minecraftServer.getLevel(pocketDimensionKey);
                    if (pocketDimension != null) {
                        pocketDimension.getCapability(PocketCellLevelDataProvider.POCKET_CELL_LEVEL_DATA).ifPresent(levelData -> {
                            BlockPos posOfCellCenter = levelData.getOccupiedCellLocations().get(data.getPocketCellIndex());
                            PocketCell.updateRoofAndWallHeight(EpicRegistry.CELL_BLOCK.get(), data.getPocketCellLevel(), pocketDimension, posOfCellCenter);
                        });
                    }
                }
            }
        });
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration==1;
    }
}
