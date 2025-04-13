package net.zam.castingcaving.common.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class LegendaryFishItem extends Item {
    public static final FoodProperties LEGENDARY_FISH_RAW = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.5F).build();

    public LegendaryFishItem() {
        this(LEGENDARY_FISH_RAW);
    }

    public LegendaryFishItem(FoodProperties foodProperties) {
        super(new Item.Properties().food(foodProperties).rarity(Rarity.EPIC));
    }
}
