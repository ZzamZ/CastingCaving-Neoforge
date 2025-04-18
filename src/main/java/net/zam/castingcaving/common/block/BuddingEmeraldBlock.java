package net.zam.castingcaving.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.zam.castingcaving.registry.ZAMBlocks;

public class BuddingEmeraldBlock extends AmethystBlock {
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingEmeraldBlock(Properties p_152726_) {
        super(p_152726_);
    }

    public void randomTick(BlockState p_220898_, ServerLevel p_220899_, BlockPos p_220900_, RandomSource p_220901_) {
        if (p_220901_.nextInt(5) == 0) {
            Direction $$4 = DIRECTIONS[p_220901_.nextInt(DIRECTIONS.length)];
            BlockPos $$5 = p_220900_.relative($$4);
            BlockState $$6 = p_220899_.getBlockState($$5);
            Block $$7 = null;
            if (canClusterGrowAtState($$6)) {
                $$7 = ZAMBlocks.SMALL_EMERALD_BUD.get();
            } else if ($$6.is(ZAMBlocks.SMALL_EMERALD_BUD.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = ZAMBlocks.MEDIUM_EMERALD_BUD.get();
            } else if ($$6.is(ZAMBlocks.MEDIUM_EMERALD_BUD.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = ZAMBlocks.LARGE_EMERALD_BUD.get();
            } else if ($$6.is(ZAMBlocks.LARGE_EMERALD_BUD.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = ZAMBlocks.EMERALD_CLUSTER.get();
            }

            if ($$7 != null) {
                BlockState $$8 = (BlockState)((BlockState)$$7.defaultBlockState().setValue(AmethystClusterBlock.FACING, $$4)).setValue(AmethystClusterBlock.WATERLOGGED, $$6.getFluidState().getType() == Fluids.WATER);
                p_220899_.setBlockAndUpdate($$5, $$8);
            }

        }
    }

    public static boolean canClusterGrowAtState(BlockState p_152735_) {
        return p_152735_.isAir() || p_152735_.is(Blocks.WATER) && p_152735_.getFluidState().getAmount() == 8;
    }
}
