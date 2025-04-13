package net.zam.castingcaving.common.block.trophy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class TrophyBlockEntityRenderer implements BlockEntityRenderer<TrophyBlockEntity> {
    private static final int LIGHT_PURPLE_COLOR = 0xFF55FF; // Light purple color

    public TrophyBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(TrophyBlockEntity te, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        String playerName = te.getPlayerName();
        if (playerName.isEmpty()) return;

        String line1 = "Arcade Champion";
        String line2 = playerName;

        poseStack.pushPose();

        // Position text above the block
        poseStack.translate(0.5, 1.5, 0.5);

        // Rotate to face the player
        float rotationAngle = getRotationAngle(te);
        poseStack.mulPose(Axis.YP.rotationDegrees(-rotationAngle));

        // Scale text
        poseStack.scale(-0.025f, -0.025f, 0.025f);

        // Render lines of text
        Font font = Minecraft.getInstance().font;
        int line1Width = font.width(line1);
        int line2Width = font.width(line2);

        font.drawInBatch(
                line1,
                -line1Width / 2f, -10, LIGHT_PURPLE_COLOR, // Top line
                false, poseStack.last().pose(), buffer,
                Font.DisplayMode.NORMAL, 0, combinedLight);

        font.drawInBatch(
                line2,
                -line2Width / 2f, 0, LIGHT_PURPLE_COLOR, // Bottom line
                false, poseStack.last().pose(), buffer,
                Font.DisplayMode.NORMAL, 0, combinedLight);

        poseStack.popPose();
    }

    private float getRotationAngle(TrophyBlockEntity te) {
        Vec3 cameraPosition = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        Vec3 blockPosition = Vec3.atCenterOf(te.getBlockPos());
        double dx = cameraPosition.x - blockPosition.x;
        double dz = cameraPosition.z - blockPosition.z;
        return (float) (Math.atan2(dz, dx) * (180 / Math.PI)) + 90;
    }
}

