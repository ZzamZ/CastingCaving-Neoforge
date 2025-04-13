package net.zam.castingcaving.common.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.client.entity.layers.ZAMModelLayers;
import net.zam.castingcaving.common.client.entity.model.OtterModel;
import net.zam.castingcaving.common.entity.OtterEntity;

public class OtterRenderer extends MobRenderer<OtterEntity, OtterModel> {

    public OtterRenderer(EntityRendererProvider.Context context) {
        super(context, new OtterModel(context.bakeLayer(ZAMModelLayers.OTTER)), 0.35F);
    }

    @Override
    public ResourceLocation getTextureLocation(OtterEntity otterEntity) {
        return ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "textures/entity/otter/otter.png");
    }

    @Override
    public void render(OtterEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pPoseStack.scale(1.0f, 1.0f, 1.0f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}

