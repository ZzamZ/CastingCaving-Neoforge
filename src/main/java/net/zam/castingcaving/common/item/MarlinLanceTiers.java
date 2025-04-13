package net.zam.castingcaving.common.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.zam.castingcaving.registry.ZAMItems;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.level.block.Block;

public class MarlinLanceTiers {
    private static final TagKey<Block> NO_INCORRECT_BLOCKS = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("castingcaving", "no_incorrect_blocks")
    );

    public static final Tier MARLIN_LANCE = new Tier() {
        @Override
        public int getUses() {
            return 1400;
        }

        @Override
        public float getSpeed() {
            return 6.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 5.0F;
        }

        @Override
        public int getEnchantmentValue() {
            return 9;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(ZAMItems.AQUAMARINE);
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return NO_INCORRECT_BLOCKS;
        }
    };
}