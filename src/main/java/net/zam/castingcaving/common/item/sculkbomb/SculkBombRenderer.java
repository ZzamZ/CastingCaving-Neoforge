package net.zam.castingcaving.common.item.sculkbomb;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.zam.castingcaving.CastingCaving;

public class SculkBombRenderer extends EntityRenderer<SculkBombEntity> {
    private static final ResourceLocation SCULK_BOMB_TEXTURE = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "textures/entity/sculk_bomb.png");
    private final ItemRenderer itemRenderer;

    public SculkBombRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(SculkBombEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        ItemStack itemStack = new ItemStack(entity.getItem().getItem());
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);  // Adjust size as needed
        this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SculkBombEntity entity) {
        return SCULK_BOMB_TEXTURE;
    }
}
