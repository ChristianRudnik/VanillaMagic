package seia.vanillamagic.integration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import net.minecraft.nbt.NBTTagCompound;
import seia.vanillamagic.VanillaMagic;

/**
 * Integration is always done at the end of each phase (PreInit, Init, PostInit).
 */
public class VanillaMagicIntegration
{
	public static final VanillaMagicIntegration INSTANCE = new VanillaMagicIntegration();
	
	//==================================================================================================
	
	public final NBTTagCompound tagCompound = new NBTTagCompound();
	public final List<IIntegration> integrations = new ArrayList<IIntegration>();
	
	private VanillaMagicIntegration()
	{
		/*
		 * Comment any of the following to disable integration.
		 */
		integrations.add(new IntegrationVersionChecker());
		integrations.add(new IntegrationBetterAchievements());
		integrations.add(new IntegrationWTFExpedition());
		integrations.add(new IntegrationFilledOres());
		integrations.add(new IntegrationSuperOres());
		
		integrations.add(new IntegrationNetherMetals());
		integrations.add(new IntegrationEndMetals());
		integrations.add(new IntegrationDenseMetals());
		
		integrations.add(new IntegrationJEI());
	}
	
	public void preInit()
	{
		for(IIntegration i : integrations)
		{
			try
			{
				if(i.preInit())
				{
					VanillaMagic.LOGGER.log(Level.INFO, "[PRE-INIT] " + i.getModName() + " integration - enabled");
				}
			}
			catch(Exception e)
			{
				VanillaMagic.LOGGER.log(Level.INFO, "[PRE-INIT] " + i.getModName() + " integration - failed");
			}
		}
	}
	
	public void init()
	{
		for(IIntegration i : integrations)
		{
			try
			{
				if(i.init())
				{
					VanillaMagic.LOGGER.log(Level.INFO, "[INIT] " + i.getModName() + " integration - enabled");
				}
			}
			catch(Exception e)
			{
				VanillaMagic.LOGGER.log(Level.INFO, "[INIT] " + i.getModName() + " integration - failed");
			}
		}
	}
	
	public void postInit()
	{
		for(IIntegration i : integrations)
		{
			try
			{
				if(i.postInit())
				{
					VanillaMagic.LOGGER.log(Level.INFO, "[POST-INIT] " + i.getModName() + " integration - enabled");
				}
			}
			catch(Exception e)
			{
				VanillaMagic.LOGGER.log(Level.INFO, "[POST-INIT] " + i.getModName() + " integration - failed");
			}
		}
	}
}