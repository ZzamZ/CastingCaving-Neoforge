package net.zam.castingcaving.common.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class BlessingOfTheDeep extends MobEffect {


    private static final double SWIM_SPEED_BOOST = 0.03;

    public BlessingOfTheDeep(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
        this.addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.parse("2b43157a-a3d4-4e2b-8d58-95cfd1d8e750"), 4.0D, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);

        if (entity.isInWater()) {
            applySwimmingSpeedBoost(entity, SWIM_SPEED_BOOST);
        }

        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    private void applySwimmingSpeedBoost(LivingEntity entity, double boost) {
        if (entity.isSwimming()) {
            BlockPos entityPos = entity.blockPosition();
            if (entity.level().getBlockState(entityPos).is(Blocks.WATER)) {
                Vec3 lookVector = entity.getViewVector(1.0F);

                Vec3 direction = lookVector.normalize();

                Vec3 boostedVelocity = entity.getDeltaMovement().add(
                        direction.x * boost,
                        direction.y * boost,
                        direction.z * boost
                );

                double maxSpeed = 1.0;
                if (boostedVelocity.length() > maxSpeed) {
                    boostedVelocity = boostedVelocity.normalize().scale(maxSpeed);
                }

                entity.setDeltaMovement(boostedVelocity);
            }
        }
    }
}