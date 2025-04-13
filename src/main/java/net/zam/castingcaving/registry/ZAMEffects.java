package net.zam.castingcaving.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.effect.BlessingOfTheDeep;

import java.util.function.Supplier;

public class ZAMEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, CastingCaving.MOD_ID);


    public static final Supplier<MobEffect> BLESSING_OF_THE_DEEP = MOB_EFFECTS.register("blessing_of_the_deep",
            () -> new BlessingOfTheDeep(MobEffectCategory.BENEFICIAL, 0xADD8E6));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}

