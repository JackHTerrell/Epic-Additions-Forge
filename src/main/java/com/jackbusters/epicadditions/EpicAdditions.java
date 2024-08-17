package com.jackbusters.epicadditions;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod(EpicAdditions.MOD_ID)
public class EpicAdditions {
    public static final String MOD_ID = "epicadditions";

    public EpicAdditions(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::RegisterRegistries);
    }

    public void RegisterRegistries(final NewRegistryEvent event){
        EpicRegistry.registerItems();
        EpicRegistry.registerEntities();
        EpicRegistry.registerCreativeModeTabs();
        EpicRegistry.registerLootModifiers();
    }
}