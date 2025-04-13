package net.zam.castingcaving.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zam.castingcaving.CastingCaving;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ZAMPaintingVariantTagProvider extends PaintingVariantTagsProvider {
    public ZAMPaintingVariantTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, CastingCaving.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256017_) {
    //    this.tag(PaintingVariantTags.PLACEABLE)
     //           .addOptional(new ResourceLocation(CastingCaving.MOD_ID, "bold_and_brash"));
    }
}
