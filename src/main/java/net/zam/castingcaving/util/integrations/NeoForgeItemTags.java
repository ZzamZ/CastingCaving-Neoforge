package net.zam.castingcaving.util.integrations;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NeoForgeItemTags {
    public static final TagKey<Item> AQUAMARINE = bind("gems/aquamarine");
    public static final TagKey<Item> OPAL = bind("gems/opal");
    public static final TagKey<Item> PERIDOT = bind("gems/peridot");

    private static TagKey<Item> bind(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", path));
    }
}
