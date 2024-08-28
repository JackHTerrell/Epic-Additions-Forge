package com.jackbusters.epicadditions;

import com.jackbusters.epicadditions.configurations.EpicServerConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod(EpicAdditions.MOD_ID)
public class EpicAdditions {
    public static final String MOD_ID = "epicadditions";

    public EpicAdditions(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerRegistries);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EpicRegistry::addToExistingTabs);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, EpicServerConfig.SPEC);
    }

    public void registerRegistries(final NewRegistryEvent event){
        EpicRegistry.registerItems();
        EpicRegistry.registerEntities();
        EpicRegistry.registerCreativeModeTabs();
        EpicRegistry.registerLootModifiers();
        EpicRegistry.registerBlocks();
        EpicRegistry.registerEnchantments();
    }
}