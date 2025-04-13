package net.zam.castingcaving.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ZAMBlockTags extends BlockTagsProvider {
    public ZAMBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CastingCaving.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.CLIMBABLE)
                .add(ZAMBlocks.SCAFFINITY.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ZAMBlocks.AQUAMARINE_ORE.get(),
                ZAMBlocks.DEEPSLATE_AQUAMARINE_ORE.get(),
                ZAMBlocks.PRISMATIC_BLOCK.get(),
                ZAMBlocks.OPAL_BLOCK.get(),
                ZAMBlocks.OPAL_ORE.get(),
                ZAMBlocks.DEEPSLATE_OPAL_ORE.get(),
                ZAMBlocks.PERIDOT_BLOCK.get(),
                ZAMBlocks.PERIDOT_ORE.get(),
                ZAMBlocks.DEEPSLATE_PERIDOT_ORE.get(),
                ZAMBlocks.EMERALD_CRYSTAL_BLOCK.get(),
                ZAMBlocks.BUDDING_EMERALD.get(),
                ZAMBlocks.EMERALD_CLUSTER.get(),
                ZAMBlocks.LARGE_EMERALD_BUD.get(),
                ZAMBlocks.MEDIUM_EMERALD_BUD.get(),
                ZAMBlocks.SMALL_EMERALD_BUD.get(),
                ZAMBlocks.LOST_BOUNTY.get(),
                ZAMBlocks.AQUAMARINE_BLOCK.get(),
                ZAMBlocks.AQUAMARINE_ORE.get(),
                ZAMBlocks.DEEPSLATE_AQUAMARINE_ORE.get(),
                ZAMBlocks.PRISMATIC_BLOCK.get(),
                ZAMBlocks.SCAFFINITY.get(),
                ZAMBlocks.LOST_BOUNTY.get(),
                ZAMBlocks.AQUAMARINE_BRICKS.get(),
                ZAMBlocks.OPAL_BRICKS.get(),
                ZAMBlocks.PERIDOT_BRICKS.get(),
                ZAMBlocks.CHISELED_AQUAMARINE_BRICKS.get(),
                ZAMBlocks.CHISELED_OPAL_BRICKS.get(),
                ZAMBlocks.CHISELED_PERIDOT_BRICKS.get(),
                ZAMBlocks.AQUAMARINE_BRICK_STAIRS.get(),
                ZAMBlocks.OPAL_BRICK_STAIRS.get(),
                ZAMBlocks.PERIDOT_BRICK_STAIRS.get(),
                ZAMBlocks.AQUAMARINE_BRICK_SLAB.get(),
                ZAMBlocks.OPAL_BRICK_SLAB.get(),
                ZAMBlocks.PERIDOT_BRICK_SLAB.get(),
                ZAMBlocks.AQUAMARINE_BRICK_WALL.get(),
                ZAMBlocks.OPAL_BRICK_WALL.get(),
                ZAMBlocks.PERIDOT_BRICK_WALL.get());

                this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                ZAMBlocks.OPAL_ORE.get(),
                ZAMBlocks.DEEPSLATE_OPAL_ORE.get(),
                ZAMBlocks.AQUAMARINE_ORE.get(),
                ZAMBlocks.DEEPSLATE_AQUAMARINE_ORE.get(),
                ZAMBlocks.PERIDOT_ORE.get(),
                ZAMBlocks.DEEPSLATE_PERIDOT_ORE.get());

        this.tag(BlockTags.WALLS).add(
                ZAMBlocks.AQUAMARINE_BRICK_WALL.get(),
                ZAMBlocks.OPAL_BRICK_WALL.get(),
                ZAMBlocks.PERIDOT_BRICK_WALL.get());

        this.tag(BlockTags.PLANKS)
                .add(ZAMBlocks.MARINE_PLANKS.get());

        this.tag(BlockTags.LOGS)
                .add(ZAMBlocks.MARINE_LOG.get())
                .add(ZAMBlocks.MARINE_WOOD.get())
                .add(ZAMBlocks.STRIPPED_MARINE_LOG.get())
                .add(ZAMBlocks.STRIPPED_MARINE_WOOD.get());

        this.tag(BlockTags.LEAVES)
                .add(ZAMBlocks.MARINE_LEAVES.get())
                .add(ZAMBlocks.MARINE_BERRY_LEAVES.get());

        this.tag(BlockTags.FENCES)
                .add(ZAMBlocks.MARINE_FENCE.get());

        this.tag(BlockTags.FENCE_GATES)
                .add(ZAMBlocks.MARINE_FENCE_GATE.get());
    }
}

