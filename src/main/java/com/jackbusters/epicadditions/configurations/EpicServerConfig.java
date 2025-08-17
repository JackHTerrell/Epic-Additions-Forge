package com.jackbusters.epicadditions.configurations;

import net.minecraftforge.common.ForgeConfigSpec;

public class EpicServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Integer> severedWitherSkullCoolDown;
    public static final ForgeConfigSpec.ConfigValue<Integer> masteredDragonHeadCoolDown;
    public static final ForgeConfigSpec.ConfigValue<Integer> pocketCellKeyCoolDown;
    public static final ForgeConfigSpec.ConfigValue<Boolean> isSoftSteppingEnchantmentTradeable;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Server Config");

        severedWitherSkullCoolDown = BUILDER.comment("The amount of time, in seconds, the cooldown on the Severed Wither Skull lasts.").defineInRange("severedWitherSkullCoolDown", 7, 0, 90);
        masteredDragonHeadCoolDown = BUILDER.comment("The amount of time, in seconds, the cooldown on the Mastered Dragon Head lasts.").defineInRange("masteredDragonHeadCoolDown", 7, 0, 90);
        pocketCellKeyCoolDown = BUILDER.comment("The amount of time the, in seconds, cooldown on the Pocket Cell Key lasts.").defineInRange("pocketCellKeyCoolDown", 10, 0, 90);
        isSoftSteppingEnchantmentTradeable = BUILDER.comment("If true, Soft Stepping enchantment will sometimes appear as a trade for librarian villagers. Set to false to disable.").define("isSoftSteppingEnchantmentTradeable", true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
