package com.jackbusters.epicadditions.glm;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * A loot modifier built to easily modify any loot table at a whim.
 */
public class EpicLootModifier extends LootModifier {

    public static final Supplier<Codec<EpicLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(instance -> codecStart(instance).and(ForgeRegistries.ITEMS.getCodec()
                    .fieldOf("item").forGetter(a -> a.item)).and(Codec.INT
                    .fieldOf("amount").forGetter(b -> b.amount)).and(Codec.STRING
                    .fieldOf("loot_table").forGetter(c -> c.lootTable)).and(Codec.DOUBLE
                    .fieldOf("chance").forGetter(d -> d.chance))
                    .apply(instance, EpicLootModifier::new)));

    private final Item item; // The item to generate
    private final int amount; // the number of items
    private final String lootTable; // the loot table to generate the loot in (i.e. "minecraft:entities/wither").
    private final double chance; // The chance of the loot generating 0 being no chance, 1 being guaranteed.

    protected EpicLootModifier(LootItemCondition[] conditionsIn, Item item, int amount, String lootTable, double chance) {
        super(conditionsIn);
        this.item = item;
        this.amount = amount;
        this.lootTable = lootTable;
        this.chance = chance;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> objectArrayList, LootContext lootContext) {
        if(lootContext.getQueriedLootTableId().equals(ResourceLocation.tryParse(lootTable))) {
            if(chance == 1)
                objectArrayList.add(new ItemStack(item, amount));
            else{
                double random = Math.random();
                if(random < chance)
                    objectArrayList.add(new ItemStack(item, amount));
            }
        }
        return objectArrayList;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
