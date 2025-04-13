package net.zam.castingcaving.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.entity.OtterEntity;
import net.zam.castingcaving.common.entity.ZAMBoatEntity;
import net.zam.castingcaving.common.entity.ZAMChestBoatEntity;
import net.zam.castingcaving.common.item.sculkbomb.SculkBombEntity;
import net.zam.castingcaving.util.enchant.FireworkRocketEntityAttacher;

import java.util.function.Supplier;

public class ZAMEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CastingCaving.MOD_ID);


    public static final Supplier<EntityType<SculkBombEntity>> SCULK_BOMB_ENTITY = ENTITY_TYPES.register("sculk_bomb_entity",
            () -> EntityType.Builder.<SculkBombEntity>of(SculkBombEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(ResourceLocation.fromNamespaceAndPath("castingcaving", "sculk_bomb_entity").toString()));

    public static final Supplier<EntityType<OtterEntity>> OTTER = ENTITY_TYPES.register("otter", () -> EntityType.Builder.of(OtterEntity::new, MobCategory.WATER_CREATURE).sized(0.8F, 0.4F).build("otter"));


    public static final Supplier<EntityType<ZAMBoatEntity>> MARINE_BOAT =
            ENTITY_TYPES.register("marine_boat", () -> EntityType.Builder.<ZAMBoatEntity>of(ZAMBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("marine_boat"));
    public static final Supplier<EntityType<ZAMChestBoatEntity>> MARINE_CHEST_BOAT =
            ENTITY_TYPES.register("marine_chest_boat", () -> EntityType.Builder.<ZAMChestBoatEntity>of(ZAMChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("marine_chest_boat"));

    public static final Supplier<EntityType<FireworkRocketEntityAttacher>> FIREWORK_ROCKET_ATTACHER = ENTITY_TYPES.register(
            "firework_rocket_attacher", () -> EntityType.Builder.<FireworkRocketEntityAttacher>of(FireworkRocketEntityAttacher::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
                    .build(ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "firework_rocket_attacher").toString()));


    public static <T extends Entity> Supplier<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, name);
        return ENTITY_TYPES.register(name, () -> builder.get().build(location.toString()));
    }



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}