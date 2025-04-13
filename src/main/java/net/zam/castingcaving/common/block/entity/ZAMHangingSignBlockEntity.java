package net.zam.castingcaving.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zam.castingcaving.registry.ZAMBlockEntities;

public class ZAMHangingSignBlockEntity extends SignBlockEntity {
    public ZAMHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ZAMBlockEntities.MARINE_HANGING_SIGN.get(), blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ZAMBlockEntities.MARINE_HANGING_SIGN.get();
    }
}
