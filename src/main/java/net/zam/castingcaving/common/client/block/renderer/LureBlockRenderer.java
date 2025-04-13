package net.zam.castingcaving.common.client.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.zam.castingcaving.common.block.chest.LostBounty;
import net.zam.castingcaving.common.block.entity.LostBountyBlockEntity;
import net.zam.castingcaving.registry.ZAMBlocks;

import javax.annotation.Nonnull;

public class LureBlockRenderer extends BlockEntityWithoutLevelRenderer {

    public LureBlockRenderer(BlockEntityRenderDispatcher renderDispatcher, EntityModelSet entityModelSet) {
        super(renderDispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, ItemDisplayContext itemDisplayContext, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int i, int i1) {
        Minecraft mc = Minecraft.getInstance();
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof LostBounty) {
                mc.getBlockEntityRenderDispatcher().renderItem(new LostBountyBlockEntity(BlockPos.ZERO, ZAMBlocks.LOST_BOUNTY.get().defaultBlockState()), matrixStack, buffer, i, i1);
            }
        }
    }
}