package com.jackbusters.epicadditions.mixins.softstepping;

import com.jackbusters.epicadditions.enchantments.SoftSteppingChecker;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationInfo;
import net.minecraft.world.level.gameevent.vibrations.VibrationSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(VibrationSelector.class)
public class VibrationAcceptorEdits {

    @Inject(at = @At("HEAD"), method = "shouldReplaceVibration", cancellable = true)
    void canAccept(VibrationInfo vibration, long tick, CallbackInfoReturnable<Boolean> cir) {
            Entity entity = vibration.entity();
            GameEvent event = vibration.gameEvent();
            if(entity instanceof LivingEntity aliveEntity)
            {
                if(SoftSteppingChecker.isWearingSoftSteppers(aliveEntity))
              {
                if(event.equals(GameEvent.STEP))
                {
                  cir.setReturnValue(false);
                }
                if(event.equals(GameEvent.HIT_GROUND))
                {
                  cir.setReturnValue(false);
                }
                if(event.equals(GameEvent.PROJECTILE_SHOOT))
                {
                  cir.setReturnValue(false);
                }
                if(event.equals(GameEvent.EQUIP))
                {
                  cir.setReturnValue(false);
                }
              }
            }
          }
}
    
