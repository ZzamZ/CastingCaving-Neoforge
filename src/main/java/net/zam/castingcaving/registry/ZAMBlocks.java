package net.zam.castingcaving.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.block.*;
import net.zam.castingcaving.common.block.arcade.ArcadeMachineBlock;
import net.zam.castingcaving.common.block.chest.LostBounty;
import net.zam.castingcaving.common.block.trophy.TrophyBlock;
import net.zam.castingcaving.common.client.block.renderer.BlockItemWithoutLevelRenderer;
import net.zam.castingcaving.common.worldgen.ZAMTreeGrower;
import net.zam.castingcaving.util.ZAMWoodTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ZAMBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CastingCaving.MOD_ID);

    public static final DeferredBlock<Block> SCAFFINITY = registerBlockAndItem("scaffinity", () -> new ScaffinityBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SCAFFOLDING).mapColor(MapColor.COLOR_LIGHT_BLUE).noLootTable()), 1);
    public static final DeferredBlock<Block> LOST_BOUNTY = registerWithRenderer(LostBounty::new, "lost_bounty", new Item.Properties());
    public static final DeferredBlock<Block> MARINE_FARMLAND = registerBlock("aquamarine_farmland", MarineFarmlandBlock::new);

    public static final DeferredBlock<Block> AQUAMARINE_ORE = registerBlock("aquamarine_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
    public static final DeferredBlock<Block> DEEPSLATE_AQUAMARINE_ORE = registerBlock("deepslate_aquamarine_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
    public static final DeferredBlock<Block> OPAL_ORE = registerBlock("opal_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
    public static final DeferredBlock<Block> DEEPSLATE_OPAL_ORE = registerBlock("deepslate_opal_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
    public static final DeferredBlock<Block> PERIDOT_ORE = registerBlock("peridot_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
    public static final DeferredBlock<Block> DEEPSLATE_PERIDOT_ORE = registerBlock("deepslate_peridot_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));

    public static final DeferredBlock<Block> EMERALD_CRYSTAL_BLOCK = registerBlock("emerald_crystal_block", () -> new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.EMERALD).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> BUDDING_EMERALD = registerBlock("budding_emerald", () -> new BuddingEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.EMERALD).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> EMERALD_CLUSTER = registerBlock("emerald_cluster", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.EMERALD).forceSolidOn().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152680_) -> {return 5; }).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> LARGE_EMERALD_BUD = registerBlock("large_emerald_bud", () -> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.of().sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((p_50804_) -> {return 4;}).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> MEDIUM_EMERALD_BUD = registerBlock("medium_emerald_bud", () -> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.of().sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((p_152677_) -> {return 2;}).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SMALL_EMERALD_BUD = registerBlock("small_emerald_bud", () -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.of().sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((p_187433_) -> {return 1;}).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> AQUAMARINE_BLOCK = registerBlock("aquamarine_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).requiresCorrectToolForDrops().strength(8.0F, 6.0F).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> OPAL_BLOCK = registerBlock("opal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).requiresCorrectToolForDrops().strength(8.0F, 6.0F).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> PERIDOT_BLOCK = registerBlock("peridot_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.EMERALD).requiresCorrectToolForDrops().strength(8.0F, 6.0F).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> PRISMATIC_BLOCK = registerBlockWithRarity("prismatic_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).requiresCorrectToolForDrops().strength(8.0F, 6.0F).sound(SoundType.METAL).lightLevel(b -> 15)), Rarity.EPIC);
    public static final DeferredBlock<Block> GLOW_INK_BLOCK = registerBlock("glow_ink_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.3f).lightLevel(b -> 15).sound(SoundType.FROGLIGHT)));
    public static final DeferredBlock<Block> ECHO_FROGLIGHT = registerBlock("echo_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.3f).lightLevel(b -> 15).sound(SoundType.FROGLIGHT)));

    public static final DeferredBlock<Block> REFINED_ANVIL = registerBlock("refined_anvil", () -> new RefinedAnvilBlock(Block.Properties.ofFullCopy(Blocks.ANVIL).sound(SoundType.ANVIL)));
    public static final DeferredBlock<Block> PRISMATIC_FORGE = registerBlock("prismatic_forge", () -> new Block(Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE)));

    public static final DeferredBlock<Block> ARCADE_MACHINE = registerBlock("arcade_machine", () -> new ArcadeMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(0.5F).sound(SoundType.METAL).noOcclusion().lightLevel(b -> 15)));
    public static final DeferredBlock<Block> TROPHY_BLOCK = BLOCKS.register("trophy", TrophyBlock::new);
    public static final DeferredBlock<Block> AQUAMARINE_BRICKS = registerBlock("aquamarine_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> OPAL_BRICKS = registerBlock("opal_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> PERIDOT_BRICKS = registerBlock("peridot_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.EMERALD).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> CHISELED_AQUAMARINE_BRICKS = registerBlock("chiseled_aquamarine_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> CHISELED_OPAL_BRICKS = registerBlock("chiseled_opal_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> AQUAMARINE_BRICK_STAIRS = registerBlock("aquamarine_brick_stairs", () -> new StairBlock(AQUAMARINE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CHISELED_PERIDOT_BRICKS = registerBlock("chiseled_peridot_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.EMERALD).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> OPAL_BRICK_STAIRS = registerBlock("opal_brick_stairs", () -> new StairBlock(OPAL_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> PERIDOT_BRICK_STAIRS = registerBlock("peridot_brick_stairs", () -> new StairBlock(PERIDOT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> AQUAMARINE_BRICK_SLAB = registerBlock("aquamarine_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> OPAL_BRICK_SLAB = registerBlock("opal_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> PERIDOT_BRICK_SLAB = registerBlock("peridot_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> AQUAMARINE_BRICK_WALL = registerBlock("aquamarine_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> OPAL_BRICK_WALL = registerBlock("opal_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> PERIDOT_BRICK_WALL = registerBlock("peridot_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.of()));

    //Marine Wood Type
    public static final DeferredBlock<Block> MARINE_LOG = registerBlock("marine_log", () -> new MarineLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LOG)));
    public static final DeferredBlock<Block> MARINE_WOOD = registerBlock("marine_wood", () -> new MarineLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_WOOD)));
    public static final DeferredBlock<Block> STRIPPED_MARINE_LOG = registerBlock("stripped_marine_log", () -> new MarineLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_CHERRY_LOG)));
    public static final DeferredBlock<Block> STRIPPED_MARINE_WOOD = registerBlock("stripped_marine_wood", () -> new MarineLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_CHERRY_WOOD)));
    public static final DeferredBlock<Block> MARINE_PLANKS = registerBlock("marine_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> MARINE_LEAVES = registerBlock("marine_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES)));
    public static final DeferredBlock<Block> MARINE_BERRY_LEAVES = registerBlock("marine_berry_leaves", () -> new MarineBerryLeavesBlock(Block.Properties.of().strength(0.2F).randomTicks().sound(SoundType.GRASS).lightLevel(b -> 7).noOcclusion(), ZAMItems.MARINE_BERRY));
    public static final DeferredBlock<Block> MARINE_SAPLING = registerBlock("marine_sapling", () -> new ZAMSaplingBlock(ZAMTreeGrower.MARINE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING).lightLevel(b -> 7), Blocks.CHERRY_SAPLING));
    public static final DeferredBlock<Block> POTTED_MARINE_SAPLING = BLOCKS.register("potted_marine_sapling", () -> new FlowerPotBlock((() -> (FlowerPotBlock) Blocks.FLOWER_POT), MARINE_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> MARINE_SIGN = BLOCKS.register("marine_sign", () -> new ZAMStandingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SIGN), ZAMWoodTypes.MARINE));
    public static final DeferredBlock<Block> MARINE_WALL_SIGN = BLOCKS.register("marine_wall_sign", () -> new ZAMWallSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SIGN), ZAMWoodTypes.MARINE));
    public static final DeferredBlock<Block> MARINE_HANGING_SIGN = BLOCKS.register("marine_hanging_sign", () -> new ZAMHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_HANGING_SIGN), ZAMWoodTypes.MARINE));
    public static final DeferredBlock<Block> MARINE_WALL_HANGING_SIGN = BLOCKS.register("marine_wall_hanging_sign", () -> new ZAMWallHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_HANGING_SIGN), ZAMWoodTypes.MARINE));
    public static final DeferredBlock<Block> MARINE_DOOR = registerBlock("marine_door", () -> new DoorBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_DOOR).noOcclusion()));
    public static final DeferredBlock<Block> MARINE_TRAPDOOR = registerBlock("marine_trapdoor", () -> new TrapDoorBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_TRAPDOOR).noOcclusion()));
    public static final DeferredBlock<Block> MARINE_STAIRS = registerBlock("marine_stairs", () -> new StairBlock(ZAMBlocks.MARINE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_STAIRS)));
    public static final DeferredBlock<Block> MARINE_SLAB = registerBlock("marine_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SLAB)));
    public static final DeferredBlock<Block> MARINE_PRESSURE_PLATE = registerBlock("marine_pressure_plate", () -> new PressurePlateBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_STAIRS)));
    public static final DeferredBlock<Block> MARINE_BUTTON = registerBlock("marine_button", () -> new ButtonBlock(BlockSetType.CHERRY, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_BUTTON)));
    public static final DeferredBlock<Block> MARINE_FENCE = registerBlock("marine_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_FENCE)));
    public static final DeferredBlock<Block> MARINE_FENCE_GATE = registerBlock("marine_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_FENCE_GATE), SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final DeferredBlock<Block> MARINE_BERRY_BLOCK = registerBlock("marine_berry_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.3f).lightLevel(b -> 15).sound(SoundType.FROGLIGHT)));



    public static DeferredBlock<Block> registerWithRenderer(Supplier<Block> supplier, @Nonnull String name, @Nullable Item.Properties properties) {
        DeferredBlock<Block> block = BLOCKS.register(name, supplier);

        if (properties == null) {
            ZAMItems.register(() -> new BlockItemWithoutLevelRenderer(block.get(), new Item.Properties()), name);
        } else {
            ZAMItems.register(() -> new BlockItemWithoutLevelRenderer(block.get(), properties), name);
        }
        return block;
    }

    private static DeferredBlock<Block> registerBlockAndItem(String name, Supplier<Block> block, int itemType) {
        DeferredBlock<Block> blockObj = ZAMBlocks.BLOCKS.register(name, block);
        ZAMItems.ITEMS.register(name, getBlockSupplier(itemType, blockObj));
        return blockObj;
    }

    private static Supplier<? extends BlockItem> getBlockSupplier(int itemType, DeferredBlock<Block> blockObj) {
        switch (itemType) {
            default:
                return () -> new BlockItem(blockObj.get(), new Item.Properties());
            case 1:
                return () -> new ScaffinityBlockItem(blockObj.get(), new Item.Properties());
        }
    }

    private static DeferredBlock<Block> registerBlockWithRarity(String name, Supplier<Block> block, Rarity rarity) {
        DeferredBlock<Block> blockRegistryObject = BLOCKS.register(name, block);
        ZAMItems.ITEMS.register(name, () -> new BlockItem(blockRegistryObject.get(), new Item.Properties().rarity(rarity)));
        return blockRegistryObject;
    }


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ZAMItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
