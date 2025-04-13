package net.zam.castingcaving.common.block;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.zam.castingcaving.registry.ZAMBlocks;
import org.jetbrains.annotations.Nullable;

public class MarineLogBlock extends RotatedPillarBlock {
    public MarineLogBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            if (state.is(ZAMBlocks.MARINE_LOG.get())) {
                return ZAMBlocks.STRIPPED_MARINE_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if (state.is(ZAMBlocks.MARINE_WOOD.get())) {
                return ZAMBlocks.STRIPPED_MARINE_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}


