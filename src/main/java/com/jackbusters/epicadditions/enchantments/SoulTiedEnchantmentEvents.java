package com.jackbusters.epicadditions.enchantments;

import com.jackbusters.epicadditions.EpicAdditions;
import com.jackbusters.epicadditions.EpicRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicAdditions.MOD_ID)
public class SoulTiedEnchantmentEvents {

    /*
        Prevents soul-tied objects from dropping, which would cause duplication.
     */
    @SubscribeEvent
    public static void preventSoulTiedFromDropping(LivingDropsEvent event){
        LivingEntity livingEntity = event.getEntity();
        if(livingEntity instanceof Player){
            event.getDrops().removeIf(itemEntity -> itemEntity.getItem().getEnchantmentLevel(EpicRegistry.SOUL_TIED.get()) > 0);
        }
    }
}
