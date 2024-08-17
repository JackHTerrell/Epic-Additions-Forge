package com.jackbusters.epicjourney;

import com.jackbusters.epicjourney.glm.EpicLootModifier;
import com.jackbusters.epicjourney.items.PocketDimensionWarpKey;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EpicRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicJourney.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EpicJourney.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), EpicJourney.MOD_ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, EpicJourney.MOD_ID);

    // Items
    public static final RegistryObject<Item> POCKET_DIMENSION_KEY = ITEMS.register("pocket_dimension_key", ()->
            new PocketDimensionWarpKey(new Item.Properties()));

    // Creative Mode Tabs
    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab", ()->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.main.epicjourney"))
                    .icon(()-> new ItemStack(POCKET_DIMENSION_KEY.get()))
                    .withSearchBar()
                    .displayItems((enabledFeatures, output) -> {
                        output.accept(POCKET_DIMENSION_KEY.get());
                    })
                    .build());

    // GLM
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_DROP = LOOT_MODIFIERS.register("add_drop", EpicLootModifier.CODEC);

    public static void registerItems(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public static void registerEntities(){
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public static void registerCreativeModeTabs(){
        CREATIVE_MODE_TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public static void registerLootModifiers(){
        LOOT_MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
