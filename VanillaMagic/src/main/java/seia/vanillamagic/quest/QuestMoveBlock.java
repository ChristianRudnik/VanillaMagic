package seia.vanillamagic.quest;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import seia.vanillamagic.utils.ItemStackHelper;
import seia.vanillamagic.utils.NBTHelper;
import seia.vanillamagic.utils.spell.EnumWand;

public class QuestMoveBlock extends Quest
{
	/*
	 * Stack offHand must be an item that has maxStackSize = 1
	 * If it is a book, it must be a renamed book.
	 */
	public final ItemStack requiredStackOffHand;
	public final EnumWand requiredWand;

	public QuestMoveBlock(Quest required, int posX, int posY, ItemStack icon, String questName, String uniqueName, 
			ItemStack requiredStackOffHand, EnumWand requiredWand)
	{
		super(required, posX, posY, icon, questName, uniqueName);
		this.requiredStackOffHand = requiredStackOffHand;
		this.requiredWand = requiredWand;
	}
	
	/*
	 * This event is fired twice. And I want it to fire only once.
	 */
	int clickedTimes = 0;
	@SubscribeEvent
	public void onRightClick(RightClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		BlockPos wantedBlockPos = event.getPos();
		World world = player.worldObj;
		try
		{
			EnumWand wandPlayerHand = EnumWand.getWandByItemStack(player.getHeldItemMainhand());
			if(EnumWand.areWandsEqual(requiredWand, wandPlayerHand))
			{
				if(player.isSneaking())
				{
					ItemStack stackOffHand = player.getHeldItemOffhand();
					if(ItemStack.areItemsEqual(requiredStackOffHand, stackOffHand))
					{
						if(!player.hasAchievement(achievement))
						{
							player.addStat(achievement, 1);
						}
						if(player.hasAchievement(achievement))
						{
							/*
							 * can be write to ONLY one item
							 * IGNORES THE offHandRequiredStack.stackSize
							 */
							if(stackOffHand.stackSize == 1)
							{
								if(clickedTimes > 0)
								{
									clickedTimes = 0;
									return;
								}
								handleSave(world, player, wantedBlockPos, event.getFace());
								clickedTimes++;
							}
						}
						return;
					}
					NBTTagCompound stackTag = stackOffHand.getTagCompound();
					if(stackTag.hasKey(NBTHelper.NBT_TAG_COMPOUND_NAME))
					{
						if(clickedTimes > 0)
						{
							clickedTimes = 0;
							return;
						}
						handleLoad(world, player, wantedBlockPos, event.getFace());
						clickedTimes++;
					}
				}
			}
		}
		catch(Exception e)
		{
		}
	}
	
	public void handleSave(World world, EntityPlayer player, BlockPos wantedBlockPos, EnumFacing hittedFace)
	{
		// changing name just to force Minecraft to let this item have NBTTagCompound
		player.getHeldItemOffhand().setStackDisplayName("QuestBook");
		// any item will be replaced to Enchanted Book
		ItemStack stackOffHand = ItemStackHelper.replaceItemInStack(player.getHeldItemOffhand(), Items.ENCHANTED_BOOK);
		NBTTagCompound stackTagCompound = stackOffHand.getTagCompound();
		// Save to ItemStack
		if(!stackTagCompound.hasKey(NBTHelper.NBT_TAG_COMPOUND_NAME))
		{
			stackTagCompound.setTag(NBTHelper.NBT_TAG_COMPOUND_NAME, new NBTTagCompound());
			NBTTagCompound questTag = stackTagCompound.getCompoundTag(NBTHelper.NBT_TAG_COMPOUND_NAME);
			Block wantedBlock = world.getBlockState(wantedBlockPos).getBlock();
			questTag = NBTHelper.setBlockPosDataToNBT(wantedBlockPos, questTag);
			stackOffHand.setStackDisplayName("Saved block: " + wantedBlock.getLocalizedName());
			TileEntity tileEntity = world.getTileEntity(wantedBlockPos);
			if(tileEntity != null)
			{
				questTag = tileEntity.writeToNBT(questTag);
				stackOffHand.setStackDisplayName("[TileEntity] " + stackOffHand.getDisplayName());
			}
			if(tileEntity instanceof IInventory)
			{
				IInventory inv = (IInventory) tileEntity;
				questTag = NBTHelper.writeIInventoryToNBT(inv, questTag);
			}
			else if(tileEntity instanceof INBTSerializable<?>)
			{
				INBTSerializable<NBTTagCompound> serial = (INBTSerializable<NBTTagCompound>) tileEntity;
				questTag = NBTHelper.writeToINBTSerializable(serial, questTag);
			}
			else if(tileEntity instanceof IItemHandler)
			{
				IItemHandler handler = (IItemHandler) tileEntity;
				questTag = NBTHelper.writeToIItemHandler(handler, questTag);
			}
			player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stackOffHand);
		}
	}
	
	// TODO: Fix replacing TileEntity not always respond to right-click after move
	public void handleLoad(World world, EntityPlayer player, BlockPos wantedBlockPos, EnumFacing hittedFace)
	{
		ItemStack stackOffHand = player.getHeldItemOffhand();
		NBTTagCompound stackTagCompound = stackOffHand.getTagCompound();
		NBTTagCompound questTag = stackTagCompound.getCompoundTag(NBTHelper.NBT_TAG_COMPOUND_NAME);
		/*
		 * Unload from ItemStack 
		 * (place block) 
		 * (remove offHandItem -> place in offHand new ItemStack from the requiredStackOffHand but with stackSize 1)
		 */
		wantedBlockPos = wantedBlockPos.offset(hittedFace);
		if(world.isAirBlock(wantedBlockPos))
		{
			BlockPos readdedPos = NBTHelper.getBlockPosDataFromNBT(questTag);
			IBlockState readdedBlockState = world.getBlockState(readdedPos);
			Block readdedBlock = readdedBlockState.getBlock();
			if(readdedBlock != null)
			{
				world.setBlockState(wantedBlockPos, readdedBlockState);
				// TODO: Fix bug with rendering after replacing double-chest
				int renderDistance = 3;
				world.scheduleUpdate(readdedPos, readdedBlock, renderDistance);
				for(EnumFacing faceing : EnumFacing.values())
				{
					BlockPos checkingPos = readdedPos.offset(faceing);
					Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(checkingPos.getX() + renderDistance, checkingPos.getY() + renderDistance, checkingPos.getZ() + renderDistance, 
							checkingPos.getX() - renderDistance, checkingPos.getY() - renderDistance, checkingPos.getZ() - renderDistance);
					world.markBlockRangeForRenderUpdate(checkingPos.getX() + renderDistance, checkingPos.getY() + renderDistance, checkingPos.getZ() + renderDistance, 
							checkingPos.getX() - renderDistance, checkingPos.getY() - renderDistance, checkingPos.getZ() - renderDistance);
					Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(checkingPos.getX() - renderDistance, checkingPos.getY() - renderDistance, checkingPos.getZ() - renderDistance, 
							checkingPos.getX() + renderDistance, checkingPos.getY() + renderDistance, checkingPos.getZ() + renderDistance);
					world.markBlockRangeForRenderUpdate(checkingPos.getX() - renderDistance, checkingPos.getY() - renderDistance, checkingPos.getZ() - renderDistance, 
							checkingPos.getX() + renderDistance, checkingPos.getY() + renderDistance, checkingPos.getZ() + renderDistance);
				}
				world.scheduleUpdate(readdedPos, readdedBlock, renderDistance);
				world.updateEntities();
			}
			TileEntity readdedTile = world.getTileEntity(readdedPos);
			if(readdedTile != null)
			{
				world.setTileEntity(wantedBlockPos, readdedTile);
				world.removeTileEntity(readdedPos);
				//world.getTileEntity(readdedPos).setPos(wantedBlockPos);
			}
			TileEntity tileAfter = world.getTileEntity(wantedBlockPos);
			if(tileAfter instanceof IInventory)
			{
				IInventory inv = (IInventory) tileAfter;
				NBTHelper.readIInventoryFromNBT(inv, questTag);
			}
			else if(tileAfter instanceof INBTSerializable<?>)
			{
				INBTSerializable<NBTTagCompound> serial = (INBTSerializable<NBTTagCompound>) tileAfter;
				NBTHelper.readFromINBTSerializable(serial, questTag);
			}
			else if(tileAfter instanceof IItemHandler)
			{
				IItemHandler handler = (IItemHandler) tileAfter;
				NBTHelper.readFromIItemHandler(handler, questTag);
			}
			ItemStack newOffHand = requiredStackOffHand.copy();
			newOffHand.stackSize = 1;
			player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, newOffHand);
			world.setBlockToAir(readdedPos);
			return;
		}
	}
}