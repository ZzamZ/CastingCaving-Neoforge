package net.zam.castingcaving.common.client.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.zam.castingcaving.common.block.RefinedAnvilBlock;
import net.zam.castingcaving.common.block.entity.RefinedAnvilBlockEntity;

public class RefinedAnvilRenderer implements BlockEntityRenderer<RefinedAnvilBlockEntity> {

    public RefinedAnvilRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(RefinedAnvilBlockEntity anvilEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack storedItem = anvilEntity.getStoredItem();
        if (!storedItem.isEmpty()) {
            poseStack.pushPose();

            Direction facing = anvilEntity.getBlockState().getValue(RefinedAnvilBlock.FACING);
            int seed = (int) anvilEntity.getBlockPos().asLong();

            renderGeodeOnAnvil(poseStack, facing);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(
                    storedItem,
                    ItemDisplayContext.FIXED,
                    combinedLight,
                    combinedOverlay,
                    poseStack,
                    buffer,
                    anvilEntity.getLevel(),
                    seed
            );

            poseStack.popPose();
        }
    }

    private void renderGeodeOnAnvil(PoseStack poseStack, Direction facing) {
        // Center the item on top of the anvil
        poseStack.translate(0.5D, 1.D, 0.5D); // Adjust Y as needed

        // Rotate to match anvil facing
        float rotationY = -facing.toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationY));

        // Rotate 90 degrees around Y-axis before laying flat
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));

        // Lay flat on the surface
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

        // Scale to fit
        poseStack.scale(0.6F, 0.6F, 0.6F);
    }
}