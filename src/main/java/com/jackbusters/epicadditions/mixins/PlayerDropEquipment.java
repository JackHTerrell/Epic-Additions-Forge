package com.jackbusters.epicadditions.mixins;

import com.jackbusters.epicadditions.EpicRegistry;
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

    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void onDropEquipment(CallbackInfo ci){
        for(int i = 0; i < inventory.getContainerSize(); ++i){
            ItemStack item = inventory.getItem(i);
            if(item.getEnchantmentLevel(EpicRegistry.SOUL_TIED.get()) > 0)
                oldInventory.setItem(i, item);
        }
    }
    @Inject(method = "dropEquipment", at = @At("TAIL"))
    private void onDropEquipmentBottom(CallbackInfo ci){
        inventory.replaceWith(oldInventory);
    }
}
