package com.github.sejoslaw.vanillamagic.common.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

/**
 * Class which store various methods connected with Block.
 *
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public final class BlockUtil {
    private BlockUtil() {
    }

    /**
     * @return Returns true if the given state is a fluid.
     */
    public static boolean isBlockLiquid(BlockState state) {
        return (state instanceof IFluidBlock || state.getMaterial().isLiquid());
    }

    /**
     * Place block from given stack on given position.
     */
    public static void placeBlockFromStack(World world, BlockPos placePosition, ItemStack stack) {
        Item itemFromStack = stack.getItem();
        Block blockFromStack = Block.getBlockFromItem(itemFromStack);
        world.setBlockState(placePosition, blockFromStack.getDefaultState());
    }

    public static boolean areEqual(Block b1, Block b2) {
        return b1.equals(b2);
    }
}