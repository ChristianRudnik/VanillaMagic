package com.github.sejoslaw.vanillamagic.api.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Base class for Events connected with Quest Jumper.
 *
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public class EventJumper extends EventPlayerOnWorld {
    private final BlockPos pos;

    public EventJumper(PlayerEntity player, World world, BlockPos pos) {
        super(player, world);
        this.pos = pos;
    }

    /**
     * It can be either position which Player wants to save OR position on which
     * Player wants to teleport.
     *
     * @return Returns position.
     */
    public BlockPos getPos() {
        return this.pos;
    }

    /**
     * This Event is fired when Player wants to save block position into book.
     */
    public static class SavePosition extends EventJumper {
        public SavePosition(PlayerEntity player, World world, BlockPos pos) {
            super(player, world, pos);
        }

        /**
         * This Event is fired BEFORE Player save position into book.
         */
        public static class Before extends SavePosition {
            public Before(PlayerEntity player, World world, BlockPos pos) {
                super(player, world, pos);
            }
        }

        /**
         * This Event is fired AFTER Player saved position into book.
         */
        public static class After extends SavePosition {
            public After(PlayerEntity player, World world, BlockPos pos) {
                super(player, world, pos);
            }
        }
    }

    /**
     * This Event is fired when Player wants to Teleport.
     */
    public static class Teleport extends EventJumper {
        private final int destinationDimId;

        public Teleport(PlayerEntity player, World world, BlockPos pos, int dimId) {
            super(player, world, pos);
            this.destinationDimId = dimId;
        }

        /**
         * For getting the current dimension of Player use {@link #getWorld()}
         *
         * @return Returns id of a dimension to which Player wants to teleport.
         */
        public int getDestinationDimensionId() {
            return this.destinationDimId;
        }

        /**
         * This Event is fired BEFORE Player teleports.
         */
        public static class Before extends Teleport {
            public Before(PlayerEntity player, World world, BlockPos pos, int dimId) {
                super(player, world, pos, dimId);
            }
        }

        /**
         * This Event is fired AFTER Player teleports.
         */
        public static class After extends Teleport {
            public After(PlayerEntity player, World world, BlockPos pos, int dimId) {
                super(player, world, pos, dimId);
            }
        }
    }
}