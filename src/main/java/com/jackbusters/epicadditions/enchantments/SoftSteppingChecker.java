package com.jackbusters.epicadditions.enchantments;

import com.jackbusters.epicadditions.EpicRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/**
 * <h1>Soft Stepping Checker</h1>
 * <p>This class houses a collection of functions for checking soft stepping-related statuses.</p>
 */
public class SoftSteppingChecker {
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
}
