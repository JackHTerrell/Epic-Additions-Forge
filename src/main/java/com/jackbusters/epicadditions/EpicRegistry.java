package com.jackbusters.epicadditions;

import com.jackbusters.epicadditions.blocks.CellBlock;
import com.jackbusters.epicadditions.enchantments.SoulTiedEnchantment;
import com.jackbusters.epicadditions.glm.EpicLootModifier;
import com.jackbusters.epicadditions.items.*;
import com.jackbusters.epicadditions.statuseffects.UpgradePocketCellEffect;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.*;
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
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EpicAdditions.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EpicAdditions.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), EpicAdditions.MOD_ID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EpicAdditions.MOD_ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, EpicAdditions.MOD_ID);

    // Mob Effects
    public static final RegistryObject<MobEffect> UPGRADE_POCKET_CELL = MOB_EFFECTS.register("upgrade_pocket_cell", ()-> new UpgradePocketCellEffect(MobEffectCategory.BENEFICIAL, 0x000000));

    // Blocks
    public static final RegistryObject<Block> CELL_BLOCK = BLOCKS.register("cell_block",
            () -> new CellBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN)
                    .instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)
                    .noLootTable().isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never)
                    .isSuffocating(Blocks::never).isViewBlocking(Blocks::never).noOcclusion()));

    public static final RegistryObject<Block> DIMENSIONAL_ORE = BLOCKS.register("dimensional_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
    public static final RegistryObject<Block> DEEPSLATE_DIMENSIONAL_ORE = BLOCKS.register("deepslate_dimensional_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(DIMENSIONAL_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));

    // Items
    public static final RegistryObject<Item> POCKET_DIMENSION_KEY = ITEMS.register("pocket_dimension_key", ()->
            new PocketDimensionWarpKey(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> POCKET_CELL_ASSIGNMENT_REMOVER = ITEMS.register("pocket_cell_assignment_remover", ()->
            new PocketCellAssignmentSelfRemover(new Item.Properties().stacksTo(1)));


    public static final RegistryObject<Item> POCKET_CELL_GENERATOR = ITEMS.register("pocket_cell_generator", ()->
            new PocketCellGenerator(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SEVERED_WITHER_SKULL = ITEMS.register("severed_wither_skull", ()->
            new SeveredWitherSkull(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> MASTERED_DRAGON_HEAD = ITEMS.register("mastered_dragon_head", ()->
            new MasteredDragonHead(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> DIMENSIONAL_GEM = ITEMS.register("dimensional_gem", ()->
            new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> DIMENSIONAL_APPLE = ITEMS.register("dimensional_apple", ()->
            new DimensionalAppleItem(new Item.Properties().rarity(Rarity.RARE).food(new FoodProperties.Builder().alwaysEat().nutrition(4).saturationMod(0.3F).effect(()->new MobEffectInstance(UPGRADE_POCKET_CELL.get(), 1, 1, false, false, false), 1).build())));

    // Block Items
    public static final RegistryObject<BlockItem> CELL_BLOCK_ITEM = ITEMS.register("cell_block", ()->
            new BlockItem(CELL_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<BlockItem> DIMENSIONAL_ORE_ITEM = ITEMS.register("dimensional_ore", ()->
            new BlockItem(DIMENSIONAL_ORE.get(), new Item.Properties()));

    public static final RegistryObject<BlockItem> DEEPSLATE_DIMENSIONAL_ORE_ITEM = ITEMS.register("deepslate_dimensional_ore", ()->
            new BlockItem(DEEPSLATE_DIMENSIONAL_ORE.get(), new Item.Properties()));

    // Creative Mode Tabs
    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab", ()->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.main.epicadditions"))
                    .icon(()-> new ItemStack(POCKET_DIMENSION_KEY.get()))
                    .withSearchBar()
                    .displayItems((enabledFeatures, output) -> {
                        output.accept(POCKET_DIMENSION_KEY.get());
                        output.accept(CELL_BLOCK_ITEM.get());
                        output.accept(DIMENSIONAL_ORE_ITEM.get());
                        output.accept(DEEPSLATE_DIMENSIONAL_ORE_ITEM.get());
                        output.accept(DIMENSIONAL_GEM.get());
                        output.accept(DIMENSIONAL_APPLE.get());
                        output.accept(SEVERED_WITHER_SKULL.get());
                        output.accept(MASTERED_DRAGON_HEAD.get());
                        ItemStack soulTiedBook = new ItemStack(Items.ENCHANTED_BOOK);
                        EnchantedBookItem.addEnchantment(soulTiedBook, new EnchantmentInstance(EpicRegistry.SOUL_TIED.get(), 1));
                        output.accept(soulTiedBook);
                    })
                    .build());

    // Enchantments
    public static final RegistryObject<Enchantment> SOUL_TIED = ENCHANTMENTS.register("soul_tied", ()-> new
            SoulTiedEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.create("epicadditions_all", (item -> true)), EquipmentSlot.values()));

    // GLM
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_DROP = LOOT_MODIFIERS.register("add_drop", EpicLootModifier.CODEC);

    public static void registerItems(FMLJavaModLoadingContext modLoadingContext){
        ITEMS.register(modLoadingContext.getModEventBus());
    }
    public static void registerEntities(FMLJavaModLoadingContext modLoadingContext){
        ENTITIES.register(modLoadingContext.getModEventBus());
    }
    public static void registerCreativeModeTabs(FMLJavaModLoadingContext modLoadingContext){
        CREATIVE_MODE_TABS.register(modLoadingContext.getModEventBus());
    }
    public static void registerLootModifiers(FMLJavaModLoadingContext modLoadingContext){
        LOOT_MODIFIERS.register(modLoadingContext.getModEventBus());
    }
    public static void registerBlocks(FMLJavaModLoadingContext modLoadingContext){
        BLOCKS.register(modLoadingContext.getModEventBus());
    }
    public static void registerEnchantments(FMLJavaModLoadingContext modLoadingContext){
        ENCHANTMENTS.register(modLoadingContext.getModEventBus());
    }
    public static void registerMobEffects(FMLJavaModLoadingContext modLoadingContext){
        MOB_EFFECTS.register(modLoadingContext.getModEventBus());
    }

    public static void addToExistingTabs(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey().equals(CreativeModeTabs.OP_BLOCKS) && event.hasPermissions()) {
            event.accept(POCKET_CELL_GENERATOR.get());
            event.accept(POCKET_CELL_ASSIGNMENT_REMOVER.get());
        }
    }
}
