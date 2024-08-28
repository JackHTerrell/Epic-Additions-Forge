package com.jackbusters.epicadditions;

import com.jackbusters.epicadditions.blocks.CellBlock;
import com.jackbusters.epicadditions.enchantments.SoulTiedEnchantment;
import com.jackbusters.epicadditions.glm.EpicLootModifier;
import com.jackbusters.epicadditions.items.*;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EpicRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicAdditions.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EpicAdditions.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EpicAdditions.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), EpicAdditions.MOD_ID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EpicAdditions.MOD_ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, EpicAdditions.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> CELL_BLOCK = BLOCKS.register("cell_block",
            () -> new CellBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN)
                    .instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)
                    .noLootTable().isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never)
                    .isSuffocating(Blocks::never).isViewBlocking(Blocks::never).noOcclusion()));

    // Items
    public static final RegistryObject<Item> POCKET_DIMENSION_KEY = ITEMS.register("pocket_dimension_key", ()->
            new PocketDimensionWarpKey(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> POCKET_CELL_ASSIGNMENT_REMOVER = ITEMS.register("pocket_cell_assignment_remover", ()->
            new PocketCellAssignmentSelfRemover(new Item.Properties().stacksTo(1)));


    public static final RegistryObject<Item> POCKET_CELL_GENERATOR = ITEMS.register("pocket_cell_generator", ()->
            new PocketCellGenerator(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SEVERED_WITHER_SKULL = ITEMS.register("severed_wither_skull", ()->
            new SeveredWitherSkull(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> MASTERED_DRAGON_HEAD = ITEMS.register("mastered_dragon_head", ()->
            new MasteredDragonHead(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> DIMENSIONAL_GEL = ITEMS.register("dimensional_gel", ()->
            new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    // Block Items
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
                        output.accept(DIMENSIONAL_GEL.get());
                        output.accept(SEVERED_WITHER_SKULL.get());
                        output.accept(MASTERED_DRAGON_HEAD.get());
                    })
                    .build());

    // Enchantments
    public static final RegistryObject<Enchantment> SOUL_TIED = ENCHANTMENTS.register("soul_tied", ()-> new
            SoulTiedEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.create("epicadditions_all", (item -> true)), EquipmentSlot.values()));

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
    public static void registerEnchantments(){
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void addToExistingTabs(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey().equals(CreativeModeTabs.OP_BLOCKS)) {
            event.accept(POCKET_CELL_GENERATOR.get());
            event.accept(POCKET_CELL_ASSIGNMENT_REMOVER.get());
        }
    }
}
