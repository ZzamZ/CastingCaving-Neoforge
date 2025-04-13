package net.zam.castingcaving.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MarlinLanceItem extends SwordItem {
    public MarlinLanceItem() {
        super(MarlinLanceTiers.MARLIN_LANCE, new Properties()
                .fireResistant()
                .rarity(Rarity.RARE)
                .food(new FoodProperties.Builder()
                        .nutrition(5)
                        .saturationModifier(0.6f)
                        .build()));
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(@NotNull ItemStack stack) {
        ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers(stack);

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }

        builder.add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("castingcaving", "marlin_lance_damage"),
                        7.5,
                        AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.HAND
        );
        builder.add(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("castingcaving", "marlin_lance_speed"),
                        -3.0,
                        AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.HAND
        );
        builder.add(
                Attributes.ENTITY_INTERACTION_RANGE,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("castingcaving", "marlin_lance_reach"),
                        1.0,
                        AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.HAND
        );

        return builder.build();
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
        if (entity instanceof Player player) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(),
                    player.getEatingSound(itemstack), SoundSource.NEUTRAL,
                    1.0F, 1.0F + (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.4F);
            FoodProperties foodProperties = itemstack.getFoodProperties(entity);
            if (foodProperties != null) {
                player.getFoodData().eat(foodProperties.nutrition(), foodProperties.saturation());
            }
        }
        itemstack.setDamageValue(itemstack.getDamageValue() + 15);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
            itemstack.shrink(1);
        }
        return itemstack;
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        return new ItemStack(this);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {

    }
}