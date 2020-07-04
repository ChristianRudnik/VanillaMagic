package com.github.sejoslaw.vanillamagic.item.potionedcrystal;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import com.github.sejoslaw.vanillamagic.core.VanillaMagic;
import com.github.sejoslaw.vanillamagic.item.VMItems;
import com.github.sejoslaw.vanillamagic.util.CauldronUtil;
import com.github.sejoslaw.vanillamagic.util.ItemStackUtil;

/**
 * Class which add various methods to work with PotionedCrystals.
 * 
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public class PotionedCrystalHelper {
	private PotionedCrystalHelper() {
	}

	/**
	 * @return Returns the name of the Potion from given PotionType.
	 */
	public static String getPotionTypeName(PotionType pt) {
		return ForgeRegistries.POTION_TYPES.getKey(pt).getResourcePath();
	}

	/**
	 * @return Returns the IPotionedCrystal from ItemPotion. Used mainly during
	 *         Cauldron Crafting.
	 */
	@Nullable
	public static IPotionedCrystal getPotionedCrystalFromCauldron(World world, BlockPos cauldronPos) {
		List<ItemEntity> itemsInCauldron = CauldronUtil.getItemsInCauldron(world, cauldronPos);

		if (itemsInCauldron == null) {
			return null;
		}

		for (ItemEntity ei : itemsInCauldron) {
			ItemStack stack = ei.getItem();

			if (ItemStackUtil.isNullStack(stack)) {
				return null;
			}

			if (!(stack.getItem() instanceof ItemPotion)) {
				continue;
			}

			PotionType pt = PotionUtils.getPotionFromItem(stack);
			String ptName = getPotionTypeName(pt);

			for (IPotionedCrystal pc : VMItems.POTIONED_CRYSTALS) {
				if (pc.getPotionUnlocalizedName().equals(ptName)) {
					return pc;
				}
			}
		}
		return null;
	}

	/**
	 * Given ItemStack is a PotionedCrystal - Nether Star.
	 * 
	 * @return Returns the PotionedCrystal from given ItemStack.
	 */
	@Nullable
	public static IPotionedCrystal getPotionedCrystal(ItemStack netherStarStack) {
		if (ItemStackUtil.isNullStack(netherStarStack)) {
			return null;
		}

		CompoundNBT stackTag = netherStarStack.getTagCompound();
		if ((stackTag == null) || !stackTag.hasKey(IPotionedCrystal.NBT_POTION_TYPE_NAME)) {
			return null;
		}

		String name = stackTag.getString(IPotionedCrystal.NBT_POTION_TYPE_NAME);
		for (IPotionedCrystal pc : VMItems.POTIONED_CRYSTALS) {
			if (name.equals(pc.getPotionUnlocalizedName())) {
				return pc;
			}
		}

		return null;
	}

	/**
	 * Register all PotionedCrystal recipes.
	 */
	public static void registerRecipes() {
		for (PotionType potionType : ForgeRegistries.POTION_TYPES.getValues()) {
			VMItems.POTIONED_CRYSTALS.add(new IPotionedCrystal() {
				public PotionType getPotionType() {
					return potionType;
				}
			});
		}

		VanillaMagic.logInfo("Registered Potioned Crystals: " + VMItems.POTIONED_CRYSTALS.size());
	}
}