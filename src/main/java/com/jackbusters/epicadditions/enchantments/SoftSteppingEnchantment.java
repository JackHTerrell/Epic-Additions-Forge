package com.jackbusters.epicadditions.enchantments;

import com.jackbusters.epicadditions.configurations.EpicServerConfig;
import com.jackbusters.epicadditions.mixins.softstepping.SculkSensorEdits;
import com.jackbusters.epicadditions.mixins.softstepping.SculkShriekerEdits;
import com.jackbusters.epicadditions.mixins.softstepping.VibrationAcceptorEdits;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * <h1>Soft Stepping Enchantment</h1>
 * <p>When applied to boots, will actively prevent the following vibrations from the wearer:</p>
 * <ul>
 *     <li>Stepping</li>
 *     <li>Falling</li>
 *     <li>Launching Projectile</li>
 *     <li>Equipping Armor</li>
 * </ul>
 * <p>Most functional work for this enchantment will be done in the following classes:</p>
 * <ul>
 *     <li>{@link SculkSensorEdits}</li>
 *     <li>{@link SculkShriekerEdits}</li>
 *     <li>{@link VibrationAcceptorEdits}</li>
 * </ul>
 */
public class SoftSteppingEnchantment extends Enchantment {
    public SoftSteppingEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    /*
        If true, will be capable of being found in Enchantment tables.
     */
    @Override
    public boolean isDiscoverable() {
        return false;
    }

    /*
        If true, Librarian villagers will generate trades for this item.
    */
    @Override
    public boolean isTradeable() {
        return EpicServerConfig.isSoftSteppingEnchantmentTradeable.get();
    }
}
