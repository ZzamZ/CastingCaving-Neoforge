package net.zam.castingcaving.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CoralBerryPieItem extends Item {
    public CoralBerryPieItem() {
        super(new Item.Properties()
                .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.3f).alwaysEdible().build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, worldIn, entityLiving);
        entityLiving.setAirSupply(Math.min(entityLiving.getAirSupply() + 100, 300));
        return itemstack;
    }
}