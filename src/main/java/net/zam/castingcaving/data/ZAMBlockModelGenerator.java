package net.zam.castingcaving.data;

import com.google.gson.JsonElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMBlocks;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ZAMBlockModelGenerator extends BlockStateProvider {
    Consumer<BlockStateGenerator> blockStateOutput;
    BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

    public ZAMBlockModelGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CastingCaving.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ZAMBlocks.AQUAMARINE_ORE);
        blockWithItem(ZAMBlocks.DEEPSLATE_AQUAMARINE_ORE);
        blockWithItem(ZAMBlocks.AQUAMARINE_BLOCK);
        blockWithItem(ZAMBlocks.OPAL_ORE);
        blockWithItem(ZAMBlocks.DEEPSLATE_OPAL_ORE);
        blockWithItem(ZAMBlocks.OPAL_BLOCK);
        blockWithItem(ZAMBlocks.PERIDOT_ORE);
        blockWithItem(ZAMBlocks.DEEPSLATE_PERIDOT_ORE);
        blockWithItem(ZAMBlocks.PERIDOT_BLOCK);
        blockWithItem(ZAMBlocks.PRISMATIC_BLOCK);
        blockWithItem(ZAMBlocks.GLOW_INK_BLOCK);
        blockWithItem(ZAMBlocks.BUDDING_EMERALD);
        blockWithItem(ZAMBlocks.EMERALD_CRYSTAL_BLOCK);
        blockWithItem(ZAMBlocks.AQUAMARINE_BRICKS);
        blockWithItem(ZAMBlocks.OPAL_BRICKS);
        blockWithItem(ZAMBlocks.PERIDOT_BRICKS);
        blockWithItem(ZAMBlocks.CHISELED_AQUAMARINE_BRICKS);
        blockWithItem(ZAMBlocks.CHISELED_OPAL_BRICKS);
        blockWithItem(ZAMBlocks.CHISELED_PERIDOT_BRICKS);
        modStairsBlock(ZAMBlocks.AQUAMARINE_BRICK_STAIRS, ZAMBlocks.AQUAMARINE_BRICKS);
        modStairsBlock(ZAMBlocks.OPAL_BRICK_STAIRS, ZAMBlocks.OPAL_BRICKS);
        modStairsBlock(ZAMBlocks.PERIDOT_BRICK_STAIRS, ZAMBlocks.PERIDOT_BRICKS);
        modSlabBlock(ZAMBlocks.AQUAMARINE_BRICK_SLAB, ZAMBlocks.AQUAMARINE_BRICKS);
        modSlabBlock(ZAMBlocks.OPAL_BRICK_SLAB, ZAMBlocks.OPAL_BRICKS);
        modSlabBlock(ZAMBlocks.PERIDOT_BRICK_SLAB, ZAMBlocks.PERIDOT_BRICKS);
        wallBlock((WallBlock) ZAMBlocks.AQUAMARINE_BRICK_WALL.get(), blockTexture(ZAMBlocks.AQUAMARINE_BRICKS.get()));
        wallBlock((WallBlock) ZAMBlocks.OPAL_BRICK_WALL.get(), blockTexture(ZAMBlocks.OPAL_BRICKS.get()));
        wallBlock((WallBlock) ZAMBlocks.PERIDOT_BRICK_WALL.get(), blockTexture(ZAMBlocks.PERIDOT_BRICKS.get()));
        logBlock((RotatedPillarBlock) ZAMBlocks.MARINE_LOG.get());
        axisBlock((RotatedPillarBlock) ZAMBlocks.MARINE_WOOD.get(), blockTexture(ZAMBlocks.MARINE_LOG.get()), blockTexture(ZAMBlocks.MARINE_LOG.get()));
        axisBlock((RotatedPillarBlock) ZAMBlocks.STRIPPED_MARINE_LOG.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "block/stripped_marine_log"), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "block/stripped_marine_log_top"));
        axisBlock((RotatedPillarBlock) ZAMBlocks.STRIPPED_MARINE_WOOD.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "block/stripped_marine_log"), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "block/stripped_marine_log"));
        blockItem(ZAMBlocks.MARINE_LOG);
        blockItem(ZAMBlocks.MARINE_WOOD);
        blockItem(ZAMBlocks.STRIPPED_MARINE_LOG);
        blockItem(ZAMBlocks.STRIPPED_MARINE_WOOD);
        blockWithItem(ZAMBlocks.MARINE_PLANKS);
        leavesBlock(ZAMBlocks.MARINE_LEAVES);
        leavesBlock(ZAMBlocks.MARINE_BERRY_LEAVES);
        saplingBlock(ZAMBlocks.MARINE_SAPLING);


        blockItem(ZAMBlocks.AQUAMARINE_BRICK_STAIRS);
        blockItem(ZAMBlocks.OPAL_BRICK_STAIRS);
        blockItem(ZAMBlocks.PERIDOT_BRICK_STAIRS);

        blockItem(ZAMBlocks.AQUAMARINE_BRICK_SLAB);
        blockItem(ZAMBlocks.OPAL_BRICK_SLAB);
        blockItem(ZAMBlocks.PERIDOT_BRICK_SLAB);

        signBlock(((StandingSignBlock) ZAMBlocks.MARINE_SIGN.get()), ((WallSignBlock) ZAMBlocks.MARINE_WALL_SIGN.get()),
                blockTexture(ZAMBlocks.MARINE_PLANKS.get()));

        hangingSignBlock(ZAMBlocks.MARINE_HANGING_SIGN.get(), ZAMBlocks.MARINE_WALL_HANGING_SIGN.get(),
                blockTexture(ZAMBlocks.STRIPPED_MARINE_LOG.get()));

        doorBlockWithRenderType((DoorBlock)ZAMBlocks.MARINE_DOOR.get(), modLoc("block/marine_door_bottom"), modLoc("block/marine_door_top"), "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock)ZAMBlocks.MARINE_TRAPDOOR.get(), modLoc("block/marine_trapdoor"), true, "cutout");

        blockItem(ZAMBlocks.MARINE_TRAPDOOR, "_bottom");

        stairsBlock((StairBlock) ZAMBlocks.MARINE_STAIRS.get(), blockTexture(ZAMBlocks.MARINE_PLANKS.get()));
        slabBlock(((SlabBlock) ZAMBlocks.MARINE_SLAB.get()), blockTexture(ZAMBlocks.MARINE_PLANKS.get()), blockTexture(ZAMBlocks.MARINE_PLANKS.get()));

        buttonBlock((ButtonBlock) ZAMBlocks.MARINE_BUTTON.get(), blockTexture(ZAMBlocks.MARINE_PLANKS.get()));
        pressurePlateBlock((PressurePlateBlock) ZAMBlocks.MARINE_PRESSURE_PLATE.get(), blockTexture(ZAMBlocks.MARINE_PLANKS.get()));


        fenceBlock((FenceBlock) ZAMBlocks.MARINE_FENCE.get(), blockTexture(ZAMBlocks.MARINE_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ZAMBlocks.MARINE_FENCE_GATE.get(), blockTexture(ZAMBlocks.MARINE_PLANKS.get()));
        blockItem(ZAMBlocks.MARINE_STAIRS);
        blockItem(ZAMBlocks.MARINE_SLAB);
        blockItem(ZAMBlocks.MARINE_PRESSURE_PLATE);
        blockItem(ZAMBlocks.MARINE_FENCE_GATE);

    }

    private void blockItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("castingcaving:block/" + deferredBlock.getId().getPath()));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }


    private void saplingBlock(DeferredBlock<Block> deferredBlock) {
        simpleBlock(deferredBlock.get(), models().cross(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(), blockTexture(deferredBlock.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().cubeAll(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(DeferredBlock<Block> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("castingcaving:block/" + deferredBlock.getId().getPath() + appendix));
    }

    private void blockWithItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    public static String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private void modSlabBlock(DeferredBlock<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        slabBlock((SlabBlock) block.get(), blockTexture(blockForTexture.get()), blockTexture(blockForTexture.get()));
    }

    private void modStairsBlock(DeferredBlock<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        stairsBlock((StairBlock) block.get(), blockTexture(blockForTexture.get()));
    }
}
