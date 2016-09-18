package seia.vanillamagic.quest.portablecraftingtable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import seia.vanillamagic.quest.Quest;
import seia.vanillamagic.utils.EntityHelper;

public class QuestPortableCraftingTable extends Quest
{
	@SubscribeEvent
	public void openCrafting(RightClickItem event)
	{
		EntityPlayer player = event.getEntityPlayer();
		World world = player.worldObj;
		if(EntityHelper.hasPlayerCraftingTableInMainHand(player))
		{
			if(canPlayerGetAchievement(player))
			{
				player.addStat(achievement, 1);
			}
			if(player.hasAchievement(achievement))
			{
				player.displayGui(new InterfacePortableCraftingTable(player));
			}
		}
	}
}