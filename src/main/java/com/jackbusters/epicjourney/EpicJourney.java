package com.jackbusters.epicjourney;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod(EpicJourney.MOD_ID)
public class EpicJourney {
    public static final String MOD_ID = "epicjourney";

    public EpicJourney(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::RegisterRegistries);
    }

    public void RegisterRegistries(final NewRegistryEvent event){
        EpicRegistry.registerItems();
        EpicRegistry.registerEntities();
        EpicRegistry.registerCreativeModeTabs();
        EpicRegistry.registerLootModifiers();
    }
}