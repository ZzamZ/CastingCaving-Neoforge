package net.zam.castingcaving.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMBlocks;
import net.zam.castingcaving.registry.ZAMItems;
import net.zam.castingcaving.util.integrations.NeoForgeItemTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ZAMItemTags extends ItemTagsProvider {
    public ZAMItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future,
                       CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, CastingCaving.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.HOES)
                .add(ZAMItems.MARINE_HOE.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ZAMItems.MARINE_HELMET.get())
                .add(ZAMItems.MARINE_CHESTPLATE.get())
                .add(ZAMItems.MARINE_LEGGINGS.get())
                .add(ZAMItems.MARINE_BOOTS.get());

        this.tag(ItemTags.TRIM_TEMPLATES)
                .add(ZAMItems.ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE.get());

        this.tag(ItemTags.TRIM_MATERIALS)
                .add(ZAMItems.AQUAMARINE.get())
                .add(ZAMItems.OPAL.get())
                .add(ZAMItems.PERIDOT.get());


        this.tag(NeoForgeItemTags.AQUAMARINE).add(ZAMItems.AQUAMARINE.get());
        this.tag(NeoForgeItemTags.OPAL).add(ZAMItems.OPAL.get());
        this.tag(NeoForgeItemTags.PERIDOT).add(ZAMItems.PERIDOT.get());

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ZAMBlocks.MARINE_LOG.get().asItem())
                .add(ZAMBlocks.MARINE_WOOD.get().asItem())
                .add(ZAMBlocks.STRIPPED_MARINE_LOG.get().asItem())
                .add(ZAMBlocks.STRIPPED_MARINE_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(ZAMBlocks.MARINE_PLANKS.get().asItem());



    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
