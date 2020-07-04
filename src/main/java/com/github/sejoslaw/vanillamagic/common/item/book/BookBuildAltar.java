package com.github.sejoslaw.vanillamagic.item.book;

import static com.github.sejoslaw.vanillamagic.util.TextUtil.ENTER;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTTagString;
import com.github.sejoslaw.vanillamagic.api.quest.QuestRegistry;
import com.github.sejoslaw.vanillamagic.quest.QuestBuildAltar;
import com.github.sejoslaw.vanillamagic.util.CraftingUtil;
import com.github.sejoslaw.vanillamagic.util.TextUtil;

/**
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public class BookBuildAltar implements IBook
{
	public int getBookID() {
		return BookRegistry.BOOK_BUILD_ALTAR_UID;
	}
	
	public void registerRecipe() {
		CraftingUtil.addShapedRecipe(getItem(), new Object[]{
				"   ",
				"BBB",
				"   ",
				'B', Items.BOOK
		});
	}
	
	public ItemStack getItem()
	{
		ItemStack infoBook = new ItemStack(BookRegistry.BOOK_ITEM);
		CompoundNBT data = new CompoundNBT();
		{
			// Constructing TagCompound
			ListNBT pages = new ListNBT();
			{
				// Pages
				pages.appendTag(new NBTTagString(
						"\n\n\n\n" + BookRegistry.COLOR_TITLE + "==== " + TextUtil.translateToLocal("book.altarBuilding.title") + " ====" + 
						TextUtil.getEnters(4) + "-" + BookRegistry.AUTHOR + " " + BookRegistry.YEAR
						));
				{
					// Tier 1
					{
						//QuestBuildAltar quest1 = QuestList.QUEST_BUILD_ALTAR_TIER_1;
						QuestBuildAltar quest1 = (QuestBuildAltar) QuestRegistry.get("buildAltarTier1");
						pages.appendTag(new NBTTagString(
								BookRegistry.COLOR_HEADER + 
								TextUtil.translateToLocal("quest." + quest1.getUniqueName()) + 
								TextUtil.getEnters(2) + 
								"�0" +
								TextUtil.translateToLocal("quest." + quest1.getUniqueName() + ".desc") + TextUtil.getEnters(2) +
								// How Altar should look
										"�cRRR" + ENTER +
										"�cR�0C�cR" + ENTER +
										"�cRRR"
								));
					}
					// Tier 2
					{
						//QuestBuildAltar quest2 = QuestList.QUEST_BUILD_ALTAR_TIER_2;
						QuestBuildAltar quest2 = (QuestBuildAltar) QuestRegistry.get("buildAltarTier2");
						pages.appendTag(new NBTTagString(
								BookRegistry.COLOR_HEADER + 
								TextUtil.translateToLocal("quest." + quest2.getUniqueName()) + 
								TextUtil.getEnters(2) + 
								"�0" +
								TextUtil.translateToLocal("quest." + quest2.getUniqueName() + ".desc") + TextUtil.getEnters(2) + "--->"
								));
						pages.appendTag(new NBTTagString(
								TextUtil.getEnters(1) +
								// How Altar should look
								"�7I     I" + ENTER +
								" �cRRR " + ENTER +
								" �cR�0C�cR " + ENTER +
								" �cRRR " + ENTER +
								"�7I     I"
								));
					}
					// Tier 3
					{
						//QuestBuildAltar quest3 = QuestList.QUEST_BUILD_ALTAR_TIER_3;
						QuestBuildAltar quest3 = (QuestBuildAltar) QuestRegistry.get("buildAltarTier3");
						pages.appendTag(new NBTTagString(
								BookRegistry.COLOR_HEADER + 
								TextUtil.translateToLocal("quest." + quest3.getUniqueName()) + 
								TextUtil.getEnters(2) + 
								"�0" +
								TextUtil.translateToLocal("quest." + quest3.getUniqueName() + ".desc") + TextUtil.getEnters(2) + "--->"
								));
						pages.appendTag(new NBTTagString(
								TextUtil.getEnters(1) +
								// How Altar should look
								"     �6G   " + ENTER +
								"  �7I     I " + ENTER +
								"   �cRRR  " + ENTER +
								"�6G  �cR�0C�cR  �6G" + ENTER +
								"   �cRRR  " + ENTER +
								"  �7I     I " + ENTER +
								"     �6G   "
								));
					}
					// Tier 4
					{
						//QuestBuildAltar quest4 = QuestList.QUEST_BUILD_ALTAR_TIER_4;
						QuestBuildAltar quest4 = (QuestBuildAltar) QuestRegistry.get("buildAltarTier4");
						pages.appendTag(new NBTTagString(
								BookRegistry.COLOR_HEADER + 
								TextUtil.translateToLocal("quest." + quest4.getUniqueName()) + 
								TextUtil.getEnters(2) + 
								"�0" +
								TextUtil.translateToLocal("quest." + quest4.getUniqueName() + ".desc") + TextUtil.getEnters(2) + "--->"
								));
						pages.appendTag(new NBTTagString(
								TextUtil.getEnters(1) +
								// How Altar should look
								"�cR   �6G    �cR" + ENTER +
								"  �7I     I " + ENTER +
								"   �cRRR  " + ENTER +
								"�6G  �cR�0C�cR  �6G" + ENTER +
								"   �cRRR  " + ENTER +
								"  �7I     I " + ENTER +
								"�cR   �6G    �cR"
								));
					}
					// Tier 5
					{
						//QuestBuildAltar quest5 = QuestList.QUEST_BUILD_ALTAR_TIER_5;
						QuestBuildAltar quest5 = (QuestBuildAltar) QuestRegistry.get("buildAltarTier5");
						pages.appendTag(new NBTTagString(
								BookRegistry.COLOR_HEADER + 
								TextUtil.translateToLocal("quest." + quest5.getUniqueName()) + 
								TextUtil.getEnters(2) + 
								"�0" +
								TextUtil.translateToLocal("quest." + quest5.getUniqueName() + ".desc") + TextUtil.getEnters(2) + "--->"
								));
						pages.appendTag(new NBTTagString(
								TextUtil.getEnters(1) +
								// How Altar should look
								"         �1L" + ENTER +
								"                        " + ENTER +
								"   �cR    �6G    �cR" + ENTER +
								"     �7I      I " + ENTER +
								"       �cRRR  " + ENTER +
								"�1L  �6G  �cR�0C�cR  �6G  �1L" + ENTER +
								"       �cRRR  " + ENTER +
								"     �7I      I " + ENTER +
								"   �cR    �6G    �cR" + ENTER +
								"                        " + ENTER +
								"         �1L"
								));
					}
					// Tier 6
					{
						//QuestBuildAltar quest6 = QuestList.QUEST_BUILD_ALTAR_TIER_6;
						QuestBuildAltar quest6 = (QuestBuildAltar) QuestRegistry.get("buildAltarTier6");
						pages.appendTag(new NBTTagString(
								BookRegistry.COLOR_HEADER + 
								TextUtil.translateToLocal("quest." + quest6.getUniqueName()) + 
								TextUtil.getEnters(2) + 
								"�0" +
								TextUtil.translateToLocal("quest." + quest6.getUniqueName() + ".desc") + TextUtil.getEnters(2) + "--->"
								));
						pages.appendTag(new NBTTagString(
								TextUtil.getEnters(1) +
								// How Altar should look
								"          �1L" + ENTER +
								"  �9D              D" + ENTER +
								"    �cR    �6G    �cR" + ENTER +
								"      �7I      I " + ENTER +
								"        �cRRR  " + ENTER +
								"�1L   �6G  �cR�0C�cR   �6G    �1L" + ENTER +
								"        �cRRR  " + ENTER +
								"      �7I      I " + ENTER +
								"    �cR    �6G    �cR" + ENTER +
								"  �9D              D" + ENTER +
								"          �1L"
								));
					}
					// Tier 7
					{
						//QuestBuildAltar quest7 = QuestList.QUEST_BUILD_ALTAR_TIER_7;
						QuestBuildAltar quest7 = (QuestBuildAltar) QuestRegistry.get("buildAltarTier7");
						pages.appendTag(new NBTTagString(
								BookRegistry.COLOR_HEADER + 
								TextUtil.translateToLocal("quest." + quest7.getUniqueName()) + 
								TextUtil.getEnters(2) + 
								"�0" +
								TextUtil.translateToLocal("quest." + quest7.getUniqueName() + ".desc") + TextUtil.getEnters(2) + "--->"
								));
						pages.appendTag(new NBTTagString(
								// How Altar should look
								"              �aE" + ENTER +
								"                       " + ENTER +
								"              �1L" + ENTER +
								"      �9D              D" + ENTER +
								"        �cR    �6G    �cR" + ENTER +
								"          �7I      I " + ENTER +
								"            �cRRR  " + ENTER +
								"�aE  �1L   �6G  �cR�0C�cR   �6G   �1L  �aE" + ENTER +
								"            �cRRR  " + ENTER +
								"          �7I      I " + ENTER +
								"        �cR    �6G    �cR" + ENTER +
								"      �9D              D" + ENTER +
								"              �1L" + ENTER +
								"                       " + ENTER +
								"              �aE"
								));
					}
				}
			}
			data.setTag("pages", pages);
			data.putString("author", BookRegistry.AUTHOR);
			data.putString("title", BookRegistry.BOOK_NAME_BUILD_ALTAR);
			data.setInteger(BookRegistry.BOOK_NBT_UID, getBookID());
		}
		infoBook.setTagCompound(data);
		infoBook.setDisplayName(BookRegistry.BOOK_NAME_BUILD_ALTAR);
		return infoBook;
	}
}