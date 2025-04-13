package net.zam.castingcaving.common.worldgen;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMBlocks;

import java.util.List;

public class ZAMConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> PERIDOT_ORE_KEY = registerKey("peridot_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OPAL_ORE_KEY = registerKey("opal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AQUAMARINE_ORE_KEY = registerKey("aquamarine_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMERALD_GEODE_KEY = registerKey("emerald_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARINE_KEY = registerKey("marine");
    
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> peridotOres = List.of(OreConfiguration.target(stoneReplaceables, ZAMBlocks.PERIDOT_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ZAMBlocks.DEEPSLATE_PERIDOT_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> opalOres = List.of(OreConfiguration.target(stoneReplaceables, ZAMBlocks.OPAL_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ZAMBlocks.DEEPSLATE_OPAL_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> aquamarineOres = List.of(OreConfiguration.target(stoneReplaceables, ZAMBlocks.AQUAMARINE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ZAMBlocks.DEEPSLATE_AQUAMARINE_ORE.get().defaultBlockState()));

        register(context, PERIDOT_ORE_KEY, Feature.ORE, new OreConfiguration(peridotOres, 5));
        register(context, OPAL_ORE_KEY, Feature.ORE, new OreConfiguration(opalOres, 5));
        register(context, AQUAMARINE_ORE_KEY, Feature.ORE, new OreConfiguration(aquamarineOres, 5));

        register(context, MARINE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ZAMBlocks.MARINE_LOG.get()),
                new ForkingTrunkPlacer(4, 2, 3), // Revert to original trunk settings
                new WeightedStateProvider(
                        new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(ZAMBlocks.MARINE_LEAVES.get().defaultBlockState(), 4) // 80% chance
                                .add(ZAMBlocks.MARINE_BERRY_LEAVES.get().defaultBlockState(), 1) // 20% chance
                                .build()
                ),
                new CherryFoliagePlacer(
                        ConstantInt.of(4), // Revert to original radius
                        ConstantInt.of(0),
                        ConstantInt.of(4),
                        0F, 0F, 0F, 0F
                ),
                new TwoLayersFeatureSize(1, 0, 2)
        ).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
        
        register(context, EMERALD_GEODE_KEY, Feature.GEODE, new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                BlockStateProvider.simple(ZAMBlocks.EMERALD_CRYSTAL_BLOCK.get()), BlockStateProvider.simple(ZAMBlocks.BUDDING_EMERALD.get()), BlockStateProvider.simple(Blocks.CALCITE),
                BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(ZAMBlocks.SMALL_EMERALD_BUD.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP).setValue(BlockStateProperties.WATERLOGGED, false),
                ZAMBlocks.MEDIUM_EMERALD_BUD.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP).setValue(BlockStateProperties.WATERLOGGED, false),
                ZAMBlocks.LARGE_EMERALD_BUD.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP).setValue(BlockStateProperties.WATERLOGGED, false),
                ZAMBlocks.EMERALD_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP).setValue(BlockStateProperties.WATERLOGGED, false)), BlockTags.FEATURES_CANNOT_REPLACE,
                BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D), new GeodeCrackSettings(0.95D, 2.0D, 2), 0.05D,
                0.35D, true, UniformInt.of(4, 6), UniformInt.of(3, 4), UniformInt.of(1, 2), -16, 16, 0.083D, 1));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
