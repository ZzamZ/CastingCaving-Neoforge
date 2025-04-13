package net.zam.castingcaving.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zam.castingcaving.registry.ZAMBlockEntities;

import javax.annotation.Nonnull;

public class LostBountyBlockEntity extends ChestBlockEntity {

    public LostBountyBlockEntity(BlockPos pos, BlockState state) {
        super(ZAMBlockEntities.LOST_BOUNTY.get(), pos, state);
    }

    @Override
    @Nonnull
    public Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }
}