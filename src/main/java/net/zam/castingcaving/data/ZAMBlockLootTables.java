package net.zam.castingcaving.data;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zam.castingcaving.registry.ZAMBlocks;
import net.zam.castingcaving.registry.ZAMItems;

import java.util.Set;

public class ZAMBlockLootTables extends BlockLootSubProvider {
    public ZAMBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        this.dropSelf(ZAMBlocks.LOST_BOUNTY.get());
        this.dropOther(ZAMBlocks.MARINE_FARMLAND.get(), Blocks.DIRT);
        this.dropSelf(ZAMBlocks.GLOW_INK_BLOCK.get());
        this.dropSelf(ZAMBlocks.ECHO_FROGLIGHT.get());
        this.dropSelf(ZAMBlocks.AQUAMARINE_BLOCK.get());
        this.add(ZAMBlocks.AQUAMARINE_ORE.get(), block -> this.createOreDrop(block, ZAMItems.AQUAMARINE.get()));
        this.add(ZAMBlocks.DEEPSLATE_AQUAMARINE_ORE.get(), block -> this.createOreDrop(block, ZAMItems.AQUAMARINE.get()));
        this.add(ZAMBlocks.OPAL_ORE.get(), block -> this.createOreDrop(block, ZAMItems.OPAL.get()));
        this.add(ZAMBlocks.DEEPSLATE_OPAL_ORE.get(), block -> this.createOreDrop(block, ZAMItems.OPAL.get()));
        this.add(ZAMBlocks.PERIDOT_ORE.get(), block -> this.createOreDrop(block, ZAMItems.PERIDOT.get()));
        this.add(ZAMBlocks.DEEPSLATE_PERIDOT_ORE.get(), block -> this.createOreDrop(block, ZAMItems.PERIDOT.get()));
        this.dropSelf(ZAMBlocks.PRISMATIC_BLOCK.get());
        this.dropSelf(ZAMBlocks.OPAL_BLOCK.get());
        this.dropSelf(ZAMBlocks.PERIDOT_BLOCK.get());
        this.dropSelf(ZAMBlocks.EMERALD_CRYSTAL_BLOCK.get());
        this.add(ZAMBlocks.BUDDING_EMERALD.get(), noDrop());
        this.dropSelf(ZAMBlocks.AQUAMARINE_BRICKS.get());
        this.dropSelf(ZAMBlocks.OPAL_BRICKS.get());
        this.dropSelf(ZAMBlocks.PERIDOT_BRICKS.get());
        this.dropSelf(ZAMBlocks.CHISELED_AQUAMARINE_BRICKS.get());
        this.dropSelf(ZAMBlocks.CHISELED_OPAL_BRICKS.get());
        this.dropSelf(ZAMBlocks.CHISELED_PERIDOT_BRICKS.get());
        this.dropSelf(ZAMBlocks.AQUAMARINE_BRICK_STAIRS.get());
        this.dropSelf(ZAMBlocks.OPAL_BRICK_STAIRS.get());
        this.dropSelf(ZAMBlocks.PERIDOT_BRICK_STAIRS.get());
        this.dropSelf(ZAMBlocks.AQUAMARINE_BRICK_SLAB.get());
        this.dropSelf(ZAMBlocks.OPAL_BRICK_SLAB.get());
        this.dropSelf(ZAMBlocks.PERIDOT_BRICK_SLAB.get());
        this.dropSelf(ZAMBlocks.AQUAMARINE_BRICK_WALL.get());
        this.dropSelf(ZAMBlocks.OPAL_BRICK_WALL.get());
        this.dropSelf(ZAMBlocks.PERIDOT_BRICK_WALL.get());
        this.dropSelf(ZAMBlocks.MARINE_LOG.get());
        this.dropSelf(ZAMBlocks.MARINE_WOOD.get());
        this.dropSelf(ZAMBlocks.STRIPPED_MARINE_LOG.get());
        this.dropSelf(ZAMBlocks.STRIPPED_MARINE_WOOD.get());
        this.dropSelf(ZAMBlocks.MARINE_SAPLING.get());
        this.dropSelf(ZAMBlocks.MARINE_PLANKS.get());
        this.dropSelf(ZAMBlocks.MARINE_BERRY_BLOCK.get());
        this.dropSelf(ZAMBlocks.REFINED_ANVIL.get());
        this.dropSelf(ZAMBlocks.ARCADE_MACHINE.get());
        this.dropSelf(ZAMBlocks.TROPHY_BLOCK.get());
        this.dropSelf(ZAMBlocks.PRISMATIC_FORGE.get());

        this.add(ZAMBlocks.MARINE_LEAVES.get(), block -> createLeavesDrops(block, ZAMBlocks.MARINE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.dropPottedContents(ZAMBlocks.POTTED_MARINE_SAPLING.get());
        this.add(ZAMBlocks.MARINE_SIGN.get(), block -> createSingleItemTable(ZAMItems.MARINE_SIGN.get()));
        this.add(ZAMBlocks.MARINE_WALL_SIGN.get(), block -> createSingleItemTable(ZAMItems.MARINE_SIGN.get()));
        this.add(ZAMBlocks.MARINE_HANGING_SIGN.get(), block -> createSingleItemTable(ZAMItems.MARINE_HANGING_SIGN.get()));
        this.add(ZAMBlocks.MARINE_WALL_HANGING_SIGN.get(), block -> createSingleItemTable(ZAMItems.MARINE_HANGING_SIGN.get()));

        this.dropSelf(ZAMBlocks.MARINE_TRAPDOOR.get());
        this.add(ZAMBlocks.MARINE_DOOR.get(), block -> createDoorTable(ZAMBlocks.MARINE_DOOR.get()));

        this.dropSelf(ZAMBlocks.MARINE_STAIRS.get());
        this.add(ZAMBlocks.MARINE_SLAB.get(), block -> createSlabItemTable(ZAMBlocks.MARINE_SLAB.get()));
        this.dropSelf(ZAMBlocks.MARINE_PRESSURE_PLATE.get());
        this.dropSelf(ZAMBlocks.MARINE_BUTTON.get());
        this.dropSelf(ZAMBlocks.MARINE_FENCE.get());
        this.dropSelf(ZAMBlocks.MARINE_FENCE_GATE.get());
        this.add(ZAMBlocks.MARINE_BERRY_LEAVES.get(), block -> createMarineBerryLeavesDrop(block, ZAMItems.MARINE_BERRY));

        this.add(ZAMBlocks.EMERALD_CLUSTER.get(),
                p_344203_ -> this.createSilkTouchDispatchTable(
                        p_344203_,
                        LootItem.lootTableItem(ZAMItems.EMERALD_SHARD)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES)))
                                .otherwise(
                                        this.applyExplosionDecay(
                                                p_344203_, LootItem.lootTableItem(ZAMItems.EMERALD_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                                        )
                                )
                )
        );
        this.dropWhenSilkTouch(ZAMBlocks.SMALL_EMERALD_BUD.get());
        this.dropWhenSilkTouch(ZAMBlocks.MEDIUM_EMERALD_BUD.get());
        this.dropWhenSilkTouch(ZAMBlocks.LARGE_EMERALD_BUD.get());
    }

    private LootTable.Builder createMarineBerryLeavesDrop(Block block, DeferredHolder<Item, Item> berryItem) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block)
                                .when(this.hasSilkTouch()))
                        .add(LootItem.lootTableItem(berryItem.get())
                                .when(this.doesNotHaveSilkTouch())
                                .when(LootItemRandomChanceCondition.randomChance(0.25f))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ZAMBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}