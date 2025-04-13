package net.zam.castingcaving.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.zam.castingcaving.registry.ZAMEntities;
import net.zam.castingcaving.registry.ZAMItems;

public class ZAMChestBoatEntity extends ChestBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ZAMChestBoatEntity.class, EntityDataSerializers.INT);

    public ZAMChestBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ZAMChestBoatEntity(Level pLevel, double pX, double pY, double pZ) {
        this(ZAMEntities.MARINE_CHEST_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public Item getDropItem() {
        switch (getModVariant()) {
            case MARINE -> {
                return ZAMItems.MARINE_CHEST_BOAT.get();
            }
        }
        return super.getDropItem();
    }

    public void setVariant(ZAMBoatEntity.Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, ZAMBoatEntity.Type.MARINE.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(ZAMBoatEntity.Type.byName(pCompound.getString("Type")));
        }
    }

    public ZAMBoatEntity.Type getModVariant() {
        return ZAMBoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }
}

