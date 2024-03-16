package gov.babalar.myth.utils;

import net.minecraft.BlockPos;
import net.minecraft.Qu;

public class BlockCache {

        private final BlockPos position;
        private final Qu facing;

        public BlockCache(final BlockPos position, final Qu facing) {
            this.position = position;
            this.facing = facing;
        }

        public BlockPos getPosition() {
            return this.position;
        }

        public Qu getFacing() {
            return this.facing;
        }
    }
