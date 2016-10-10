package seia.vanillamagic.tileentity.chunkloader;

import org.apache.logging.log4j.Level;

import seia.vanillamagic.VanillaMagic;
import seia.vanillamagic.api.tileentity.chunkloader.IChunkLoader;
import seia.vanillamagic.tileentity.CustomTileEntity;
import seia.vanillamagic.util.BlockPosHelper;

public class TileChunkLoader extends CustomTileEntity implements IChunkLoader
{
	public static final String REGISTRY_NAME = TileChunkLoader.class.getSimpleName();
	
//	public void validate() 
//	{
//		super.validate();
//		if ((!this.worldObj.isRemote) && (this.chunkTicket == null)) 
//		{
//			Ticket ticket = ForgeChunkManager.requestTicket(VanillaMagic.INSTANCE, this.worldObj, ForgeChunkManager.Type.NORMAL);
//			if (ticket != null) 
//			{
//				forceChunkLoading(ticket);
//			}
//		}
//	}
	
//	public void invalidate() 
//	{
//		super.invalidate();
//		stopChunkLoading();
//	}
	/*
	public void unforceChunkLoading() 
	{
		for(Object obj : this.chunkTicket.getChunkList()) 
		{
			ChunkPos coord = (ChunkPos) obj;
			ForgeChunkManager.unforceChunk(this.chunkTicket, coord);
		}
	}
	*/
//	public void stopChunkLoading() 
//	{
//		if (this.chunkTicket != null) 
//		{
//			ForgeChunkManager.releaseTicket(this.chunkTicket);
//			this.chunkTicket = null;
//		}
//	}
	
	public void update() 
	{
		if(!QuestChunkLoader.isChunkLoaderBuildCorrectly(worldObj, this.pos))
		{
			invalidate();
			VanillaMagic.LOGGER.log(Level.WARN, "Incorrect ChunkLoader placed on:");
			BlockPosHelper.printCoords(this.pos);
		}
	}
}