package net.zam.castingcaving.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zam.castingcaving.registry.ZAMBlockEntities;

public class ZAMSignBlockEntity extends SignBlockEntity {
    public ZAMSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ZAMBlockEntities.MARINE_SIGN.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ZAMBlockEntities.MARINE_SIGN.get();
    }
}
