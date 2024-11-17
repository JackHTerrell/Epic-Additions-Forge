package com.jackbusters.epicadditions;

import com.jackbusters.epicadditions.configurations.EpicServerConfig;
import com.jackbusters.epicadditions.dimensioneffects.PocketDimensionEffect;
import com.jackbusters.epicadditions.packets.EpicPacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod(EpicAdditions.MOD_ID)
public class EpicAdditions {
    public static final String MOD_ID = "epicadditions";
    private final FMLJavaModLoadingContext modLoadingContext;

    public EpicAdditions(FMLJavaModLoadingContext modLoadingContext){
        this.modLoadingContext=modLoadingContext;
        modLoadingContext.getModEventBus().addListener(this::commonSetupEvent);
        modLoadingContext.getModEventBus().addListener(this::registerRegistriesEvent);
        modLoadingContext.getModEventBus().addListener(this::registerDimensionEffectsEvent);
        modLoadingContext.getModEventBus().addListener(EpicRegistry::addToExistingTabs);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, EpicServerConfig.SPEC);
    }

    public void commonSetupEvent(final FMLCommonSetupEvent event) {
        event.enqueueWork(EpicPacketHandler::registerPackets);
    }

    public void registerRegistriesEvent(final NewRegistryEvent event){
        EpicRegistry.registerMobEffects(modLoadingContext);
        EpicRegistry.registerItems(modLoadingContext);
        EpicRegistry.registerEntities(modLoadingContext);
        EpicRegistry.registerCreativeModeTabs(modLoadingContext);
        EpicRegistry.registerLootModifiers(modLoadingContext);
        EpicRegistry.registerBlocks(modLoadingContext);
        EpicRegistry.registerEnchantments(modLoadingContext);
    }

    public void registerDimensionEffectsEvent(final RegisterDimensionSpecialEffectsEvent event){
        event.register(new ResourceLocation(MOD_ID, "pocket"),new PocketDimensionEffect());
    }
}