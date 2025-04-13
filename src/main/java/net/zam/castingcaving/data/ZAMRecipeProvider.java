package net.zam.castingcaving.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMBlocks;
import net.zam.castingcaving.registry.ZAMItems;
import net.zam.castingcaving.registry.ZAMTags;

import java.util.concurrent.CompletableFuture;

public class ZAMRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ZAMRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        trimSmithing(pRecipeOutput, ZAMItems.ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), CastingCaving.id(getItemName(ZAMItems.ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ZAMItems.DIAMOND_FISHING_ROD.get()),
                        Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.MISC, ZAMItems.NETHERITE_FISHING_ROD.get())
                .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(pRecipeOutput, CastingCaving.id(getItemName(ZAMItems.NETHERITE_FISHING_ROD.get())));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_WOOD.get(), 3)
                .pattern("GG ")
                .pattern("GG ")
                .define('G', ZAMBlocks.MARINE_LOG.get())
                .unlockedBy(getHasName(ZAMBlocks.MARINE_LOG.get()), has(ZAMBlocks.MARINE_LOG.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.STRIPPED_MARINE_WOOD.get(), 3)
                .pattern("GG ")
                .pattern("GG ")
                .define('G', ZAMBlocks.STRIPPED_MARINE_LOG.get())
                .unlockedBy(getHasName(ZAMBlocks.STRIPPED_MARINE_LOG.get()), has(ZAMBlocks.STRIPPED_MARINE_LOG.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_PRESSURE_PLATE.get())
                .pattern("GG ")
                .define('G', ZAMBlocks.MARINE_PLANKS.get())
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_SLAB.get(), 6)
                .pattern("GGG")
                .define('G', ZAMBlocks.MARINE_PLANKS.get())
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_STAIRS.get(), 4)
                .pattern("G  ")
                .pattern("GG ")
                .pattern("GGG")
                .define('G', ZAMBlocks.MARINE_PLANKS.get())
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_DOOR.get(), 3)
                .pattern("GG ")
                .pattern("GG ")
                .pattern("GG ")
                .define('G', ZAMBlocks.MARINE_PLANKS.get())
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_FENCE.get(), 3)
                .pattern("GSG")
                .pattern("GSG")
                .define('G', ZAMBlocks.MARINE_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMItems.MARINE_SIGN.get(), 3)
                .pattern("GGG")
                .pattern("GGG")
                .pattern(" S ")
                .define('G', ZAMBlocks.MARINE_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_TRAPDOOR.get(), 2)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', ZAMBlocks.MARINE_PLANKS.get())
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_BUTTON.get())
                .requires(ZAMBlocks.MARINE_PLANKS.get())
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_PLANKS.get(), 4)
                .requires(Ingredient.of(ZAMTags.MARINE_LOGS))
                .unlockedBy(getHasName(ZAMBlocks.MARINE_LOG.get()), has(ZAMBlocks.MARINE_LOG.get()))
                .save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, ZAMItems.MARINE_CHEST_BOAT.get())
                .requires(Blocks.CHEST)
                .requires(ZAMItems.MARINE_BOAT.get())
                .unlockedBy(getHasName(ZAMItems.MARINE_CHEST_BOAT.get()), has(ZAMItems.MARINE_CHEST_BOAT.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.MARINE_FENCE_GATE.get())
                .pattern("GSG")
                .pattern("GSG")
                .define('S', ZAMBlocks.MARINE_PLANKS.get())
                .define('G', Items.STICK)
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMItems.MARINE_HANGING_SIGN.get())
                .pattern("C C")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ZAMBlocks.STRIPPED_MARINE_LOG.get())
                .define('C', Items.CHAIN)
                .unlockedBy(getHasName(ZAMBlocks.STRIPPED_MARINE_LOG.get()), has(ZAMBlocks.STRIPPED_MARINE_LOG.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ZAMItems.MARINE_BOAT.get())
                .pattern("G G")
                .pattern("GGG")
                .define('G', ZAMBlocks.MARINE_PLANKS.get())
                .unlockedBy(getHasName(ZAMBlocks.MARINE_PLANKS.get()), has(ZAMBlocks.MARINE_PLANKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.GLOW_INK_BLOCK.get())
                .pattern("GGG")
                .pattern("GGG")
                .pattern("GGG")
                .define('G', Items.GLOW_INK_SAC)
                .unlockedBy(getHasName(Items.GLOW_INK_SAC), has(Items.GLOW_INK_SAC))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.AQUAMARINE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ZAMItems.AQUAMARINE.get())
                .unlockedBy(getHasName(ZAMItems.AQUAMARINE.get()), has(ZAMItems.AQUAMARINE.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.OPAL_BLOCK.get())
                .pattern("OOO")
                .pattern("OOO")
                .pattern("OOO")
                .define('O', ZAMItems.OPAL.get())
                .unlockedBy(getHasName(ZAMItems.OPAL.get()), has(ZAMItems.OPAL.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PERIDOT_BLOCK.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP")
                .define('P', ZAMItems.PERIDOT.get())
                .unlockedBy(getHasName(ZAMItems.PERIDOT.get()), has(ZAMItems.PERIDOT.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PRISMATIC_BLOCK.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP")
                .define('P', ZAMItems.PRISMATIC_SHARD.get())
                .unlockedBy(getHasName(ZAMItems.PRISMATIC_SHARD.get()), has(ZAMItems.PRISMATIC_SHARD.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.EMERALD_CRYSTAL_BLOCK.get())
                .pattern("EE ")
                .pattern("EE ")
                .define('E', ZAMItems.EMERALD_SHARD.get())
                .unlockedBy(getHasName(ZAMItems.EMERALD_SHARD.get()), has(ZAMItems.EMERALD_SHARD.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMItems.OPAL_WAND.get())
                .pattern(" EO")
                .pattern(" BE")
                .pattern("E  ")
                .define('E', ZAMItems.EMERALD_SHARD.get())
                .define('O', ZAMItems.OPAL.get())
                .define('B', Items.BLAZE_ROD)
                .unlockedBy(getHasName(ZAMItems.OPAL.get()), has(ZAMItems.OPAL.get()))
                .save(pRecipeOutput);
     //  ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.ARCADE_MACHINE.get())
     //          .pattern("BEB")
     //          .pattern("RPR")
     //          .pattern("OAp")
     //          .define('B', Blocks.BLACK_CONCRETE)
     //          .define('R', Blocks.REDSTONE_BLOCK)
     //          .define('P', ZAMItems.PRISMATIC_SHARD.get())
     //          .define('E', ZAMBlocks.ECHO_FROGLIGHT.get())
     //          .define('O', ZAMBlocks.OPAL_BLOCK.get())
     //          .define('A', ZAMBlocks.AQUAMARINE_BLOCK.get())
     //          .define('p', ZAMBlocks.PERIDOT_BLOCK.get())
     //          .unlockedBy(getHasName(ZAMItems.PRISMATIC_SHARD.get()), has(ZAMItems.PRISMATIC_SHARD.get()))
     //          .save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ZAMItems.SCULK_BOMB.get(), 2)
                .requires(Items.SNOWBALL)
                .requires(Items.ECHO_SHARD)
                .requires(Items.GUNPOWDER)
                .unlockedBy(getHasName(Items.ECHO_SHARD), has(Items.ECHO_SHARD))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.AQUAMARINE_BRICKS.get(), 4)
                .pattern("AA ")
                .pattern("AA ")
                .define('A', ZAMBlocks.AQUAMARINE_BLOCK.get())
                .unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BLOCK.get()), has(ZAMBlocks.AQUAMARINE_BLOCK.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.OPAL_BRICKS.get(), 4)
                .pattern("AA ")
                .pattern("AA ")
                .define('A', ZAMBlocks.OPAL_BLOCK.get())
                .unlockedBy(getHasName(ZAMBlocks.OPAL_BLOCK.get()), has(ZAMBlocks.OPAL_BLOCK.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PERIDOT_BRICKS.get(), 4)
                .pattern("AA ")
                .pattern("AA ")
                .define('A', ZAMBlocks.PERIDOT_BLOCK.get())
                .unlockedBy(getHasName(ZAMBlocks.PERIDOT_BLOCK.get()), has(ZAMBlocks.PERIDOT_BLOCK.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.CHISELED_AQUAMARINE_BRICKS.get())
                .pattern(" A ")
                .pattern(" A ")
                .define('A', ZAMBlocks.AQUAMARINE_BRICK_SLAB.get())
                .unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BRICK_SLAB.get()), has(ZAMBlocks.AQUAMARINE_BRICK_SLAB.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.CHISELED_OPAL_BRICKS.get())
                .pattern(" A ")
                .pattern(" A ")
                .define('A', ZAMBlocks.OPAL_BRICK_SLAB.get())
                .unlockedBy(getHasName(ZAMBlocks.OPAL_BRICK_SLAB.get()), has(ZAMBlocks.OPAL_BRICK_SLAB.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.CHISELED_PERIDOT_BRICKS.get())
                .pattern(" A ")
                .pattern(" A ")
                .define('A', ZAMBlocks.PERIDOT_BRICK_SLAB.get())
                .unlockedBy(getHasName(ZAMBlocks.PERIDOT_BRICK_SLAB.get()), has(ZAMBlocks.PERIDOT_BRICK_SLAB.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.AQUAMARINE_BRICK_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', ZAMBlocks.AQUAMARINE_BRICKS.get())
                .unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BRICKS.get()), has(ZAMBlocks.AQUAMARINE_BRICKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.OPAL_BRICK_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', ZAMBlocks.OPAL_BRICKS.get())
                .unlockedBy(getHasName(ZAMBlocks.OPAL_BRICKS.get()), has(ZAMBlocks.OPAL_BRICKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PERIDOT_BRICK_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', ZAMBlocks.PERIDOT_BRICKS.get())
                .unlockedBy(getHasName(ZAMBlocks.PERIDOT_BRICKS.get()), has(ZAMBlocks.PERIDOT_BRICKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.AQUAMARINE_BRICK_SLAB.get(), 6)
                .pattern("AAA")
                .define('A', ZAMBlocks.AQUAMARINE_BRICKS.get())
                .unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BRICKS.get()), has(ZAMBlocks.AQUAMARINE_BRICKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.OPAL_BRICK_SLAB.get(), 6)
                .pattern("AAA")
                .define('A', ZAMBlocks.OPAL_BRICKS.get())
                .unlockedBy(getHasName(ZAMBlocks.OPAL_BRICKS.get()), has(ZAMBlocks.OPAL_BRICKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PERIDOT_BRICK_SLAB.get(), 6)
                .pattern("AAA")
                .define('A', ZAMBlocks.PERIDOT_BRICKS.get())
                .unlockedBy(getHasName(ZAMBlocks.PERIDOT_BRICKS.get()), has(ZAMBlocks.PERIDOT_BRICKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.AQUAMARINE_BRICK_WALL.get(), 6)
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ZAMBlocks.AQUAMARINE_BRICKS.get())
                .unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BRICKS.get()), has(ZAMBlocks.AQUAMARINE_BRICKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.OPAL_BRICK_WALL.get(), 6)
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ZAMBlocks.OPAL_BRICKS.get())
                .unlockedBy(getHasName(ZAMBlocks.OPAL_BRICKS.get()), has(ZAMBlocks.OPAL_BRICKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PERIDOT_BRICK_WALL.get(), 6)
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ZAMBlocks.PERIDOT_BRICKS.get())
                .unlockedBy(getHasName(ZAMBlocks.PERIDOT_BRICKS.get()), has(ZAMBlocks.PERIDOT_BRICKS.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMItems.TOTEM_OF_RETURNING.get())
                .pattern("AEA")
                .pattern("ATA")
                .pattern("ADA")
                .define('T', Items.TOTEM_OF_UNDYING)
                .define('D', Items.DRAGON_BREATH)
                .define('E', ZAMItems.EMERALD_SHARD.get())
                .define('A', ZAMItems.AQUAMARINE.get())
                .unlockedBy(getHasName(ZAMItems.EMERALD_SHARD.get()), has(ZAMItems.EMERALD_SHARD.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMItems.ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
                .pattern("DTD")
                .pattern("DAD")
                .pattern("DDD")
                .define('T', ZAMItems.ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE.get())
                .define('D', Items.DIAMOND)
                .define('A', ZAMItems.AQUAMARINE.get())
                .unlockedBy(getHasName(ZAMItems.ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE.get()), has(ZAMItems.ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                .save(pRecipeOutput,  "castingcaving:angler_armor_trim_smithing_template_duplicate");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMItems.IRON_FISHING_ROD.get(), 1)
                .pattern("III")
                .pattern("IFI")
                .pattern("III")
                .define('F', Items.FISHING_ROD)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.FISHING_ROD), has(Items.FISHING_ROD))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMItems.GOLD_FISHING_ROD.get(), 1)
                .pattern("III")
                .pattern("IFI")
                .pattern("III")
                .define('F', ZAMItems.IRON_FISHING_ROD.get())
                .define('I', Items.GOLD_INGOT)
                .unlockedBy(getHasName(ZAMItems.IRON_FISHING_ROD.get()), has(ZAMItems.IRON_FISHING_ROD.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZAMItems.DIAMOND_FISHING_ROD.get(), 1)
                .pattern("III")
                .pattern("IFI")
                .pattern("III")
                .define('F', ZAMItems.GOLD_FISHING_ROD.get())
                .define('I', Items.DIAMOND)
                .unlockedBy(getHasName(ZAMItems.GOLD_FISHING_ROD.get()), has(ZAMItems.GOLD_FISHING_ROD.get()))
                        .save(pRecipeOutput);

        // Stonecutter recipes for brick variants
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.AQUAMARINE_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.AQUAMARINE_BRICKS.get(), 1).unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BLOCK.get()), has(ZAMBlocks.AQUAMARINE_BLOCK.get())).save(pRecipeOutput, "castingcaving:aquamarine_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.OPAL_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.OPAL_BRICKS.get(), 1).unlockedBy(getHasName(ZAMBlocks.OPAL_BLOCK.get()), has(ZAMBlocks.OPAL_BLOCK.get())).save(pRecipeOutput, "castingcaving:opal_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.PERIDOT_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PERIDOT_BRICKS.get(), 1).unlockedBy(getHasName(ZAMBlocks.PERIDOT_BLOCK.get()), has(ZAMBlocks.PERIDOT_BLOCK.get())).save(pRecipeOutput, "castingcaving:peridot_bricks_stonecutting");

// Stonecutter recipes for chiseled blocks using the base block or bricks
        SingleItemRecipeBuilder.stonecutting(
                        Ingredient.of(ZAMBlocks.AQUAMARINE_BLOCK.get(), ZAMBlocks.AQUAMARINE_BRICKS.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ZAMBlocks.CHISELED_AQUAMARINE_BRICKS.get(),
                        1
                ).unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BLOCK.get()), has(ZAMBlocks.AQUAMARINE_BLOCK.get()))
                .save(pRecipeOutput, "castingcaving:chiseled_aquamarine_bricks_stonecutting");

        SingleItemRecipeBuilder.stonecutting(
                        Ingredient.of(ZAMBlocks.OPAL_BLOCK.get(), ZAMBlocks.OPAL_BRICKS.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ZAMBlocks.CHISELED_OPAL_BRICKS.get(),
                        1
                ).unlockedBy(getHasName(ZAMBlocks.OPAL_BLOCK.get()), has(ZAMBlocks.OPAL_BLOCK.get()))
                .save(pRecipeOutput, "castingcaving:chiseled_opal_bricks_stonecutting");

        SingleItemRecipeBuilder.stonecutting(
                        Ingredient.of(ZAMBlocks.PERIDOT_BLOCK.get(), ZAMBlocks.PERIDOT_BRICKS.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ZAMBlocks.CHISELED_PERIDOT_BRICKS.get(),
                        1
                ).unlockedBy(getHasName(ZAMBlocks.PERIDOT_BLOCK.get()), has(ZAMBlocks.PERIDOT_BLOCK.get()))
                .save(pRecipeOutput, "castingcaving:chiseled_peridot_bricks_stonecutting");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ZAMItems.AQUAMARINE.get(), 9)
                .requires(ZAMBlocks.AQUAMARINE_BLOCK.get())
                .unlockedBy("has_aquamarine_block", has(ZAMBlocks.AQUAMARINE_BLOCK.get())).save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ZAMItems.OPAL.get(), 9)
                .requires(ZAMBlocks.OPAL_BLOCK.get())
                .unlockedBy("has_opal_block", has(ZAMBlocks.OPAL_BLOCK.get())).save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ZAMItems.PERIDOT.get(), 9)
                .requires(ZAMBlocks.PERIDOT_BLOCK.get())
                .unlockedBy("has_peridot_block", has(ZAMBlocks.PERIDOT_BLOCK.get())).save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ZAMItems.PRISMATIC_SHARD.get(), 9)
                .requires(ZAMBlocks.PRISMATIC_BLOCK.get())
                .unlockedBy("has_prismatic_block", has(ZAMBlocks.PRISMATIC_BLOCK.get())).save(pRecipeOutput);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.AQUAMARINE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.AQUAMARINE_BRICK_STAIRS.get(), 1).unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BRICKS.get()), has(ZAMBlocks.AQUAMARINE_BRICKS.get())).save(pRecipeOutput, "castingcaving:aquamarine_brick_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.OPAL_BRICK_STAIRS.get(), 1).unlockedBy(getHasName(ZAMBlocks.OPAL_BRICKS.get()), has(ZAMBlocks.OPAL_BRICKS.get())).save(pRecipeOutput, "castingcaving:opal_brick_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.PERIDOT_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PERIDOT_BRICK_STAIRS.get(), 1).unlockedBy(getHasName(ZAMBlocks.PERIDOT_BRICKS.get()), has(ZAMBlocks.PERIDOT_BRICKS.get())).save(pRecipeOutput, "castingcaving:peridot_brick_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.AQUAMARINE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.AQUAMARINE_BRICK_SLAB.get(), 2).unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BRICKS.get()), has(ZAMBlocks.AQUAMARINE_BRICKS.get())).save(pRecipeOutput, "castingcaving:aquamarine_brick_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.OPAL_BRICK_SLAB.get(), 2).unlockedBy(getHasName(ZAMBlocks.OPAL_BRICKS.get()), has(ZAMBlocks.OPAL_BRICKS.get())).save(pRecipeOutput, "castingcaving:opal_brick_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.PERIDOT_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PERIDOT_BRICK_SLAB.get(), 2).unlockedBy(getHasName(ZAMBlocks.PERIDOT_BRICKS.get()), has(ZAMBlocks.PERIDOT_BRICKS.get())).save(pRecipeOutput, "castingcaving:peridot_brick_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.AQUAMARINE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.AQUAMARINE_BRICK_WALL.get(), 1).unlockedBy(getHasName(ZAMBlocks.AQUAMARINE_BRICKS.get()), has(ZAMBlocks.AQUAMARINE_BRICKS.get())).save(pRecipeOutput, "castingcaving:aquamarine_brick_wall_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.OPAL_BRICK_WALL.get(), 1).unlockedBy(getHasName(ZAMBlocks.OPAL_BRICKS.get()), has(ZAMBlocks.OPAL_BRICKS.get())).save(pRecipeOutput, "castingcaving:opal_brick_wall_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZAMBlocks.PERIDOT_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ZAMBlocks.PERIDOT_BRICK_WALL.get(), 1).unlockedBy(getHasName(ZAMBlocks.PERIDOT_BRICKS.get()), has(ZAMBlocks.PERIDOT_BRICKS.get())).save(pRecipeOutput, "castingcaving:peridot_brick_wall_stonecutting");
    }
}

