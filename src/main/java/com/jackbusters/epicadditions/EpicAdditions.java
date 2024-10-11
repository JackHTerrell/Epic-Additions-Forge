package com.jackbusters.epicadditions;

import com.jackbusters.epicadditions.configurations.EpicServerConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod(EpicAdditions.MOD_ID)
public class EpicAdditions {
    public static final String MOD_ID = "epicadditions";
    private final FMLJavaModLoadingContext modLoadingContext;

    public EpicAdditions(FMLJavaModLoadingContext modLoadingContext){
        this.modLoadingContext=modLoadingContext;
        modLoadingContext.getModEventBus().addListener(this::registerRegistries);
        modLoadingContext.getModEventBus().addListener(EpicRegistry::addToExistingTabs);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, EpicServerConfig.SPEC);
    }

    public void registerRegistries(final NewRegistryEvent event){
        EpicRegistry.registerItems(modLoadingContext);
        EpicRegistry.registerEntities(modLoadingContext);
        EpicRegistry.registerCreativeModeTabs(modLoadingContext);
        EpicRegistry.registerLootModifiers(modLoadingContext);
        EpicRegistry.registerBlocks(modLoadingContext);
        EpicRegistry.registerEnchantments(modLoadingContext);
    }
}