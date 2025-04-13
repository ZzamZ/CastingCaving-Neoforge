package net.zam.castingcaving.common.client.entity.layers;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.zam.castingcaving.CastingCaving;

public class ZAMModelLayers {
    public static final ModelLayerLocation MARINE_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "boat/marine"), "main");
    public static final ModelLayerLocation MARINE_CHEST_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "chest_boat/marine"), "main");
    public static final ModelLayerLocation OTTER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "otter"), "main");
}
