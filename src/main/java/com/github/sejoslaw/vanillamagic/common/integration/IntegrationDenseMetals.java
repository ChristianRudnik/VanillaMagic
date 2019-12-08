package com.github.sejoslaw.vanillamagic.integration;

import net.minecraft.block.Block;
import com.github.sejoslaw.vanillamagic.util.ClassUtils;

/**
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public class IntegrationDenseMetals implements IIntegration {
	public String getModName() {
		return "Dense Metals";
	}

	public void postInit() throws Exception {
		Class.forName("com.mmd.densemetals.Main");
		CustomOre.REDSTONE.add(
				(Block) ClassUtils.getFieldObject("com.mmd.densemetals.blocks.ModBlocks", "denseredstoneOre", true));
		CustomOre.DIAMOND.add(
				(Block) ClassUtils.getFieldObject("com.mmd.densemetals.blocks.ModBlocks", "densediamondOre", true));
	}
}