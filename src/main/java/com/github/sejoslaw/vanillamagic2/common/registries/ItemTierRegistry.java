package com.github.sejoslaw.vanillamagic2.common.registries;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public final class ItemTierRegistry {
    private static class TierEntry {
        public final int tier;
        public final String tierType;
        public final List<ItemStack> stacks;

        public TierEntry(int tier, String tierType, List<ItemStack> stacks) {
            this.tier = tier;
            this.tierType = tierType;
            this.stacks = stacks;
        }
    }

    private static final Set<TierEntry> ENTRIES = new HashSet<>();
    private static final TierEntry EMPTY = new TierEntry(-1, "EMPTY", new ArrayList<>());

    public static void initialize() {
        add(1, "wooden", "planks");
        add(2, "stone", Blocks.STONE);
        add(3, "iron", Items.IRON_INGOT);
        add(4, "golden", Items.GOLD_INGOT);
        add(5, "diamond", Items.DIAMOND);
    }

    public static void add(int tier, String tierType, String itemType) {
        List<Block> blocks = ForgeRegistries.BLOCKS.getValues()
                .stream()
                .filter(block -> block.getRegistryName().toString().toLowerCase().contains(itemType))
                .collect(Collectors.toList());

        for (Block block : blocks) {
            add(tier, tierType, block);
        }
    }

    public static void add(int tier, String tierType, Block block) {
        add(tier, tierType, Item.BLOCK_TO_ITEM.get(block));
    }

    public static void add(int tier, String tierType, Item item) {
        add(tier, tierType, new ItemStack(item));
    }

    public static void add(int tier, String tierType, ItemStack stack) {
        TierEntry te = find(tier, tierType);

        if (te == null) {
            te = new TierEntry(tier, tierType, new ArrayList<>());
            te.stacks.add(stack);
            ENTRIES.add(te);
        } else {
            te.stacks.add(stack);
        }
    }

    public static boolean isBase(ItemStack item) {
        return ENTRIES
                .stream()
                .anyMatch(entry -> item.getItem().getRegistryName().toString().toLowerCase().contains(entry.tierType));
    }

    public static boolean isIngredient(ItemStack item) {
        return getTier(item) != EMPTY.tier;
    }

    public static int getTier(ItemStack item) {
        return ENTRIES
                .stream()
                .filter(entry -> entry.stacks.stream().anyMatch(stack -> stack.getItem() == item.getItem() && stack.getCount() <= item.getCount()))
                .findFirst()
                .orElse(EMPTY)
                .tier;
    }

    public static String getTierType(int tier) {
        return ENTRIES
                .stream()
                .filter(entry -> entry.tier == tier).findFirst().orElse(EMPTY).tierType;
    }

    private static TierEntry find(int tier, String tierType) {
        return ENTRIES
                .stream()
                .filter(entry -> entry.tier == tier && entry.tierType.equals(tierType))
                .findFirst()
                .orElse(null);
    }
}
