package net.zam.castingcaving.registry;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.zam.castingcaving.CastingCaving;

import java.util.Map;

public class ZAMTrimMaterials {
    public static final ResourceKey<TrimMaterial> AQUAMARINE = ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "aquamarine"));
    public static final ResourceKey<TrimMaterial> OPAL = ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "opal"));
    public static final ResourceKey<TrimMaterial> PERIDOT = ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "peridot"));

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        register(context, AQUAMARINE, ZAMItems.AQUAMARINE.get(), Style.EMPTY.withColor(TextColor.parseColor("#00B5D8").getOrThrow()), 0.8F);
        register(context, OPAL, ZAMItems.OPAL.get(), Style.EMPTY.withColor(TextColor.parseColor("#D8BFEF").getOrThrow()), 0.1F);
        register(context, PERIDOT, ZAMItems.PERIDOT.get(), Style.EMPTY.withColor(TextColor.parseColor("#60B347").getOrThrow()), 0.7F);
    }

    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item,
                                 Style style, float itemModelIndex) {
        TrimMaterial trimmaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex,
                Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), Map.of());
        context.register(trimKey, trimmaterial);
    }
}
