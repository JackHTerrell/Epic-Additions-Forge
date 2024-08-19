package com.jackbusters.epicadditions.capabilities.pocketcells;

import com.jackbusters.epicadditions.EpicAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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
            event.addCapability(new ResourceLocation(EpicAdditions.MOD_ID, "pocket_dimension_data"), new PocketCellProvider());
    }

    @SubscribeEvent
    public static void attachLevelCapability(AttachCapabilitiesEvent<Level> event){
        if(event.getObject() instanceof ServerLevel level) {
            ResourceKey<Level> pocketDimensionKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(EpicAdditions.MOD_ID, "pocket"));
            if (level.dimension().equals(pocketDimensionKey)) {
                event.addCapability(new ResourceLocation(EpicAdditions.MOD_ID, "pocket_dimension_data"), new PocketCellLevelDataProvider());
            }
        }
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
