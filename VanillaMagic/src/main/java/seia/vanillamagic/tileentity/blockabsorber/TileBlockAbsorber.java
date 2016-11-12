package seia.vanillamagic.tileentity.blockabsorber;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import seia.vanillamagic.api.tileentity.blockabsorber.IBlockAbsorber;
import seia.vanillamagic.core.VanillaMagic;
import seia.vanillamagic.inventory.InventoryHelper;
import seia.vanillamagic.tileentity.CustomTileEntity;
import seia.vanillamagic.util.BlockPosHelper;
import seia.vanillamagic.util.NBTHelper;

public class TileBlockAbsorber extends CustomTileEntity implements IBlockAbsorber
{
	public static final String REGISTRY_NAME = TileBlockAbsorber.class.getName();
	
	protected TileEntityHopper connectedHopper;
	protected double connectedHopperPosX, connectedHopperPosY, connectedHopperPosZ;
	
	public void init(World world, BlockPos pos)
	{
		super.init(world, pos);
		this.connectedHopper = getConnectedHopper();
		if(connectedHopper == null)
		{
			VanillaMagic.LOGGER.log(Level.ERROR, "Connected Hopper is null.");
			BlockPosHelper.printCoords(Level.ERROR, "TileBlockAbsorber position:", this.pos);
		}
		else
		{
			connectedHopperPosX = connectedHopper.getXPos();
			connectedHopperPosY = connectedHopper.getYPos();
			connectedHopperPosZ = connectedHopper.getZPos();
		}
	}
	
	public List<String> getAdditionalInfo()
	{
		List<String> list = super.getAdditionalInfo();
		list.add("Saved Hopper position: X=" + connectedHopperPosX + ", Y=" + connectedHopperPosY + ", Z=" + connectedHopperPosZ);
		return list;
	}
	
	/**
	 * On each update this {@link TileEntity} will check for block which is on this position and try to place it in bottom {@link IHopper}
	 * 
	 * @see seia.vanillamagic.tileentity.CustomTileEntity#update()
	 */
	public void update()
	{
		if(connectedHopper == null)
		{
			//connectedHopper = getConnectedHopper();
			BlockPos hopperPos = new BlockPos(connectedHopperPosX, connectedHopperPosY, connectedHopperPosZ);
			connectedHopper = (TileEntityHopper) worldObj.getTileEntity(hopperPos);
			return;
		}
		IBlockState thisState = worldObj.getBlockState(pos);
		if(Block.isEqualTo(thisState.getBlock(), Blocks.AIR)) // We don't want to put Air into Hopper
		{
			return;
		}
		// For IInventory
		TileEntity tileAtThisPos = worldObj.getTileEntity(pos);
		if(tileAtThisPos != null)
		{
			// If we have a Tile which is Inventory we want to absorb it's items first
			if(tileAtThisPos instanceof IInventory)
			{
				IInventory inv = (IInventory) tileAtThisPos;
				if(!InventoryHelper.isInventoryEmpty(inv, EnumFacing.DOWN)) // First call to absorb items from Inventory
				{
					for(int i = 0; i < inv.getSizeInventory(); i++)
					{
						ItemStack stackInSlot = inv.getStackInSlot(i);
						ItemStack leftItems = InventoryHelper.putStackInInventoryAllSlots(connectedHopper, stackInSlot, getInputFacing());
						inv.setInventorySlotContents(i, leftItems);
					}
				}
				if(!InventoryHelper.isInventoryEmpty(inv, EnumFacing.DOWN)) // Second call to check if all items were absorbed after first call
				{
					return; // If it's not possible to absorb all, return
				}
			}
		}
		// If it's not an Inventory than it must be a Block
		// For normal Block
		ItemStack thisBlock = new ItemStack(thisState.getBlock());
		if(thisBlock.getItem() == null)
		{
			return;
		}
		ItemStack leftItems = InventoryHelper.putStackInInventoryAllSlots(connectedHopper, thisBlock, getInputFacing());
		if(leftItems == null)
		{
			worldObj.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	public EnumFacing getInputFacing()
	{
		return EnumFacing.UP;
	}
	
	public TileEntityHopper getConnectedHopper()
	{
		if(connectedHopper == null)
		{
			return (TileEntityHopper) worldObj.getTileEntity(pos.offset(EnumFacing.DOWN));
		}
		else
		{
			return connectedHopper;
		}
	}
	
	/**
	 * Try to override serializeNBT instead of this method.
	 */
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
		super.writeToNBT(compound);
		compound = NBTHelper.writeToINBTSerializable(this, compound);
		return compound;
    }
	
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setDouble(NBTHelper.NBT_POSX, connectedHopperPosX);
		compound.setDouble(NBTHelper.NBT_POSY, connectedHopperPosY);
		compound.setDouble(NBTHelper.NBT_POSZ, connectedHopperPosZ);
		return compound;
	}
	
	/**
	 * Try to override deserializeNBT instead of this method.
	 */
	public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
		NBTHelper.readFromINBTSerializable(this, compound);
    }
	
	public void deserializeNBT(NBTTagCompound compound)
	{
		connectedHopperPosX = compound.getDouble(NBTHelper.NBT_POSX);
		connectedHopperPosY = compound.getDouble(NBTHelper.NBT_POSY);
		connectedHopperPosZ = compound.getDouble(NBTHelper.NBT_POSZ);
	}
	
	/**
	 * Returns NULL if there is no inventory for Hopper to transfer into.
	 */
	@Nullable
	public IInventory getInventoryForHopperTransfer()
	{
		try
		{
			Method thisMethod = getConnectedHopper().getClass().getMethod("getInventoryForHopperTransfer");
			thisMethod.setAccessible(true);
			return (IInventory) thisMethod.invoke(getConnectedHopper());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}