package seia.vanillamagic.quest;

import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import seia.vanillamagic.utils.ItemStackHelper;

/** 
 * Base class for all the quests.<br>
 * Each Quest MUST overrides {@link readData} if it adds additional data.
 */
public abstract class Quest
{
	public Achievement achievement;
	public Achievement required;
	public Quest requiredQuest;
	public int posX, posY;
	public ItemStack icon;
	public String questName, uniqueName;
	
	@Nullable
	public Quest[] additionalRequiredQuests;
	
	public void readData(JsonObject jo)
	{
		this.requiredQuest = QuestList.get(jo.get("requiredQuest").getAsString());
		this.required = (this.requiredQuest == null ? null : this.requiredQuest.achievement);
		if(jo.has("questName"))
		{
			this.questName = jo.get("questName").getAsString();
		}
		if(jo.has("uniqueName"))
		{
			this.uniqueName = jo.get("uniqueName").getAsString();
		}
		// Quest position on the screen
		int tmpX = jo.get("posX").getAsInt();
		this.posX = (this.requiredQuest != null ? (this.requiredQuest.posX + tmpX) : tmpX);
		int tmpY = jo.get("posY").getAsInt();
		this.posY = (this.requiredQuest != null ? (this.requiredQuest.posY + tmpY) : tmpY);
		if(jo.has("icon"))
		{
			this.icon = ItemStackHelper.getItemStackFromJSON(jo.get("icon").getAsJsonObject());
		}
		// Additional Quests
		if(jo.has("additionalRequiredQuests"))
		{
			JsonObject additionalRequiredQuests = jo.get("additionalRequiredQuests").getAsJsonObject();
			Set<Entry<String, JsonElement>> set = additionalRequiredQuests.entrySet();
			Quest[] requiredQuestsTable = new Quest[set.size()];
			int index = 0;
			for(Entry<String, JsonElement> q : set)
			{
				requiredQuestsTable[index] = QuestList.get(q.getValue().getAsString());
				index++;
			}
			this.additionalRequiredQuests = requiredQuestsTable;
		}
		// Building Achievement
		this.achievement = new Achievement(this.questName, 
				this.uniqueName, 
				this.posX, 
				this.posY, 
				this.icon, 
				this.required)
				.registerStat();
		// Registering Quest - this method should ONLY be called here
		QuestList.addQuest(this);
	}
	
	public boolean canPlayerGetAchievement(EntityPlayer player)
	{
		if(!player.hasAchievement(achievement))
		{
			// Player need additional quests to be completed.
			if(hasAdditionalQuests())
			{
				return finishedAdditionalQuests(player);
			}
			return true;
		}
		return false;
	}
	
	public boolean hasAdditionalQuests()
	{
		return additionalRequiredQuests != null;
	}
	
	public boolean finishedAdditionalQuests(EntityPlayer player)
	{
		if(hasAdditionalQuests())
		{
			for(Quest quest : additionalRequiredQuests)
			{
				if(!player.hasAchievement(quest.achievement))
				{
					return false;
				}
			}
		}
		return true;
	}
}