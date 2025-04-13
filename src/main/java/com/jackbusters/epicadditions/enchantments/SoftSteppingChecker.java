package com.jackbusters.epicadditions.enchantments;

import com.jackbusters.epicadditions.EpicRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

/**
 * <h1>Soft Stepping Checker</h1>
 * <p>This class houses a collection of functions for checking soft stepping-related statuses.</p>
 */
public class SoftSteppingChecker {

    private static final List<GameEvent> SILENCED_GAME_EVENTS =
            List.of(GameEvent.STEP, GameEvent.HIT_GROUND, GameEvent.PROJECTILE_SHOOT, GameEvent.EQUIP);

    /*
        Returns true if passed entity is wearing boots with the soft stepping enchantment.
     */
    public static boolean isWearingSoftSteppers(LivingEntity livingEntity){
        return isItemStackSoftStepper(livingEntity.getItemBySlot(EquipmentSlot.FEET));
    }

    /*
        Returns true if an item has the soft stepping enchantment.
     */
    public static boolean isItemStackSoftStepper(ItemStack itemStack){
        return EnchantmentHelper.getTagEnchantmentLevel(EpicRegistry.SOFT_STEPPING.get(), itemStack) > 0;
    }

    public static boolean isSilencedVibration(GameEvent vibration){
        return SILENCED_GAME_EVENTS.contains(vibration);
    }
}
