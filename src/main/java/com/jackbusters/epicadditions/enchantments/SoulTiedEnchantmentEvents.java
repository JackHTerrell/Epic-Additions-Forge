package com.jackbusters.epicadditions.enchantments;

import com.jackbusters.epicadditions.EpicAdditions;
import com.jackbusters.epicadditions.EpicRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicAdditions.MOD_ID)
public class SoulTiedEnchantmentEvents {

    /*
        Will check player's inventory for soul-tied items and assure they're returned to new player as they were upon respawn.
     */
    @SubscribeEvent
    public static void keepSoulTiedOnRespawn(PlayerEvent.Clone event){
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        if(event.isWasDeath() && !(newPlayer.level().getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get())){
             for(int i = 0; i < originalPlayer.getInventory().getContainerSize(); ++i){
                 ItemStack item = originalPlayer.getInventory().getItem(i);
                if(item.getEnchantmentLevel(EpicRegistry.SOUL_TIED.get()) > 0){
                    newPlayer.getInventory().add(i, item);
                }
            }
        }
    }

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
