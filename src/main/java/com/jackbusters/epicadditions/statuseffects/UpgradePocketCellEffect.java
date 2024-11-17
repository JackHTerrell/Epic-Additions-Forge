package com.jackbusters.epicadditions.statuseffects;

import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class UpgradePocketCellEffect extends MobEffect {
    public UpgradePocketCellEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }


    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> {
            data.addPocketCellLevel();
            if(pLivingEntity.level instanceof ServerLevel)
                pLivingEntity.sendSystemMessage(Component.translatable("hovertext.item.upgrade_level", data.getPocketCellLevel()));
        });
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration==1;
    }
}
