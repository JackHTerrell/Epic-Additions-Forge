package com.jackbusters.epicadditions.items;

import com.jackbusters.epicadditions.EpicAdditions;
import com.jackbusters.epicadditions.EpicRegistry;
import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellLevelDataProvider;
import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellProvider;
import com.jackbusters.epicadditions.configurations.EpicServerConfig;
import com.jackbusters.epicadditions.constructs.PocketCell;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = EpicAdditions.MOD_ID)
public class PocketDimensionWarpKey extends BowItem {
    private final Predicate<ItemStack> EVERYTHING = (itemStack) -> true;
    Logger logger = LogUtils.getLogger();

    public PocketDimensionWarpKey(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level level, @NotNull LivingEntity playerUsing, int timeCharged) {
        if(level instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftServer = serverLevel.getServer();
            ResourceKey<Level> pocketDimensionKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(EpicAdditions.MOD_ID, "pocket"));
            ServerLevel pocketDimension = minecraftServer.getLevel(pocketDimensionKey);
            ServerLevel playerDimension = minecraftServer.getLevel(serverLevel.dimension());
            ServerLevel overworld = minecraftServer.getLevel(Level.OVERWORLD);
            sendToProperDimension(playerUsing, pocketDimension, playerDimension, overworld);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, @NotNull TooltipFlag pIsAdvanced) {
        tooltip.add(Component.translatable("hovertext.item.pocket_key").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(pStack, pLevel, tooltip, pIsAdvanced);
    }

    /*
        Tooltip for displaying level of player's Pocket Cell
    */
    @SubscribeEvent
    public static void tooltipEvent(final ItemTooltipEvent event){
        Player player = event.getEntity();
        if(player!=null && event.getItemStack().is(EpicRegistry.POCKET_DIMENSION_KEY.get())) {
            player.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> event.getToolTip().add(2, Component.translatable("hovertext.item.pocket_key.current_level", data.getPocketCellLevel())));
        }
    }

    /*
                Will assure the key correctly teleports the player to and from their Pocket Dimension cell.
             */
    public void sendToProperDimension(LivingEntity entityUsing, ServerLevel pocketDimension, ServerLevel playerDimension, ServerLevel overworld){
        if(playerDimension != null && overworld != null && playerDimension.equals(pocketDimension))
            teleportFromPocketCell(entityUsing, overworld);
        else if(pocketDimension != null) teleportToPocketCell(entityUsing, pocketDimension);
    }

    public void teleportFromPocketCell(LivingEntity entityUsing, ServerLevel overworld) {
        entityUsing.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> {
            ServerLevel fromDim = Objects.requireNonNull(entityUsing.getServer()).getLevel(data.getLeftDimensionId());
            if (data.getLeftPos() != null && fromDim != null) { // Respawn the player at their previous spot
                entityUsing.changeDimension(fromDim, new ITeleporter() {
                    @Override
                    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                        ServerPlayer toPosEntity = (ServerPlayer) repositionEntity.apply(false);
                        toPosEntity.getCooldowns().addCooldown(EpicRegistry.POCKET_DIMENSION_KEY.get(), EpicServerConfig.pocketCellKeyCoolDown.get()*20); // Prevent pocket cell spamming with a cooldown
                        data.setLeftPocketCellPos(new Vec3(toPosEntity.getX(), toPosEntity.getY(), toPosEntity.getZ()));
                        data.setLeftPocketCellYaw(toPosEntity.getYRot());
                        data.setLeftPocketCellPitch(toPosEntity.getXRot());
                        toPosEntity.fallDistance = data.getWasFallingDistance(); // Assures fall distance is not lost if leaving and going back from pocket dimension
                        toPosEntity.setDeltaMovement(data.getWasDeltaMovement()); // Assures momentum is maintained if leaving and coming back from pocket dimension.
                        toPosEntity.teleportTo(fromDim ,data.getLeftPos().x, data.getLeftPos().y, data.getLeftPos().z, data.getLeftYaw(), data.getLeftPitch());
                        return toPosEntity;
                    }

                    @Override
                    public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
                        return false;
                    }
                });
            }
            else { // If for some reason there is a failure to respawn the player at their previous location, they should instead spawn at their spawnpoint
                if(entityUsing instanceof ServerPlayer serverPlayer) {
                    ServerLevel respawnDimension = serverPlayer.server.getLevel(serverPlayer.getRespawnDimension());

                    if(respawnDimension == null){
                        respawnDimension = overworld; // Assures player will always have a place to spawn
                    }
                    ServerLevel finalRespawnDimension = respawnDimension; // For use in placeEntity.
                    serverPlayer.changeDimension(respawnDimension, new ITeleporter() {
                        @Override
                        public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                            ServerPlayer toPosEntity = (ServerPlayer) repositionEntity.apply(false);
                            toPosEntity.getCooldowns().addCooldown(EpicRegistry.POCKET_DIMENSION_KEY.get(), EpicServerConfig.pocketCellKeyCoolDown.get()*20); // Prevent pocket cell spamming with a cooldown
                            BlockPos respawnPoint = toPosEntity.getRespawnPosition();
                            if(respawnPoint == null){
                                respawnPoint = finalRespawnDimension.getSharedSpawnPos(); // If the player doesn't have a respawn point, send him to world spawn.
                            }
                            toPosEntity.teleportTo(respawnPoint.getX(), respawnPoint.getY(), respawnPoint.getZ());
                            logger.warn("Epic Additions: A player was respawned at their set spawn point, rather than their previous position. This should never happen and was included as a precaution against being stuck in the Pocket Cell. " +
                                    "Please let the mod developer know the context so he can fix the bug.");
                            return toPosEntity;
                        }

                        @Override
                        public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
                            return false;
                        }
                    });

                }
            }
        });
    }

    public void teleportToPocketCell(LivingEntity entityUsing, ServerLevel pocketDimension){
        entityUsing.changeDimension(pocketDimension, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                Entity toPosEntity = repositionEntity.apply(false);
                if (toPosEntity instanceof ServerPlayer serverPlayer)
                    serverPlayer.getCooldowns().addCooldown(EpicRegistry.POCKET_DIMENSION_KEY.get(), EpicServerConfig.pocketCellKeyCoolDown.get()*20); // Prevent pocket cell spamming with a cooldown
                toPosEntity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> {
                    if(!data.doesHavePocketCell()) {
                        PocketCell.buildNewPocketCell(EpicRegistry.CELL_BLOCK.get(), data.getPocketCellLevel(), destWorld, toPosEntity);
                        pocketDimension.getCapability(PocketCellLevelDataProvider.POCKET_CELL_LEVEL_DATA).ifPresent(levelData -> {
                            BlockPos posOfCell = levelData.getOccupiedCellLocations().get(data.getPocketCellIndex());
                            data.setLeftPocketCellPos(new Vec3(posOfCell.getX(), posOfCell.getY()+1, posOfCell.getZ()));
                        });

                    }
                    data.setLeftDimensionId(currentWorld.dimension());
                    data.setLeftPos(toPosEntity.position());
                    data.setLeftYaw(toPosEntity.getYRot());
                    data.setLeftPitch(toPosEntity.getXRot());
                    data.setWasFallingDistance(toPosEntity.fallDistance);
                    data.setWasDeltaMovement(toPosEntity.getDeltaMovement());
                    pocketDimension.getCapability(PocketCellLevelDataProvider.POCKET_CELL_LEVEL_DATA).ifPresent(levelData -> {
                        Vec3 posInPocket = data.getLeftPocketCellPos();
                        BlockPos posOfCell = levelData.getOccupiedCellLocations().get(data.getPocketCellIndex());
                        int universalPocketSides = 17;
                        int distance = (int) posInPocket.distanceTo(new Vec3(posOfCell.getX(), posInPocket.y(), posOfCell.getZ()));
                        if(distance > ((universalPocketSides /2)+1)){
                            posInPocket = new Vec3(posOfCell.getX(), posOfCell.getY()+1, posOfCell.getZ());
                        }
                        if(toPosEntity instanceof ServerPlayer serverPlayer){
                            serverPlayer.setDeltaMovement(Vec3.ZERO);
                            serverPlayer.fallDistance=0;
                            serverPlayer.teleportTo(pocketDimension, posInPocket.x(), posInPocket.y(), posInPocket.z(), data.getLeftPocketCellYaw(), data.getLeftPocketCellPitch());
                        }
                    });
                });
                return toPosEntity;
            }

            @Override
            public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
                return false;
            }
        });
    }

    /*
        Piggy-Backing off the bow to provide a useful "Random Things"-like animation to using the Pocket Dimension key
     */
    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return EVERYTHING;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 0;
    }
}
