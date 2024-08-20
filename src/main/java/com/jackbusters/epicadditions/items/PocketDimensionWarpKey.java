package com.jackbusters.epicadditions.items;

import com.jackbusters.epicadditions.EpicAdditions;
import com.jackbusters.epicadditions.EpicRegistry;
import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellLevelDataProvider;
import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellProvider;
import com.jackbusters.epicadditions.constructs.PocketCell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class PocketDimensionWarpKey extends BowItem {
    private final Predicate<ItemStack> EVERYTHING = (itemStack) -> true;

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

    /*
        Will assure the key correctly teleports the player to and from their Pocket Dimension cell.
     */
    public void sendToProperDimension(LivingEntity entityUsing, ServerLevel pocketDimension, ServerLevel playerDimension, ServerLevel overworld){
        if(playerDimension != null && overworld != null && playerDimension.equals(pocketDimension))
            teleportFromPocketCell(entityUsing, overworld);
        else if(pocketDimension != null) teleportToPocketCell(entityUsing, pocketDimension);
    }

    public void teleportFromPocketCell(LivingEntity entityUsing, ServerLevel overworld) {
        entityUsing.changeDimension(overworld, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> {
                    data.setHasPocketCell(false); //TODO dont forget to remove this.
                });
                return ITeleporter.super.placeEntity(entity, currentWorld, destWorld, yaw, repositionEntity);
            }
        });
    }

    public void teleportToPocketCell(LivingEntity entityUsing, ServerLevel pocketDimension){
        entityUsing.changeDimension(pocketDimension, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> {
                    if(!data.doesHavePocketCell()) {
                        PocketCell.buildNewPocketCell(EpicRegistry.CELL_BLOCK.get(), data.getPocketCellLevel(), destWorld, entity);
                    }
                    pocketDimension.getCapability(PocketCellLevelDataProvider.POCKET_CELL_LEVEL_DATA).ifPresent(levelData -> {
                        BlockPos posOfCell = levelData.getTangibleCellLocations().get(data.getPocketCellIndex());
                        System.out.println("Position of your cell is: "+posOfCell+". Your PC level is: "+data.getPocketCellLevel());
                        Entity toPosEntity = repositionEntity.apply(false);
                        if(toPosEntity instanceof ServerPlayer serverPlayer){
                            serverPlayer.teleportTo(posOfCell.getX(), posOfCell.getY()+1, posOfCell.getZ());
                        }
                    });
                });
                return ITeleporter.super.placeEntity(entity, currentWorld, destWorld, yaw, repositionEntity);
            }
        });
    }

    /*
        Want to mimic teleport command, keep movement
     */
    private Set<RelativeMovement> getMovementSet(){
        Set<RelativeMovement> set = EnumSet.noneOf(RelativeMovement.class);

        set.add(RelativeMovement.X);
        set.add(RelativeMovement.Y);
        set.add(RelativeMovement.Z);
        set.add(RelativeMovement.X_ROT);
        set.add(RelativeMovement.Y_ROT);

        return set;
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
