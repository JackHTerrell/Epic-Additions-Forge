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
                    .fieldOf("amount").forGetter(b -> b.amount)).and(Codec.STRING.fieldOf("loot_table")
                    .fieldOf("loot_table").forGetter(c -> c.lootTable)).apply(instance, EpicLootModifier::new)));

    private final Item item;
    private final int amount;
    private final String lootTable;

    protected EpicLootModifier(LootItemCondition[] conditionsIn, Item item, int amount, String lootTable) {
        super(conditionsIn);
        this.item = item;
        this.amount = amount;
        this.lootTable = lootTable;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> objectArrayList, LootContext lootContext) {
        if(lootContext.getQueriedLootTableId().equals(ResourceLocation.tryParse(lootTable)))
            objectArrayList.add(new ItemStack(item, amount));
        return objectArrayList;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
