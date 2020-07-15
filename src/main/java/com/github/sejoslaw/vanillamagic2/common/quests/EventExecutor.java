package com.github.sejoslaw.vanillamagic2.common.quests;

import com.github.sejoslaw.vanillamagic2.common.functions.Action;
import com.github.sejoslaw.vanillamagic2.common.functions.Consumer3;
import com.github.sejoslaw.vanillamagic2.common.registries.PlayerQuestProgressRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.function.Consumer;

/**
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public final class EventExecutor {
    private final EventCaller<? extends Quest> caller;

    public EventExecutor(EventCaller<? extends Quest> caller) {
        this.caller = caller;
    }

    public void onBlockBreak(BlockEvent.BreakEvent event) {
    }

    /**
     * Represents: RightClickBlock, RightClickItem, RightClickEmpty, LeftClickBlock, LeftClickEmpty
     * @param event
     * @param consumer
     */
    public void onPlayerInteract(PlayerInteractEvent event, Consumer3<PlayerEntity, BlockPos, Direction> consumer) {
        PlayerEntity player = event.getPlayer();
        performCheck(player, () -> consumer.accept(player, event.getPos(), event.getFace()));
    }

    public void onItemTooltip(ItemTooltipEvent event) {
    }

    public void onEntityPlace(BlockEvent.EntityPlaceEvent event) {
    }

    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
    }

    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
    }

    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
    }

    public void onItemPickup(PlayerEvent.ItemPickupEvent event) {
    }

    public void onLivingDrops(LivingDropsEvent event) {
    }

    public void onEntityItemPickup(EntityItemPickupEvent event) {
    }

    public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
    }

    /*
     * -----====== Private Methods =====-----
     */

    private void performCheck(PlayerEntity player, Action action) {
         checkItemsInHands(player, (quest) -> checkQuestProgress(player, quest, action));
    }

    private void checkItemsInHands(PlayerEntity player, Consumer<Quest> consumer) {
        for (Quest quest : this.caller.quests) {
            if (ItemStack.areItemStacksEqual(player.getHeldItemOffhand(), quest.stackLeftHand) &&
                    ItemStack.areItemStacksEqual(player.getHeldItemMainhand(), quest.stackRightHand)) {
                consumer.accept(quest);
            }
        }
    }

    private void checkQuestProgress(PlayerEntity player, Quest quest, Action action) {
        String questUniqueName = quest.uniqueName;

        if (!PlayerQuestProgressRegistry.hasPlayerGotQuest(player, questUniqueName)) {
            if (PlayerQuestProgressRegistry.canPlayerGetQuest(player, questUniqueName)) {
                PlayerQuestProgressRegistry.givePlayerQuest(player, questUniqueName);
            } else {
                return;
            }
        }

        action.execute();
    }
}
