package net.zam.castingcaving.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NeptunesGlaiveItem extends SwordItem {
    private static final int DAMAGE_MODIFIER = 2; // Adjusted to cap at 8 total damage
    private static final int IMPALING_LEVEL = 3;

    public NeptunesGlaiveItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(@NotNull ItemStack stack) {
        float baseDamage = this.getTier().getAttackDamageBonus(); // Tier's bonus only
        float baseSpeed = -2.4F;

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        builder.add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("castingcaving", "neptunes_glaive_damage"),
                        baseDamage + DAMAGE_MODIFIER, // Total modifier = 7.0F (5.0F tier + 2.0F)
                        AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.HAND
        );

        builder.add(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("castingcaving", "neptunes_glaive_speed"),
                        baseSpeed,
                        AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.HAND
        );
        return builder.build();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean didHurt = super.hurtEnemy(stack, target, attacker);
        if (isAquaticMob(target) && attacker instanceof Player player) {
            target.hurt(attacker.damageSources().playerAttack(player), IMPALING_LEVEL * 2.5F);
        }
        return didHurt;
    }

    private boolean isAquaticMob(LivingEntity entity) {
        return entity.getType().is(EntityTypeTags.AQUATIC);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!level.isClientSide && isSelected && entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_RESISTANCE,
                    1,
                    0,
                    false,
                    false
            ));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("enchantment.minecraft.impaling")
                .append(" ").append(Component.translatable("enchantment.level." + IMPALING_LEVEL))
                .withStyle(ChatFormatting.AQUA));
        tooltipComponents.add(Component.literal("Grants Resistance while held in main hand")
                .withStyle(ChatFormatting.GRAY));
    }
}