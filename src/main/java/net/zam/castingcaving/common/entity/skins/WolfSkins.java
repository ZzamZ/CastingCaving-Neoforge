package net.zam.castingcaving.common.entity.skins;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.zam.castingcaving.CastingCaving;

public class WolfSkins extends WolfRenderer {

    private static final ResourceLocation HONEY_TAME_LOCATION = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "textures/entity/wolf/honey_tame.png");
    private static final ResourceLocation HONEY_WILD_LOCATION = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "textures/entity/wolf/honey_wild.png");
    private static final ResourceLocation HONEY_ANGRY_LOCATION = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "textures/entity/wolf/honey_angry.png");

    private static final ResourceLocation KIRBY_TAME_LOCATION = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "textures/entity/wolf/kirby_tame.png");
    private static final ResourceLocation KIRBY_WILD_LOCATION = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "textures/entity/wolf/kirby_wild.png");
    private static final ResourceLocation KIRBY_ANGRY_LOCATION = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "textures/entity/wolf/kirby_angry.png");

    public WolfSkins(EntityRendererProvider.Context p_174452_) {
        super(p_174452_);
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf pEntity) {
        String s = ChatFormatting.stripFormatting(pEntity.getName().getString());
        if ("Honey".equals(s)) {
            if (pEntity.isTame()) {
                return HONEY_TAME_LOCATION;
            } else if (pEntity.isAngry()) {
                return HONEY_ANGRY_LOCATION;
            } else {
                return HONEY_WILD_LOCATION;
            }
        }
        if ("Kirby".equals(s)) {
            if (pEntity.isTame()) {
                return KIRBY_TAME_LOCATION;
            } else if (pEntity.isAngry()) {
                return KIRBY_ANGRY_LOCATION;
            } else {
                return KIRBY_WILD_LOCATION;
            }
        }
        return super.getTextureLocation(pEntity); // Call the superclass method for other wolves.
    }
}
