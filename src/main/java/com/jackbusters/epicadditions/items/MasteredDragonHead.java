package com.jackbusters.epicadditions.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MasteredDragonHead extends Item {
    public MasteredDragonHead(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        sprayFire(level, player);
        return super.use(level, player, usedHand);
    }

    private void sprayFire(Level level, Player player){
        DragonFireball dragonFireball = new DragonFireball(level, player, player.getLookAngle().x(), player.getLookAngle().y(), player.getLookAngle().z());
        dragonFireball.setOwner(player);
        dragonFireball.setPosRaw(player.getEyePosition().x(), player.getEyePosition().y(), player.getEyePosition().z());
        level.addFreshEntity(dragonFireball);
    }
}