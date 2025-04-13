package net.zam.castingcaving.common.client.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.client.entity.animations.OtterAnimations;
import net.zam.castingcaving.common.entity.OtterEntity;

public class OtterModel extends HierarchicalModel<OtterEntity> {
    private final ModelPart otter;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart legs;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart tail;

    public OtterModel(ModelPart root) {
        this.otter = root.getChild("otter");
        this.head = otter.getChild("head");
        this.body = otter.getChild("body");
        this.legs = otter.getChild("legs");
        this.leg1 = legs.getChild("leg1");
        this.leg2 = legs.getChild("leg2");
        this.leg3 = legs.getChild("leg3");
        this.leg4 = legs.getChild("leg4");
        this.tail = otter.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition otter = partdefinition.addOrReplaceChild("otter", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = otter.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -3.25F, -2.5833F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-2.0F, -0.25F, -3.5833F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 20).addBox(3.0F, -2.25F, 1.4167F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-4.0F, -2.25F, 1.4167F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 17).addBox(3.0F, -0.25F, -0.5833F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-6.0F, -0.25F, -0.5833F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.75F, -7.8167F));

        PartDefinition body = otter.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5F, -6.0F, 5.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, -0.4F));

        PartDefinition legs = otter.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.4F));

        PartDefinition leg1 = legs.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.95F, -2.5F, -1.5F));

        PartDefinition leg2 = legs.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(28, 17).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.95F, -2.5F, -1.5F));

        PartDefinition leg3 = legs.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 28).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -2.5F, 6.05F));

        PartDefinition leg4 = legs.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(22, 0).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.95F, -2.5F, 6.1F));

        PartDefinition tail = otter.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(14, 18).addBox(-1.0F, -1.5F, -5.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, 9.6F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(OtterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        if (entity.isInWater()) {
            this.animate(entity.swimAnimationState, OtterAnimations.OTTER_SWIM, ageInTicks, 1f);
        } else if (limbSwingAmount > 0.1F) {
            this.animateWalk(OtterAnimations.OTTER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        } else {
            this.animate(entity.idleAnimationState, OtterAnimations.OTTER_IDLE, ageInTicks, 1f);
        }
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        otter.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return otter;
    }
}