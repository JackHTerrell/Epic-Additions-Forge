package com.jackbusters.epicadditions.mixins.softstepping;

import com.jackbusters.epicadditions.enchantments.SoftSteppingChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(SculkShriekerBlock.class)
public class SculkShriekerEdits {

    @Inject(at = @At("HEAD"), method = "stepOn", cancellable = true)
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity, CallbackInfo cir) {
        if(entity instanceof LivingEntity livingEntity) {
            if (SoftSteppingChecker.isWearingSoftSteppers(livingEntity))
                cir.cancel();
        }
    }

}
