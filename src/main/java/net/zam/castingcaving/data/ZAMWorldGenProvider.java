package net.zam.castingcaving.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.worldgen.ZAMBiomeModifiers;
import net.zam.castingcaving.common.worldgen.ZAMConfiguredFeatures;
import net.zam.castingcaving.common.worldgen.ZAMPlacedFeatures;
import net.zam.castingcaving.registry.ZAMTrimMaterials;
import net.zam.castingcaving.registry.ZAMTrimPatterns;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ZAMWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ZAMConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ZAMPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ZAMBiomeModifiers::bootstrap)
            .add(Registries.TRIM_PATTERN, ZAMTrimPatterns::bootstrap)
            .add(Registries.ENCHANTMENT, ZAMEnchantments::bootstrap)
            .add(Registries.TRIM_MATERIAL, ZAMTrimMaterials::bootstrap);




    public ZAMWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(CastingCaving.MOD_ID));
    }
}
