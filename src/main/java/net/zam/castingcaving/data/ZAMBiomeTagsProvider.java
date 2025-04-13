package net.zam.castingcaving.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zam.castingcaving.registry.ZAMTags;

import java.util.concurrent.CompletableFuture;

public class ZAMBiomeTagsProvider extends BiomeTagsProvider {
    public ZAMBiomeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, "castingcaving", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ZAMTags.HAS_AQUAMARINE_ORE)
                .addTag(BiomeTags.IS_BEACH)
                .addTag(BiomeTags.IS_OCEAN)
                .addTag(BiomeTags.IS_RIVER)
                .add(Biomes.LUSH_CAVES)
                .add(Biomes.STONY_SHORE);

        this.tag(ZAMTags.HAS_EMERALD_GEODE)
                .addTag(BiomeTags.IS_MOUNTAIN)
                .add(Biomes.GROVE);
    }
}
