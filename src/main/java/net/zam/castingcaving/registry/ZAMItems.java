package net.zam.castingcaving.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.block.trophy.TrophyBlockItem;
import net.zam.castingcaving.common.entity.ZAMBoatEntity;
import net.zam.castingcaving.common.item.*;
import net.zam.castingcaving.common.item.fishing.*;
import net.zam.castingcaving.common.item.sculkbomb.SculkBomb;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ZAMItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CastingCaving.MOD_ID);

    //Otter
    public static final DeferredItem<Item> OTTER_SPAWN_EGG = ITEMS.register("otter_spawn_egg", () -> new DeferredSpawnEggItem(ZAMEntities.OTTER, 0x352C34, 0xB49494, new Item.Properties()));

    //Gems & Valuables
    public static final DeferredItem<Item> AQUAMARINE = ITEMS.register("aquamarine", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OPAL = ITEMS.register("opal", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIDOT = ITEMS.register("peridot", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PRISMATIC_SHARD = ITEMS.register("prismatic_shard", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> EMERALD_SHARD = ITEMS.register("emerald_shard", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 200), 1.0F).alwaysEdible().build())));
    public static final DeferredItem<Item> DOE_COIN = ITEMS.register("doe_coin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    //Geode & Hammer
    public static final DeferredItem<Item> GEODE = ITEMS.register("geode", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LAVA_GEODE = ITEMS.register("lava_geode", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MOSSY_GEODE = ITEMS.register("mossy_geode", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OCEAN_GEODE = ITEMS.register("ocean_geode", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ECLIPSE_GEODE = ITEMS.register("eclipse_geode", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIDOT_HAMMER = ITEMS.register("peridot_hammer", () -> new PeridotHammerItem(new Item.Properties().durability(250)));

    //Armor
    public static final DeferredItem<Item> MARINE_HELMET = ITEMS.register("marine_helmet", () -> new MarineArmorItem(ZAMArmorMaterial.MARINE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<Item> MARINE_CHESTPLATE = ITEMS.register("marine_chestplate", () -> new MarineArmorItem(ZAMArmorMaterial.MARINE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<Item> MARINE_LEGGINGS = ITEMS.register("marine_leggings", () -> new MarineArmorItem(ZAMArmorMaterial.MARINE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<Item> MARINE_BOOTS = ITEMS.register("marine_boots", () -> new MarineArmorItem(ZAMArmorMaterial.MARINE, ArmorItem.Type.BOOTS, new Item.Properties()));

    //Fishing Rods
    public static final DeferredItem<Item> IRON_FISHING_ROD = ITEMS.register("iron_fishing_rod", IronFishingRodItem::new);
    public static final DeferredItem<Item> GOLD_FISHING_ROD = ITEMS.register("gold_fishing_rod", GoldFishingRodItem::new);
    public static final DeferredItem<Item> DIAMOND_FISHING_ROD = ITEMS.register("diamond_fishing_rod", DiamondFishingRodItem::new);
    public static final DeferredItem<Item> NETHERITE_FISHING_ROD = ITEMS.register("netherite_fishing_rod", NetheriteFishingRodItem::new);
    public static final DeferredItem<Item> MARINE_FISHING_ROD = ITEMS.register("marine_fishing_rod", MarineFishingRodItem::new);

    //Tools & Weapons
    public static final DeferredItem<Item> MARINE_HOE = ITEMS.register("marine_hoe", () -> new MarineHoe(Tiers.DIAMOND, -3, 0.0F));
    public static final DeferredItem<Item> NEPTUNES_GLAIVE = ITEMS.register("neptunes_glaive", () -> new NeptunesGlaiveItem(Tiers.DIAMOND, new Item.Properties().durability(1734).rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> MARLIN_LANCE = ITEMS.register("marlin_lance", MarlinLanceItem::new);

    //Artifacts
    public static final DeferredItem<Item> SHINY_CHARM = ITEMS.register("shiny_charm", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> MONARCH_WINGS = ITEMS.register("monarch_wings", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> MANTIS_CLAW = ITEMS.register("mantis_claw", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> CRYSTAL_HEART = ITEMS.register("crystal_heart", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> MYSTERIOUS_JOURNAL = ITEMS.register("mysterious_journal", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> OLD_MEMORY_WIPE_GUN = ITEMS.register("old_memory_wipe_gun", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> UNSTABLE_RIFT = ITEMS.register("unstable_rift", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> HELLS_RETRIEVER = ITEMS.register("hells_retriever", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> GOLD_RING = ITEMS.register("gold_ring", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> ZAMS_ICE_SKATE = ITEMS.register("zams_ice_skate", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> ENDER_EYES = ITEMS.register("ender_eyes", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> WARDEN_HEART = ITEMS.register("warden_heart", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> PIGLIN_HORN = ITEMS.register("piglin_horn", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> SHIMMER = ITEMS.register("shimmer", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> SECRET_FORMULA = ITEMS.register("secret_formula", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> CHARISMATIC_CROWN = ITEMS.register("charismatic_crown", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> HORN = ITEMS.register("horn", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> BASIC_BADGE = ITEMS.register("basic_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> TRIO_BADGE = ITEMS.register("trio_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> INSECT_BADGE = ITEMS.register("insect_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> BOLT_BADGE = ITEMS.register("bolt_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> QUAKE_BADGE = ITEMS.register("q_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> JET_BADGE = ITEMS.register("jet_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> FREEZE_BADGE = ITEMS.register("freeze_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> LEGEND_BADGE = ITEMS.register("legend_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> WAVE_BADGE = ITEMS.register("wave_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> TOXIC_BADGE = ITEMS.register("toxic_badge", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> TROPHY = ITEMS.register("trophy", () -> new TrophyBlockItem(ZAMBlocks.TROPHY_BLOCK.get(), new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));

    // Bait items
    public static final DeferredItem<LeechItem> LEECH = ITEMS.register("leech", LeechItem::new);
    public static final DeferredItem<WormItem> WORM = ITEMS.register("worm", WormItem::new);
    public static final DeferredItem<MinnowItem> MINNOW = ITEMS.register("minnow", MinnowItem::new);

    //Lures
    public static final DeferredItem<LureItem> LUCK_HOOK = ITEMS.register("luck_hook", () -> new LureItem(new Item.Properties().stacksTo(1).durability(25)));
    public static final DeferredItem<LureItem> DOUBLE_HOOK = ITEMS.register("double_hook", () -> new LureItem(new Item.Properties().stacksTo(1).durability(25)));
    public static final DeferredItem<LureItem> LURE_HOOK = ITEMS.register("lure_hook", () -> new LureItem(new Item.Properties().stacksTo(1).durability(25)));
    public static final DeferredItem<LureItem> MELODY_HOOK = ITEMS.register("melody_hook", () -> new LureItem(new Item.Properties().stacksTo(1).durability(25)));
    public static final DeferredItem<LureItem> TREASURE_HOOK = ITEMS.register("treasure_hook", () -> new LureItem(new Item.Properties().stacksTo(1).durability(25)));

    //Ice/Snow Fish
    public static final DeferredItem<Item> ICE_PIP = ITEMS.register("ice_pip", () -> new FishItem(FishItem.SMALL_FISH_RAW));
    public static final DeferredItem<Item> GLACIER_FISH = ITEMS.register("glacier_fish", () -> new FishItem(FishItem.LARGE_FISH_RAW));

    //Warm Fish
    public static final DeferredItem<Item> RED_SNAPPER = ITEMS.register("red_snapper", () -> new FishItem(FishItem.SMALL_FISH_RAW));
    public static final DeferredItem<Item> TETRA = ITEMS.register("tetra", () -> new FishItem(FishItem.SMALL_FISH_RAW));
    public static final DeferredItem<Item> CLOWNFISH = ITEMS.register("clownfish", () -> new FishItem(FishItem.SMALL_FISH_RAW));
    public static final DeferredItem<Item> BLUE_DISCUS = ITEMS.register("blue_discus", () -> new FishItem(FishItem.SMALL_FISH_RAW));

    //Swamp Fish
    public static final DeferredItem<Item> FROG_FISH = ITEMS.register("frog_fish", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));
    public static final DeferredItem<Item> SLIME_FISH = ITEMS.register("slime_fish", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));

    //Mushroom Fish
    public static final DeferredItem<Item> SHROOM_FISH = ITEMS.register("shroom_fish", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));

    //Cherry Grove Fish
    public static final DeferredItem<Item> KOI_FISH = ITEMS.register("koi_fish", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));

    //Misc Fish
    public static final DeferredItem<Item> NOSTALGIA_FISH = ITEMS.register("nostalgia_fish", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));
    public static final DeferredItem<Item> CARROT_FISH = ITEMS.register("carrot_fish", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));

    //Lava/Nether Fish
    public static final DeferredItem<Item> LAVA_EEL = ITEMS.register("lava_eel", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));
    public static final DeferredItem<Item> BONE_FISH = ITEMS.register("bone_fish", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));
    public static final DeferredItem<Item> WITHERING_GUPPY = ITEMS.register("withering_guppy", () -> new FishItem(FishItem.LARGE_FISH_RAW));

    //Lake/River Fish
    public static final DeferredItem<Item> STURGEON = ITEMS.register("sturgeon", () -> new FishItem(FishItem.LARGE_FISH_RAW));
    public static final DeferredItem<Item> RAINBOW_TROUT = ITEMS.register("rainbow_trouth", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));

    //Ocean
    public static final DeferredItem<Item> STINGRAY = ITEMS.register("stingray", () -> new FishItem(FishItem.LARGE_FISH_RAW));
    public static final DeferredItem<Item> SARDEINE = ITEMS.register("sardeine", () -> new FishItem(FishItem.SMALL_FISH_RAW));

    //Desert Fish
    public static final DeferredItem<Item> SAND_FISH = ITEMS.register("sand_fish", () -> new FishItem(FishItem.SMALL_FISH_RAW));

    //Mines Fish
    public static final DeferredItem<Item> GHOST_FISH = ITEMS.register("ghost_fish", () -> new FishItem(FishItem.MEDIUM_FISH_RAW));
    public static final DeferredItem<Item> STONEFISH = ITEMS.register("stonefish", () -> new FishItem(FishItem.LARGE_FISH_RAW));

    //Legendary Fish
    public static final DeferredItem<Item> PRISMATIC_FISH = ITEMS.register("prismatic_fish", () -> new LegendaryFishItem(LegendaryFishItem.LEGENDARY_FISH_RAW));
    public static final DeferredItem<Item> ECLIPSE_KOI = ITEMS.register("eclipse_koi", () -> new LegendaryFishItem(LegendaryFishItem.LEGENDARY_FISH_RAW));
    public static final DeferredItem<Item> CRIMSONFISH = ITEMS.register("crimsonfish", () -> new LegendaryFishItem(LegendaryFishItem.LEGENDARY_FISH_RAW));
    public static final DeferredItem<Item> GLIMMERING_STARFISH = ITEMS.register("glimmering_starfish", () -> new LegendaryFishItem(LegendaryFishItem.LEGENDARY_FISH_RAW));
    public static final DeferredItem<Item> THE_LEGEND = ITEMS.register("the_legend", () -> new LegendaryFishItem(LegendaryFishItem.LEGENDARY_FISH_RAW));
    public static final DeferredItem<Item> MOONLIGHT_JELLYFISH = ITEMS.register("moonlight_jellyfish", () -> new LegendaryFishItem(LegendaryFishItem.LEGENDARY_FISH_RAW));
    public static final DeferredItem<Item> SPECTRAL_ANGLER_FISH = ITEMS.register("spectral_angler_fish", () -> new LegendaryFishItem(LegendaryFishItem.LEGENDARY_FISH_RAW));

    //Trim
    public static final DeferredItem<Item> ANGLER_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("angler_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ZAMTrimPatterns.ANGLER));

    //Misc
    public static final DeferredItem<Item> TOTEM_OF_RETURNING = ITEMS.register("totem_of_returning", () -> new TotemOfReturning(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> OPAL_WAND = ITEMS.register("opal_wand", () -> new OpalWandItem(new Item.Properties().durability(100).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SCULK_BOMB = ITEMS.register("sculk_bomb", () -> new SculkBomb(new Item.Properties().rarity(Rarity.RARE).stacksTo(16)));
    public static final DeferredItem<Item> MARINE_SIGN = ITEMS.register("marine_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ZAMBlocks.MARINE_SIGN.get(), ZAMBlocks.MARINE_WALL_SIGN.get()));
    public static final DeferredItem<Item> MARINE_HANGING_SIGN = ITEMS.register("marine_hanging_sign", () -> new HangingSignItem(ZAMBlocks.MARINE_HANGING_SIGN.get(), ZAMBlocks.MARINE_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> MARINE_BOAT = ITEMS.register("marine_boat", () -> new ZAMBoatItem(false, ZAMBoatEntity.Type.MARINE, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MARINE_CHEST_BOAT = ITEMS.register("marine_chest_boat", () -> new ZAMBoatItem(true, ZAMBoatEntity.Type.MARINE, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MARINE_BERRY = ITEMS.register("marine_berry", CoralBerryItem::new);
    public static final DeferredItem<Item> MARINE_BERRY_PIE = ITEMS.register("marine_berry_pie", CoralBerryPieItem::new);



    public static void register(@Nonnull Supplier<Item> initializer, @Nonnull String name) {
        ITEMS.register(name, initializer);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
