package net.zam.castingcaving.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CoralBerryItem extends Item {
    public CoralBerryItem() {
        super(new Item.Properties()
                .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.0f).alwaysEdible().build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, worldIn, entityLiving);
        entityLiving.setAirSupply(Math.min(entityLiving.getAirSupply() + 100, 150)); // Increases air supply, caps at 150
        return itemstack;
    }
}