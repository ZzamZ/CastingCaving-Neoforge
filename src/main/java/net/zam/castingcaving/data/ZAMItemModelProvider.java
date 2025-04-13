package net.zam.castingcaving.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMBlocks;
import net.zam.castingcaving.registry.ZAMItems;

public class ZAMItemModelProvider extends ItemModelProvider {
    public ZAMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CastingCaving.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ZAMItems.ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        //   simpleItem(LureItems.KRABBY_PATTY);
        handheldItem(ZAMItems.AQUAMARINE.get());
        basicItem(ZAMItems.PRISMATIC_SHARD.get());
        basicItem(ZAMItems.MARLIN_LANCE.get());
        basicItem(ZAMItems.MARINE_HELMET.get());
        basicItem(ZAMItems.MARINE_CHESTPLATE.get());
        basicItem(ZAMItems.MARINE_LEGGINGS.get());
        basicItem(ZAMItems.MARINE_BOOTS.get());
    //   basicItem(ZAMItems.TIDE.get());
    //   basicItem(ZAMItems.HOWLING.get());
    //   basicItem(ZAMItems.DOG.get());
    //   basicItem(ZAMItems.HALLAND.get());
    //   basicItem(ZAMItems.CREATOR.get());
        basicItem(ZAMItems.OPAL_WAND.get());
        basicItem(ZAMItems.SCULK_BOMB.get());
    //    basicItem(ZAMItems.WOOD_MEDAL.get());
    //    basicItem(ZAMItems.BRONZE_MEDAL.get());
    //    basicItem(ZAMItems.SILVER_MEDAL.get());
    //    basicItem(ZAMItems.GOLD_MEDAL.get());
    //    basicItem(ZAMItems.LEGENDARY_MEDAL.get());
        basicItem(ZAMItems.DOE_COIN.get());

        basicItem(ZAMItems.ICE_PIP.asItem());
    //    basicItem(ZAMItems.GLACIER_FISH.asItem());

        basicItem(ZAMItems.RED_SNAPPER.asItem());
    //    basicItem(ZAMItems.TETRA.asItem());
        basicItem(ZAMItems.CLOWNFISH.asItem());
    //    basicItem(ZAMItems.BLUE_DISCUS.asItem());

        basicItem(ZAMItems.FROG_FISH.asItem());
        basicItem(ZAMItems.SLIME_FISH.asItem());

        basicItem(ZAMItems.SHROOM_FISH.asItem());

        basicItem(ZAMItems.KOI_FISH.asItem());

        basicItem(ZAMItems.NOSTALGIA_FISH.asItem());
        basicItem(ZAMItems.CARROT_FISH.asItem());

        basicItem(ZAMItems.LAVA_EEL.asItem());
        basicItem(ZAMItems.BONE_FISH.asItem());
     //   basicItem(ZAMItems.WITHERING_GUPPY.asItem());

        basicItem(ZAMItems.STURGEON.asItem());
     //   basicItem(ZAMItems.RAINBOW_TROUT.asItem());

        basicItem(ZAMItems.STINGRAY.asItem());
        basicItem(ZAMItems.SARDEINE.asItem());

        basicItem(ZAMItems.SAND_FISH.asItem());

    //    basicItem(ZAMItems.GHOST_FISH.asItem());
        basicItem(ZAMItems.STONEFISH.asItem());

        basicItem(ZAMItems.PRISMATIC_FISH.asItem());
        basicItem(ZAMItems.ECLIPSE_KOI.asItem());
        basicItem(ZAMItems.CRIMSONFISH.asItem());
        basicItem(ZAMItems.GLIMMERING_STARFISH.asItem());
        basicItem(ZAMItems.THE_LEGEND.asItem());
        basicItem(ZAMItems.MOONLIGHT_JELLYFISH.asItem());
        basicItem(ZAMItems.SPECTRAL_ANGLER_FISH.asItem());

        basicItem(ZAMItems.OPAL.get());
        basicItem(ZAMItems.PERIDOT.get());
        basicItem(ZAMItems.EMERALD_SHARD.get());
        basicItem(ZAMItems.TOTEM_OF_RETURNING.get());
        handheldItem(ZAMItems.MARINE_HOE.get());
        handheldItem(ZAMItems.NEPTUNES_GLAIVE.get());

        wallItem(ZAMBlocks.AQUAMARINE_BRICK_WALL, ZAMBlocks.AQUAMARINE_BRICKS);
        wallItem(ZAMBlocks.OPAL_BRICK_WALL, ZAMBlocks.OPAL_BRICKS);
        wallItem(ZAMBlocks.PERIDOT_BRICK_WALL, ZAMBlocks.PERIDOT_BRICKS);

        saplingItem(ZAMBlocks.MARINE_SAPLING);

        basicItem(ZAMItems.MARINE_SIGN.get());
        basicItem(ZAMItems.MARINE_HANGING_SIGN.get());
        basicItem(ZAMItems.MARINE_BOAT.get());
        basicItem(ZAMItems.MARINE_CHEST_BOAT.get());

        handheldItem(ZAMItems.PERIDOT_HAMMER.get());
        basicItem(ZAMItems.GEODE.get());
        basicItem(ZAMItems.LAVA_GEODE.get());
        basicItem(ZAMItems.MOSSY_GEODE.get());
        basicItem(ZAMItems.OCEAN_GEODE.get());

        basicItem(ZAMBlocks.MARINE_DOOR.asItem());
        buttonItem(ZAMBlocks.MARINE_BUTTON, ZAMBlocks.MARINE_PLANKS);
        fenceItem(ZAMBlocks.MARINE_FENCE, ZAMBlocks.MARINE_PLANKS);
        basicItem(ZAMItems.MARINE_BERRY.get());
        basicItem(ZAMItems.MARINE_BERRY_PIE.get());

        basicItem(ZAMItems.LEECH.get());
        basicItem(ZAMItems.WORM.get());
        basicItem(ZAMItems.MINNOW.get());
        basicItem(ZAMItems.LUCK_HOOK.get());
        basicItem(ZAMItems.DOUBLE_HOOK.get());
        basicItem(ZAMItems.LURE_HOOK.get());
        basicItem(ZAMItems.MELODY_HOOK.get());
        basicItem(ZAMItems.TREASURE_HOOK.get());

        withExistingParent(ZAMItems.OTTER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID,"block/" + item.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void buttonItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }
}