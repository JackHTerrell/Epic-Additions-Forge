package com.jackbusters.epicadditions.mixins;

import com.jackbusters.epicadditions.EpicRegistry;
import com.jackbusters.epicadditions.enchantments.SoulTiedEnchantmentEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The primary use of this mixin class is to ensure the functionality of the Soul-Tied Enchantment.<br>
 * These mixins re-add any soul tied item back into the inventory immediately after they are removed on death.
 *<br><br>
 * The remaining functionality is done in {@link SoulTiedEnchantmentEvents} <br><br>
 * These mixins are necessary so that the Player Clone event has proper access to the player's inventory before he died.
 * Additionally, this maintains a similar-in-appearance functionality to the keepInventory game rule.
 * There simply was not a Forge event to accomplish this action.
 */
@Mixin(Player.class)
public abstract class PlayerDropEquipment extends LivingEntity {
    @Final
    @Shadow
    private Inventory inventory;
    @Unique
    private final Inventory oldInventory = new Inventory((Player) (Object) this);

    protected PlayerDropEquipment(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /*
        Injected at the start of the method to copy the soul-tied items into a fake inventory before the real inventory
        has them removed.
     */
    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void onDropEquipment(CallbackInfo ci){
        for(int i = 0; i < inventory.getContainerSize(); ++i){
            ItemStack item = inventory.getItem(i);
            if(item.getEnchantmentLevel(EpicRegistry.SOUL_TIED.get()) > 0)
                oldInventory.setItem(i, item);
        }
    }

    /*
        Injected at the end of the method, after the items in the player's real inventory are removed.
        This injection re-adds the soul-tied items immediately in the proper, as if they were never gone.
     */
    @Inject(method = "dropEquipment", at = @At("TAIL"))
    private void onDropEquipmentBottom(CallbackInfo ci){
        for(int i = 0; i < oldInventory.getContainerSize(); ++i) {
            ItemStack item = oldInventory.getItem(i);
            if(!item.isEmpty())
                inventory.setItem(i, item);
        }
    }
}
