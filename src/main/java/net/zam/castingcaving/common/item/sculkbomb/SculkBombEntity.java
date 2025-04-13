package net.zam.castingcaving.common.item.sculkbomb;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SculkBombEntity extends ThrowableProjectile {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(SculkBombEntity.class, EntityDataSerializers.ITEM_STACK);
    private boolean landed = false;
    private int timer = 0;
    private int groundY = Integer.MAX_VALUE;
    private boolean hasPlayedRisingSound = false;
    private boolean hasReachedTargetHeight = false;

    public SculkBombEntity(EntityType<? extends SculkBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SculkBombEntity(EntityType<? extends SculkBombEntity> entityType, Level level, LivingEntity thrower) {
        super(entityType, thrower, level);
    }

    @Override
    public void tick() {
        super.tick();

        // Trail particle while in motion or rising, stop when target height is reached
        if (!hasReachedTargetHeight) {
            level().addParticle(new SculkChargeParticleOptions(0.5f), getX(), getY(), getZ(), 0.0, 0.0, 0.0);
        }

        if (landed) {
            setDeltaMovement(Vec3.ZERO);
            timer++;

            if (timer > 5 && timer < 35) {
                double targetHeight = this.groundY + 4;

                if (getY() < targetHeight) {
                    setPos(getX(), getY() + 0.15, getZ());

                    if (!hasPlayedRisingSound) {
                        level().playSound(null, blockPosition(), SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        hasPlayedRisingSound = true;
                    }
                } else {
                    hasReachedTargetHeight = true;  // Stop trail particles once target height is reached
                }

                pullEntitiesIn();
            }

            if (timer >= 35 && timer < 55) {
                pullEntitiesToCenter(); // Pull entities to the bomb's center
            }

            if (timer == 45) {
                level().addParticle(ParticleTypes.SONIC_BOOM, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
            }

            if (timer == 55) {
                explode();
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!landed) {
            landed = true;
            groundY = (int) getY();
            setPos(getX(), getY() + 1, getZ());
        }
    }

    private void pullEntitiesIn() {
        AABB area = new AABB(this.blockPosition()).inflate(5);
        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities) {
            if (entity != this.getOwner()) {
                double dx = getX() - entity.getX();
                double dy = getY() - entity.getY();
                double dz = getZ() - entity.getZ();

                Vec3 pull = new Vec3(dx, dy, dz).normalize().scale(0.15);  // Stronger pull effect
                entity.setDeltaMovement(entity.getDeltaMovement().add(pull));
                entity.hurtMarked = true;
            }
        }
    }

    private void pullEntitiesToCenter() {
        AABB area = new AABB(this.blockPosition()).inflate(5);
        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities) {
            double dx = getX() - entity.getX();
            double dy = getY() - entity.getY();
            double dz = getZ() - entity.getZ();

            // Increase pull strength as entities get close
            Vec3 finalPull = new Vec3(dx, dy, dz).normalize().scale(0.3);
            entity.setDeltaMovement(finalPull);
            entity.hurtMarked = true;
        }
    }

    private void explode() {
        AABB explosionArea = new AABB(this.blockPosition()).inflate(3);
        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, explosionArea);

        for (LivingEntity entity : entities) {
            entity.hurt(this.damageSources().explosion(this, this.getOwner()), 10.0F);

            // Apply knockback to each affected entity
            Vec3 knockback = new Vec3(entity.getX() - getX(), entity.getY() - getY(), entity.getZ() - getZ()).normalize().scale(1.5);
            entity.setDeltaMovement(knockback);
        }

        level().playSound(null, blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public DamageSources damageSources() {
        return this.level().damageSources();
    }

    public void setItem(ItemStack stack) {
        this.getEntityData().set(DATA_ITEM_STACK, stack);
    }

    public ItemStack getItem() {
        return this.entityData.get(DATA_ITEM_STACK);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }
}
