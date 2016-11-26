package seia.vanillamagic.tileentity.machine.autocrafting;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import seia.vanillamagic.api.exception.NotInventoryException;
import seia.vanillamagic.api.inventory.InventoryWrapper;
import seia.vanillamagic.core.VanillaMagic;
import seia.vanillamagic.tileentity.machine.TileMachine;
import seia.vanillamagic.util.BlockPosHelper;

public class TileAutocrafting extends TileMachine
{
	public static final String REGISTRY_NAME = TileAutocrafting.class.getName();
	
	public ContainerAutocrafting container;
	
	private int currentlyCraftingSlot = 0;
	
	private final int defaultCraftingSlot = 0;
	
	public void init(World world, BlockPos machinePos)
	{
		super.init(world, machinePos);
		this.oneOperationCost = 100; // 1 Coal = 16 crafting operations ?
		this.startPos = BlockPosHelper.copyPos(getMachinePos());
		this.chestPosInput = getMachinePos().up();
		this.chestPosOutput = getMachinePos().down(2);
		try
		{
			this.inventoryInput = new InventoryWrapper(world, chestPosInput);
			this.inventoryOutput = new InventoryWrapper(world, chestPosOutput);
		}
		catch(NotInventoryException e)
		{
			VanillaMagic.LOGGER.log(Level.ERROR, this.getClass().getSimpleName() + " - error when converting to IInventory at position: " + e.position.toString());
		}
	}
	
	public void initContainer()
	{
		BlockPos[][] inventoryPosMatrix = QuestAutocrafting.buildInventoryMatrix(getPos());
		IInventory[][] inventoryMatrix = QuestAutocrafting.buildIInventoryMatrix(world, inventoryPosMatrix);
		ItemStack[][] stackMatrix = QuestAutocrafting.buildStackMatrix(inventoryMatrix, currentlyCraftingSlot);
		container = new ContainerAutocrafting(world, stackMatrix);
	}
	
	public boolean checkSurroundings() 
	{
		return QuestAutocrafting.isConstructionComplete(this.world, getMachinePos());
	}
	
	public void doWork() 
	{
		if(inventoryOutputHasSpace())
		{
			initContainer();
			for(int i = 0; i < 4; ++i)
			{
				boolean crafted = container.craft();
				if(crafted)
				{
					container.outputResult(getHopperForStructure());
					container.removeStacks(
							QuestAutocrafting.buildIInventoryMatrix(world, 
									QuestAutocrafting.buildInventoryMatrix(getMachinePos())));
					return;
				}
				container.rotateMatrix();
			}
			decreaseTicks();
		}
	}
	
	public EnumFacing getOutputFacing() 
	{
		return EnumFacing.DOWN;
	}
	
	@Nullable
	public IHopper getHopperForStructure()
	{
		BlockPos hopperPos = getMachinePos().down(2);
		TileEntity hopperTile = world.getTileEntity(hopperPos);
		if(hopperTile instanceof IHopper)
		{
			return (IHopper) hopperTile;
		}
		return null;
	}
}