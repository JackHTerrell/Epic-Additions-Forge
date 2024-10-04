package com.jackbusters.epicadditions.configurations;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class EpicServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Integer> severedWitherSkullCoolDown;
    public static final ForgeConfigSpec.ConfigValue<Integer> masteredDragonHeadCoolDown;
    public static final ForgeConfigSpec.ConfigValue<Integer> pocketCellKeyCoolDown;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Server Config");

        // pocketCellDimensions = BUILDER.comment("Determines the dimensions of the Pocket Cells in the Pocket Dimension. Must be an odd number greater than 1 and less than 33. For example, a value of 17 will result in 17x17x17 cells.").worldRestart().defineList("pocketCellDimensions", List.of(17), o -> o instanceof Integer val && (val.doubleValue() % 2 != 0) && val>2 && val<32); // Is a list so that I can create a custom predicate. Need value to only be odd numbers for proper function.

        severedWitherSkullCoolDown = BUILDER.comment("The amount of time, in seconds, the cooldown on the Severed Wither Skull lasts.").defineInRange("severedWitherSkullCoolDown", 7, 0, 90);
        masteredDragonHeadCoolDown = BUILDER.comment("The amount of time, in seconds, the cooldown on the Mastered Dragon Head lasts.").defineInRange("masteredDragonHeadCoolDown", 7, 0, 90);
        pocketCellKeyCoolDown = BUILDER.comment("The amount of time the, in seconds, cooldown on the Pocket Cell Key lasts.").defineInRange("pocketCellKeyCoolDown", 10, 0, 90);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
