package com.jackbusters.epicadditions.dimensioneffects;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * A class to provide special effects to the Pocket Dimension.<br>
 * Necessary for several custom combos of variables only describing the Pocket.
 *
 * <ul>
 *     <li>No clouds</li>
 *     <li>No ground</li>
 *     <li>NONE sky type (No celestial bodies, sky is slightly brighter shade of blue)</li>
 *     <li>No Forced Bright Light map</li>
 *     <li>Constant Ambient Light</li>
 *     <li>No fog</li>
 * </ul>
 */
public class PocketDimensionEffect extends DimensionSpecialEffects {
    public PocketDimensionEffect() {
        super(Float.NaN, false, SkyType.NONE, false, true);
    }

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 vec3, float v) {
        return vec3.multiply(v * 0.94F + 0.06F, v * 0.94F + 0.06F, v * 0.91F + 0.09F); // Same as overworld (See DimensionSpecialEffects)
    }

    @Override
    public boolean isFoggyAt(int i, int i1) {
        return false;
    }
}
