package net.zam.castingcaving.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.zam.castingcaving.CastingCaving;

import java.util.Locale;

public class ZAMTags {
    public static final TagKey<Biome> HAS_AQUAMARINE_ORE = biomeTag("has_aquamarine_ore");
    public static final TagKey<Biome> HAS_EMERALD_GEODE = biomeTag("has_emerald_geode");
    public static final TagKey<Biome> SPAWNS_ANCIENT_VARIANT_FROGS = biomeTag("spawns_ancient_variant_frogs");
    public static final TagKey<Item> MARINE_LOGS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "marine_logs"));

    private static TagKey<Biome> biomeTag(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, name.toLowerCase(Locale.ROOT)));
    }
}
