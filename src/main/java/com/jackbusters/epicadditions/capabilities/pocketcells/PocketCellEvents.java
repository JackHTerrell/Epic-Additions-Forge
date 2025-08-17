package com.jackbusters.epicadditions.capabilities.pocketcells;

import com.jackbusters.epicadditions.EpicAdditions;
import com.jackbusters.epicadditions.packets.EpicPacketHandler;
import com.jackbusters.epicadditions.packets.S2CSyncPocketData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;


@Mod.EventBusSubscriber(modid = EpicAdditions.MOD_ID)
public class PocketCellEvents {

    /**
     * If a player does not already have Pocket Dimension Data capabilities, assign it to the player.
     * @param event Event passed by Forge
     */
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if(entity instanceof Player && !entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).isPresent())
            event.addCapability(ResourceLocation.fromNamespaceAndPath(EpicAdditions.MOD_ID, "pocket_dimension_data"), new PocketCellProvider());
    }

    /**
     * Synchronizes the Pocket Cell level data so that the Pocket Dimension Key can properly display the level in the tooltip.
     * @param event Event passed by Forge
     */
    @SubscribeEvent
    public static void synchronizePocketLevelOnLogin(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();

        if(player instanceof ServerPlayer serverPlayer){
            player.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data ->
                    EpicPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new S2CSyncPocketData(data.getPocketCellLevel()))
            );
        }
    }

    /**
     * Synchronizes the Pocket Cell level data so that the Pocket Dimension Key can properly display the level in the tooltip.
     * @param event Event passed by Forge
     */
    @SubscribeEvent
    public static void synchronizePocketLevelOnRespawn(PlayerEvent.PlayerRespawnEvent event){
        Player player = event.getEntity();

        if(player instanceof ServerPlayer serverPlayer){
            player.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data ->
                    EpicPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new S2CSyncPocketData(data.getPocketCellLevel()))
            );
        }
    }

    /**
     * Synchronizes the Pocket Cell level data so that the Pocket Dimension Key can properly display the level in the tooltip.
     * @param event Event passed by Forge
     */
    @SubscribeEvent
    public static void synchronizePocketLevelOnChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event){
        Player player = event.getEntity();

        if(player instanceof ServerPlayer serverPlayer){
            player.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data ->
                    EpicPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new S2CSyncPocketData(data.getPocketCellLevel()))
            );
        }
    }

    /**
     * If a dimension is the Pocket Dimension, assign it Pocket Dimension capabilities.
     * @param event Event passed by Forge
     */
    @SubscribeEvent
    public static void attachLevelCapability(AttachCapabilitiesEvent<Level> event){
        if(event.getObject() instanceof ServerLevel level) {
            ResourceKey<Level> pocketDimensionKey = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(EpicAdditions.MOD_ID, "pocket"));
            if (level.dimension().equals(pocketDimensionKey)) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(EpicAdditions.MOD_ID, "pocket_dimension_data"), new PocketCellLevelDataProvider());
            }
        }
    }

    /**
     * When a player respawns upon death or leaves & returns to a world, that player's old capability data is re-assigned
     * to them so that it is not lost.
     * @param event Event passed by Forge
     */
    @SubscribeEvent
    public static void keepPocketDataOnDeath(PlayerEvent.Clone event) {
        Player originalPlayer = event.getOriginal();
        originalPlayer.reviveCaps();
        Player newPlayer = event.getEntity();
        originalPlayer.getCapability(PocketCellProvider.POCKET_CELL_DATA).
                ifPresent(oldData -> newPlayer.getCapability(PocketCellProvider.POCKET_CELL_DATA).
                        ifPresent(newData -> newData.copyFrom(oldData)));
        originalPlayer.invalidateCaps();
    }
}
