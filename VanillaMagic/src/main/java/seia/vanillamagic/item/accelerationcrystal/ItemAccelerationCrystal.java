package seia.vanillamagic.item.accelerationcrystal;

import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import seia.vanillamagic.item.CustomItemCrystal;

public class ItemAccelerationCrystal extends CustomItemCrystal
{
	public void registerRecipe()
	{
		GameRegistry.addShapedRecipe(
				new ResourceLocation(""),
				null,
				getItem(), 
				new Object[]{
				" B ",
				"BNB",
				" B ",
				'B', Items.BOOK,
				'N', Items.NETHER_STAR
		});
	}
	
	public String getItemName() 
	{
		return "Acceleration Crystal";
	}
}