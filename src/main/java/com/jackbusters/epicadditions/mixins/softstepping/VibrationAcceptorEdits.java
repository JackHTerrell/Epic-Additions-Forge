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


/**
 * <h1>Vibration Selector Mixin</h1>
 * <p>Handles the selection of vibrations from anything that detects vibrations (i.e. Warden, Sculk Sensor)</p>
 */
@Mixin(VibrationSelector.class)
public class VibrationAcceptorEdits {

    /*
        If true, game event from player will be detected as a vibration.
        If false, the game event from the player will not be detected as a vibration.
     */
    @Inject(at = @At("HEAD"), method = "shouldReplaceVibration", cancellable = true)
    void canAccept(VibrationInfo vibration, long tick, CallbackInfoReturnable<Boolean> cir) {
            Entity entity = vibration.entity();
            GameEvent event = vibration.gameEvent();
            if(entity instanceof LivingEntity aliveEntity && SoftSteppingChecker.isWearingSoftSteppers(aliveEntity))
                cir.setReturnValue(!SoftSteppingChecker.isSilencedVibration(event));
          }
}
    
