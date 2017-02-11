package seia.vanillamagic.magic.spell.spells.weather;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import seia.vanillamagic.magic.spell.Spell;
import seia.vanillamagic.magic.wand.IWand;

public class SpellWeatherRain extends Spell 
{
	public SpellWeatherRain(int spellID, String spellName, String spellUniqueName, IWand wand, 
			ItemStack itemOffHand) 
	{
		super(spellID, spellName, spellUniqueName, wand, itemOffHand);
	}

	public boolean castSpell(EntityPlayer caster, BlockPos pos, EnumFacing face, Vec3d hitVec) 
	{
		World world = caster.world;
		WorldInfo worldInfo = world.getWorldInfo();
		worldInfo.setCleanWeatherTime(0);
		worldInfo.setRainTime(1000);
		worldInfo.setThunderTime(1000);
		worldInfo.setRaining(true);
		worldInfo.setThundering(false);
		return true;
	}
}