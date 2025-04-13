package net.zam.castingcaving.registry;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import com.mojang.serialization.Codec;

public class ZAMDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "castingcaving");

    public record BaitData(String id, int count) {
        public static final Codec<BaitData> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(Codec.STRING.fieldOf("id").forGetter(BaitData::id),
                        Codec.INT.fieldOf("count").forGetter(BaitData::count)).apply(instance, BaitData::new));
    }

    public record LureData(String id, int durability) {
        public static final Codec<LureData> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(Codec.STRING.fieldOf("id").forGetter(LureData::id),
                        Codec.INT.fieldOf("durability").forGetter(LureData::durability)).apply(instance, LureData::new));
    }

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> OWNER_NAME =
            DATA_COMPONENTS.register("owner_name", () ->
                    DataComponentType.<String>builder().persistent(Codec.STRING).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BaitData>> ATTACHED_BAIT =
            DATA_COMPONENTS.register("attached_bait", () ->
                    DataComponentType.<BaitData>builder().persistent(BaitData.CODEC).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LureData>> ATTACHED_LURE =
            DATA_COMPONENTS.register("attached_lure", () ->
                    DataComponentType.<LureData>builder().persistent(LureData.CODEC).build());

    public static ItemStack createTrophyWithOwner(String playerName) {
        ItemStack trophyItem = new ItemStack(ZAMItems.TROPHY.get());
        trophyItem.set(OWNER_NAME.get(), playerName);
        trophyItem.set(DataComponents.CUSTOM_NAME, Component.literal(playerName + "'s Trophy"));
        return trophyItem;
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }

}