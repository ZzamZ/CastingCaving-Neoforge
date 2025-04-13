package net.zam.castingcaving.util.enchant;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireworkRocketEntityAttacher extends FireworkRocketEntity {

    public FireworkRocketEntityAttacher(EntityType<? extends FireworkRocketEntity> type, Level level) {
        super(type, level);
    }

    public FireworkRocketEntityAttacher(Level level, double x, double y, double z, ItemStack stack) {
        super(level, x, y, z, stack);
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof LivingEntity living) {
                if (living instanceof Wolf wolf) {
                    wolf.setOrderedToSit(false);
                }
                living.setPose(Pose.STANDING);
                living.refreshDimensions();
            }
        }
    }

    /**
     * After vanilla positions the rider, we override again to force STANDING pose.
     */
    @Override
    protected void positionRider(Entity passenger, Entity.MoveFunction moveFunc) {
        super.positionRider(passenger, moveFunc);

        if (passenger instanceof LivingEntity living) {
            if (living instanceof Wolf wolf) {
                wolf.setOrderedToSit(false);
            }
            living.setPose(Pose.STANDING);
            living.refreshDimensions();

            double offsetY = 0.0; // e.g., 0.2 to raise them up slightly
            passenger.setPos(passenger.getX(), passenger.getY() + offsetY, passenger.getZ());
        }
    }

    /**
     * Called when the rocket explodes (event ID 17). We do lethal damage to the passengers.
     */
    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);

        if (!this.level().isClientSide && id == 17) {
            for (Entity p : this.getPassengers()) {
                if (p instanceof LivingEntity living) {
                    living.hurt(
                            this.level().damageSources().explosion(this, this.getOwner()),
                            1000.0F
                    );
                }
            }
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return true;
    }
}
