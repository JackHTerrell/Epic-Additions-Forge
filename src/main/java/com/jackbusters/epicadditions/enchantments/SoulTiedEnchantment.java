package com.jackbusters.epicadditions.enchantments;

import com.jackbusters.epicadditions.mixins.PlayerDropEquipment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * <h1>The Soul Tied Enchantment</h1>
 * <p>The Soul Tied enchantment prevents enchanted items from being lost upon death.</p>
 * <p>Most functionality is handled in {@link SoulTiedEnchantmentEvents} and {@link PlayerDropEquipment}.</p>
 */
public class SoulTiedEnchantment extends Enchantment {
    public SoulTiedEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public boolean isTradeable() {
        return true;
    }
}
