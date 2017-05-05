package seia.vanillamagic.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Class which represents basic Nether Star based Custom Item.
 */
public abstract class CustomItemCrystal extends CustomItem
{
	public Item getBaseItem() 
	{
		return Items.NETHER_STAR;
	}
}