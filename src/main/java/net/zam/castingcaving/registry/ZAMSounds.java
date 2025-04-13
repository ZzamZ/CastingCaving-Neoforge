package net.zam.castingcaving.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zam.castingcaving.CastingCaving;

import java.util.function.Supplier;

public class ZAMSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CastingCaving.MOD_ID);

    public static final Supplier<SoundEvent> CAST = registerSoundEvent("cast");
    public static final Supplier<SoundEvent> FISH_BITE = registerSoundEvent("fish_bite");
    public static final Supplier<SoundEvent> PULL_ITEM = registerSoundEvent("pull_item");
    public static final Supplier<SoundEvent> COMPLETE = registerSoundEvent("complete");

    public static final Supplier<SoundEvent> TIDE = registerSoundEvent("tide");
    public static final Supplier<SoundEvent> DOG = registerSoundEvent("dog");
    public static final Supplier<SoundEvent> HALLAND = registerSoundEvent("halland");
    public static final Supplier<SoundEvent> HOWLING = registerSoundEvent("howling");

    public static final Supplier<SoundEvent> OTTER_AMBIENT = registerSoundEvent("entity.otter.ambient");
    public static final Supplier<SoundEvent> OTTER_DEATH = registerSoundEvent("entity.otter.death");
    public static final Supplier<SoundEvent> OTTER_EAT = registerSoundEvent("entity.otter.eat");
    public static final Supplier<SoundEvent> OTTER_HURT = registerSoundEvent("entity.otter.hurt");
    public static final Supplier<SoundEvent> OTTER_SWIM = registerSoundEvent("entity.otter.swim");
    public static final Supplier<SoundEvent> BITE_ATTACK = registerSoundEvent("entity.bite_attack");


    public static final Supplier<SoundEvent> WARP = registerSoundEvent("warp");
    public static final Supplier<SoundEvent> TOTEM = registerSoundEvent("totem");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
