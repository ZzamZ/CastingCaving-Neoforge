package net.zam.castingcaving.common.entity.skins;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.frog.Frog;
import net.zam.castingcaving.CastingCaving;

public class FrogSkins extends FrogRenderer {
    private static final ResourceLocation THANOS_FROG_LOCATION = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "textures/entity/frog/thanos_frog.png");
    public FrogSkins(EntityRendererProvider.Context p_234619_) {
        super(p_234619_);
    }

    @Override
    public ResourceLocation getTextureLocation(Frog pEntity) {
        String s = ChatFormatting.stripFormatting(pEntity.getName().getString());
        if ("Thanos".equals(s)) {
            return THANOS_FROG_LOCATION;
        }
        return super.getTextureLocation(pEntity);
    }
}
