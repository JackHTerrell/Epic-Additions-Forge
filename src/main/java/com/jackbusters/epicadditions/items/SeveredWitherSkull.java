package com.jackbusters.epicadditions.items;

import com.jackbusters.epicadditions.configurations.EpicServerConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SeveredWitherSkull extends Item {
    public SeveredWitherSkull(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        shootHead(level, player, usedHand);
        return super.use(level, player, usedHand);
    }

    private void shootHead(Level level, Player player, InteractionHand usedHand){
        if (!player.isSilent()) {
            level.levelEvent(player, 1024, player.blockPosition(), 0);
        }

        player.swing(usedHand);

        WitherSkull witherskull = new WitherSkull(level, player, player.getLookAngle().x(), player.getLookAngle().y(), player.getLookAngle().z());
        witherskull.setXRot(player.getXRot());
        witherskull.setYRot(player.getYRot());
        witherskull.setPosRaw(player.getEyePosition().x(), player.getEyePosition().y(), player.getEyePosition().z());
        witherskull.setOwner(player);

        level.addFreshEntity(witherskull);

        player.getCooldowns().addCooldown(this, EpicServerConfig.severedWitherSkullCoolDown.get()*20);
    }
}
