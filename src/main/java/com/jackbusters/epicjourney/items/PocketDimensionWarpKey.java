package com.jackbusters.epicjourney.items;

import com.jackbusters.epicjourney.EpicJourney;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Predicate;

public class PocketDimensionWarpKey extends BowItem {
    Predicate<ItemStack> EVERYTHING = (itemStack) -> true;

    public PocketDimensionWarpKey(Properties properties) {
        super(properties);
    }


    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level level, @NotNull LivingEntity playerUsing, int timeCharged) {
        if(level instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftServer = serverLevel.getServer();

            ResourceKey<Level> pocketDimensionKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(EpicJourney.MOD_ID, "pocket"));
            ServerLevel pocketDimension = minecraftServer.getLevel(pocketDimensionKey);
            ServerLevel playerDimension = minecraftServer.getLevel(serverLevel.dimension());
            ServerLevel overworld = minecraftServer.getLevel(Level.OVERWORLD);
            sendToProperDimension(playerUsing, pocketDimension, playerDimension, overworld);
        }
    }

//    @Override
//    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player playerUsing, @NotNull InteractionHand usedHand) {
//        if(level instanceof ServerLevel serverLevel) {
//            MinecraftServer minecraftServer = serverLevel.getServer();
//
//            ResourceKey<Level> pocketDimensionKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(EpicJourney.MOD_ID, "pocket"));
//            ServerLevel pocketDimension = minecraftServer.getLevel(pocketDimensionKey);
//            ServerLevel playerDimension = minecraftServer.getLevel(serverLevel.dimension());
//            ServerLevel overworld = minecraftServer.getLevel(Level.OVERWORLD);
//            sendToProperDimension(playerUsing, pocketDimension, playerDimension, overworld);
//        }
//        return super.use(level, playerUsing, usedHand);
//    }

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
                return ITeleporter.super.placeEntity(entity, currentWorld, destWorld, yaw, repositionEntity);
            }
        });
    }

    public void teleportToPocketCell(LivingEntity entityUsing, ServerLevel pocketDimension){
        entityUsing.changeDimension(pocketDimension, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                return ITeleporter.super.placeEntity(entity, currentWorld, destWorld, yaw, repositionEntity);
            }
        });
    }

    /*
        Piggy-Backing off the bow to provide a useful Random Things-like animation to using the Pocket Dimension key
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
