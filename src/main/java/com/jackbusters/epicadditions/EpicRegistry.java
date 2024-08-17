package com.jackbusters.epicadditions;

import com.jackbusters.epicadditions.glm.EpicLootModifier;
import com.jackbusters.epicadditions.items.PocketDimensionWarpKey;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EpicRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicAdditions.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EpicAdditions.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EpicAdditions.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), EpicAdditions.MOD_ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, EpicAdditions.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> CELL_BLOCK = BLOCKS.register("cell_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn(Blocks::never).noOcclusion()));

    // Items
    public static final RegistryObject<Item> POCKET_DIMENSION_KEY = ITEMS.register("pocket_dimension_key", ()->
            new PocketDimensionWarpKey(new Item.Properties()));

    public static final RegistryObject<BlockItem> CELL_BLOCK_ITEM = ITEMS.register("cell_block", ()->
            new BlockItem(CELL_BLOCK.get(), new Item.Properties()));

    // Creative Mode Tabs
    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab", ()->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.main.epicadditions"))
                    .icon(()-> new ItemStack(POCKET_DIMENSION_KEY.get()))
                    .withSearchBar()
                    .displayItems((enabledFeatures, output) -> {
                        output.accept(POCKET_DIMENSION_KEY.get());
                        output.accept(CELL_BLOCK_ITEM.get());
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
    public static void registerBlocks(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
