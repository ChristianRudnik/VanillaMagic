package seia.vanillamagic.machine.quarry;

import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import seia.vanillamagic.handler.customtileentity.CustomTileEntityHandler;
import seia.vanillamagic.quest.QuestMachineActivate;
import seia.vanillamagic.spell.EnumWand;
import seia.vanillamagic.util.EntityHelper;

public class QuestQuarry extends QuestMachineActivate
{
	@SubscribeEvent
	public void startQuarry(RightClickBlock event)
	{
		BlockPos quarryPos = event.getPos();
		EntityPlayer player = event.getEntityPlayer();
		ItemStack itemInHand = player.getHeldItemMainhand();
		World world = player.worldObj;
		if(!player.isSneaking())
		{
			return;
		}
		if(itemInHand == null)
		{
			return;
		}
		if(itemInHand.getItem().equals(EnumWand.BLAZE_ROD.wandItemStack.getItem()))
		{
			if(canPlayerGetAchievement(player))
			{
				player.addStat(achievement, 1);
			}
			if(player.hasAchievement(achievement))
			{
				TileQuarry tileQuarry = new TileQuarry();
				tileQuarry.init(player, quarryPos);
				if(tileQuarry.checkSurroundings())
				{
					if(CustomTileEntityHandler.INSTANCE.addCustomTileEntity(tileQuarry, player.dimension))
					{
						EntityHelper.addChatComponentMessage(player, tileQuarry.getClass().getSimpleName() + " added");
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void stopQuarry(BreakEvent event)
	{
		BlockPos quarryPos = event.getPos();
		EntityPlayer player = event.getPlayer();
		World world = event.getWorld();
		if(world.getBlockState(quarryPos).getBlock() instanceof BlockCauldron)
		{
			TileEntity quarryTile = CustomTileEntityHandler.INSTANCE.getCustomTileEntity(quarryPos, player.dimension);
			if(quarryTile == null)
			{
				return;
			}
			if(CustomTileEntityHandler.INSTANCE.removeCustomTileEntityAtPos(world, quarryPos, player.dimension))
			{
				EntityHelper.addChatComponentMessage(player, "TileEntity removed");
			}
		}
	}
}