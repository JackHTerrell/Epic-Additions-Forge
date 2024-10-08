package com.jackbusters.epicadditions;

import com.jackbusters.epicadditions.configurations.EpicServerConfig;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerItemColors);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerBlockColors);
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

    /*
        For adding colors to gray-scale block textures.
    */
    @SubscribeEvent
    public void registerBlockColors(RegisterColorHandlersEvent.Block event){
        BlockColor blockColor = (blockState, blockAndTintGetter, blockPos, i) -> FoliageColor.getEvergreenColor();
        event.register(blockColor, EpicRegistry.OAK_DIMENSIONAL_LEAVES.get(), EpicRegistry.SPRUCE_DIMENSIONAL_LEAVES.get(),
                EpicRegistry.BIRCH_DIMENSIONAL_LEAVES.get(), EpicRegistry.JUNGLE_DIMENSIONAL_LEAVES.get(),
                EpicRegistry.ACACIA_DIMENSIONAL_LEAVES.get(), EpicRegistry.CHERRY_DIMENSIONAL_LEAVES.get(),
                EpicRegistry.DARK_OAK_DIMENSIONAL_LEAVES.get(), EpicRegistry.AZALEA_DIMENSIONAL_LEAVES.get(),
                EpicRegistry.FLOWERING_AZALEA_DIMENSIONAL_LEAVES.get());
    }

    /*
        For adding colors to gray-scale item textures.
    */
    @SubscribeEvent
    public void registerItemColors(RegisterColorHandlersEvent.Item event){
        ItemColor itemColor = (itemStack, i) -> FoliageColor.getEvergreenColor();
        event.register(itemColor, EpicRegistry.OAK_DIMENSIONAL_LEAVES_ITEM.get(), EpicRegistry.SPRUCE_DIMENSIONAL_LEAVES_ITEM.get(),
                EpicRegistry.BIRCH_DIMENSIONAL_LEAVES_ITEM.get(), EpicRegistry.JUNGLE_DIMENSIONAL_LEAVES_ITEM.get(),
                EpicRegistry.ACACIA_DIMENSIONAL_LEAVES_ITEM.get(), EpicRegistry.CHERRY_DIMENSIONAL_LEAVES_ITEM.get(),
                EpicRegistry.DARK_OAK_DIMENSIONAL_LEAVES_ITEM.get(), EpicRegistry.AZALEA_DIMENSIONAL_LEAVES_ITEM.get(),
                EpicRegistry.FLOWERING_AZALEA_DIMENSIONAL_LEAVES_ITEM.get());
    }
}