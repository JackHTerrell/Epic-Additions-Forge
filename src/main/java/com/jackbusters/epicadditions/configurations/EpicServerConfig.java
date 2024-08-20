package com.jackbusters.epicadditions.configurations;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class EpicServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> pocketCellDimensions;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Server Config");

        pocketCellDimensions = BUILDER.comment("Determines the dimensions of the Pocket Cells in the Pocket Dimension. Must be an odd number greater than 1 and less than 33. For example, a value of 17 will result in 17x17x17 cells.").worldRestart().defineList("pocketCellDimensions", List.of(17), o -> o instanceof Integer val && (val.doubleValue() % 2 != 0) && val>2 && val<32); // Is a list so that I can create a custom predicate. Need value to only be odd numbers for proper function.

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
