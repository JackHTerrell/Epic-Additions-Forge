package com.jackbusters.epicadditions.capabilities.pocketcells;

import com.jackbusters.epicadditions.EpicAdditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicAdditions.MOD_ID)
public class PocketCellEvents {
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if(entity instanceof Player && !entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).isPresent())
            event.addCapability(new ResourceLocation(EpicAdditions.MOD_ID, "properties"), new PocketCellProvider());
    }

    @SubscribeEvent
    public static void keepPocketDataOnDeath(PlayerEvent.Clone event){
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        if(event.isWasDeath())
            originalPlayer.getCapability(PocketCellProvider.POCKET_CELL_DATA).
                    ifPresent(oldData -> newPlayer.getCapability(PocketCellProvider.POCKET_CELL_DATA).
                            ifPresent(newData -> newData.copyFrom(oldData)));
    }
}
