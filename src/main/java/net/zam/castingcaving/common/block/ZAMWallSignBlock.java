package net.zam.castingcaving.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.zam.castingcaving.common.block.entity.ZAMSignBlockEntity;

public class ZAMWallSignBlock extends WallSignBlock {
    public ZAMWallSignBlock(Properties pProperties, WoodType pType) {
        super(pType, pProperties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ZAMSignBlockEntity(pPos, pState);
    }
}
