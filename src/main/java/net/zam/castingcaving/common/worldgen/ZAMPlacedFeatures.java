package net.zam.castingcaving.common.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMBlocks;

import java.util.List;

public class ZAMPlacedFeatures {
    public static final ResourceKey<PlacedFeature> PERIDOT_ORE_PLACED_KEY = registerKey("peridot_ore_placed");
    public static final ResourceKey<PlacedFeature> OPAL_ORE_PLACED_KEY = registerKey("opal_ore_placed");
    public static final ResourceKey<PlacedFeature> AQUAMARINE_ORE_PLACED_KEY = registerKey("aquamarine_ore_placed");
    public static final ResourceKey<PlacedFeature> EMERALD_GEODE_PLACED_KEY = registerKey("emerald_geode_placed");
    public static final ResourceKey<PlacedFeature>MARINE_PLACED_KEY = registerKey("marine_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, PERIDOT_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ZAMConfiguredFeatures.PERIDOT_ORE_KEY),
                ZAMOrePlacement.commonOrePlacement(5,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(34))));
        register(context, OPAL_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ZAMConfiguredFeatures.OPAL_ORE_KEY),
                ZAMOrePlacement.commonOrePlacement(5,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(5))));
        register(context, AQUAMARINE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ZAMConfiguredFeatures.AQUAMARINE_ORE_KEY),
                ZAMOrePlacement.commonOrePlacement(5,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-5), VerticalAnchor.absolute(73))));
        register(context, EMERALD_GEODE_PLACED_KEY, configuredFeatures.getOrThrow(ZAMConfiguredFeatures.EMERALD_GEODE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(-30),
                                VerticalAnchor.absolute(10)), BiomeFilter.biome()));
        register(context, MARINE_PLACED_KEY, configuredFeatures.getOrThrow(ZAMConfiguredFeatures.MARINE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        ZAMBlocks.MARINE_SAPLING.get()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
