package com.github.sejoslaw.vanillamagic2.common.items;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

/**
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public class CustomItemLiquidSuppressionCrystal extends CustomItemCrystal {
    public List<ItemStack> getIngredients() {
        List<ItemStack> stacks = super.getIngredients();
        stacks.add(new ItemStack(Items.BUCKET, 8));
        return stacks;
    }
}
