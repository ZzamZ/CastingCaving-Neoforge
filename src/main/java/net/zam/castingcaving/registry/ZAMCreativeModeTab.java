package net.zam.castingcaving.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zam.castingcaving.CastingCaving;

import java.util.function.Supplier;

public class ZAMCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            CastingCaving.MOD_ID);

    public static Supplier<CreativeModeTab> AQUATIC_TAB = CREATIVE_MODE_TABS.register("lure_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ZAMItems.MARINE_HELMET.get())).title(Component.literal("ZAM's Casting & Caving")).displayItems((displayParameters, output) -> {
                //  output.accept(ZAMItems.KRABBY_PATTY.get());
                output.accept(ZAMItems.MARLIN_LANCE.get());
                output.accept(ZAMItems.OPAL_WAND.get());
                output.accept(ZAMItems.MARINE_HOE.get());
                output.accept(ZAMItems.NEPTUNES_GLAIVE.get());
                output.accept(ZAMItems.IRON_FISHING_ROD.get());
                output.accept(ZAMItems.GOLD_FISHING_ROD.get());
                output.accept(ZAMItems.DIAMOND_FISHING_ROD.get());
                output.accept(ZAMItems.NETHERITE_FISHING_ROD.get());
                output.accept(ZAMItems.MARINE_FISHING_ROD.get());
                output.accept(ZAMItems.MARINE_HELMET.get());
                output.accept(ZAMItems.MARINE_CHESTPLATE.get());
                output.accept(ZAMItems.MARINE_LEGGINGS.get());
                output.accept(ZAMItems.MARINE_BOOTS.get());
                //    output.accept(ZAMItems.TIDE.get());
                //    output.accept(ZAMItems.DOG.get());
                //    output.accept(ZAMItems.HALLAND.get());
                //    output.accept(ZAMItems.HOWLING.get());
                //    output.accept(ZAMItems.CREATOR.get());
                //    output.accept(ZAMItems.ATLANTIS.get());
                output.accept(ZAMItems.SCULK_BOMB.get());
                output.accept(ZAMItems.ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ZAMItems.AQUAMARINE.get());
                output.accept(ZAMItems.OPAL.get());
                output.accept(ZAMItems.PERIDOT.get());
                output.accept(ZAMItems.PRISMATIC_SHARD.get());

                output.accept(ZAMBlocks.MARINE_LOG.get());
                output.accept(ZAMBlocks.MARINE_WOOD.get());
                output.accept(ZAMBlocks.STRIPPED_MARINE_LOG.get());
                output.accept(ZAMBlocks.STRIPPED_MARINE_WOOD.get());
                output.accept(ZAMBlocks.MARINE_PLANKS.get());
                output.accept(ZAMBlocks.MARINE_LEAVES.get());
                output.accept(ZAMBlocks.MARINE_BERRY_LEAVES.get());
                output.accept(ZAMBlocks.MARINE_SAPLING.get());
                output.accept(ZAMItems.MARINE_SIGN.get());
                output.accept(ZAMItems.MARINE_HANGING_SIGN.get());
                output.accept(ZAMItems.MARINE_BOAT.get());
                output.accept(ZAMItems.MARINE_CHEST_BOAT.get());
                output.accept(ZAMBlocks.MARINE_DOOR.get());
                output.accept(ZAMBlocks.MARINE_TRAPDOOR.get());
                output.accept(ZAMBlocks.MARINE_STAIRS.get());
                output.accept(ZAMBlocks.MARINE_SLAB.get());
                output.accept(ZAMBlocks.MARINE_BUTTON.get());
                output.accept(ZAMBlocks.MARINE_PRESSURE_PLATE.get());
                output.accept(ZAMBlocks.MARINE_FENCE.get());
                output.accept(ZAMBlocks.MARINE_FENCE_GATE.get());
                output.accept(ZAMBlocks.MARINE_BERRY_BLOCK.get());
                output.accept(ZAMItems.MARINE_BERRY.get());
                output.accept(ZAMItems.MARINE_BERRY_PIE.get());

                output.accept(ZAMBlocks.ARCADE_MACHINE.get());
                output.accept(ZAMBlocks.TROPHY_BLOCK.get());
                output.accept(ZAMBlocks.REFINED_ANVIL.get());
                output.accept(ZAMBlocks.PRISMATIC_FORGE.get());
                output.accept(ZAMItems.PERIDOT_HAMMER.get());
                output.accept(ZAMItems.GEODE.get());
                output.accept(ZAMItems.LAVA_GEODE.get());
                output.accept(ZAMItems.MOSSY_GEODE.get());
                output.accept(ZAMItems.OCEAN_GEODE.get());
                output.accept(ZAMItems.ECLIPSE_GEODE.get());

                output.accept(ZAMBlocks.LOST_BOUNTY.get());
                output.accept(ZAMBlocks.SCAFFINITY.get());
                output.accept(ZAMBlocks.AQUAMARINE_ORE.get());
                output.accept(ZAMBlocks.DEEPSLATE_AQUAMARINE_ORE.get());
                output.accept(ZAMBlocks.AQUAMARINE_BLOCK.get());
                output.accept(ZAMBlocks.OPAL_ORE.get());
                output.accept(ZAMBlocks.DEEPSLATE_OPAL_ORE.get());
                output.accept(ZAMBlocks.OPAL_BLOCK.get());
                output.accept(ZAMBlocks.PERIDOT_ORE.get());
                output.accept(ZAMBlocks.DEEPSLATE_PERIDOT_ORE.get());
                output.accept(ZAMBlocks.PERIDOT_BLOCK.get());
                output.accept(ZAMBlocks.PRISMATIC_BLOCK.get());
                output.accept(ZAMBlocks.GLOW_INK_BLOCK.get());
                output.accept(ZAMBlocks.ECHO_FROGLIGHT.get());
                output.accept(ZAMBlocks.EMERALD_CRYSTAL_BLOCK.get());
                output.accept(ZAMBlocks.BUDDING_EMERALD.get());
                output.accept(ZAMBlocks.SMALL_EMERALD_BUD.get());
                output.accept(ZAMBlocks.MEDIUM_EMERALD_BUD.get());
                output.accept(ZAMBlocks.LARGE_EMERALD_BUD.get());
                output.accept(ZAMBlocks.EMERALD_CLUSTER.get());
                output.accept(ZAMItems.EMERALD_SHARD.get());
                output.accept(ZAMItems.TOTEM_OF_RETURNING.get());
              //  output.accept(ZAMItems.FISHERMAN_HAT.get());
              //  output.accept(ZAMItems.PIRATE_HAT.get());
              //  output.accept(ZAMItems.FEAR_ME_CAP.get());

              //  output.accept(ZAMItems.WOOD_MEDAL.get());
              //  output.accept(ZAMItems.BRONZE_MEDAL.get());
              //  output.accept(ZAMItems.SILVER_MEDAL.get());
              //  output.accept(ZAMItems.GOLD_MEDAL.get());
              //  output.accept(ZAMItems.LEGENDARY_MEDAL.get());

                output.accept(ZAMItems.LEECH.get());
                output.accept(ZAMItems.WORM.get());
                output.accept(ZAMItems.MINNOW.get());
                output.accept(ZAMItems.LURE_HOOK.get());
                output.accept(ZAMItems.LUCK_HOOK.get());
                output.accept(ZAMItems.DOUBLE_HOOK.get());
                output.accept(ZAMItems.TREASURE_HOOK.get());
                output.accept(ZAMItems.MELODY_HOOK.get());


                output.accept(ZAMItems.ICE_PIP.get());
                output.accept(ZAMItems.GLACIER_FISH.get());

                output.accept(ZAMItems.RED_SNAPPER.get());
                output.accept(ZAMItems.TETRA.get());
                output.accept(ZAMItems.CLOWNFISH.get());
                output.accept(ZAMItems.BLUE_DISCUS.get());

                output.accept(ZAMItems.FROG_FISH.get());
                output.accept(ZAMItems.SLIME_FISH.get());

                output.accept(ZAMItems.SHROOM_FISH.get());

                output.accept(ZAMItems.KOI_FISH.get());

                output.accept(ZAMItems.NOSTALGIA_FISH.get());
                output.accept(ZAMItems.CARROT_FISH.get());

                output.accept(ZAMItems.LAVA_EEL.get());
                output.accept(ZAMItems.BONE_FISH.get());
                output.accept(ZAMItems.WITHERING_GUPPY.get());

                output.accept(ZAMItems.STURGEON.get());
                output.accept(ZAMItems.RAINBOW_TROUT.get());

                output.accept(ZAMItems.STINGRAY.get());
                output.accept(ZAMItems.SARDEINE.get());

                output.accept(ZAMItems.SAND_FISH.get());

                output.accept(ZAMItems.GHOST_FISH.get());
                output.accept(ZAMItems.STONEFISH.get());

                output.accept(ZAMItems.PRISMATIC_FISH.get());
                output.accept(ZAMItems.ECLIPSE_KOI.get());
                output.accept(ZAMItems.CRIMSONFISH.get());
                output.accept(ZAMItems.GLIMMERING_STARFISH.get());
                output.accept(ZAMItems.THE_LEGEND.get());
                output.accept(ZAMItems.MOONLIGHT_JELLYFISH.get());
                output.accept(ZAMItems.SPECTRAL_ANGLER_FISH.get());

                output.accept(ZAMItems.OTTER_SPAWN_EGG.get());


            }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
