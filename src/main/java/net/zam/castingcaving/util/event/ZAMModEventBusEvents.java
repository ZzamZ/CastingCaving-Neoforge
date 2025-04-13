package net.zam.castingcaving.util.event;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.client.entity.layers.ZAMModelLayers;
import net.zam.castingcaving.common.client.entity.model.OtterModel;
import net.zam.castingcaving.common.entity.OtterEntity;
import net.zam.castingcaving.common.loot.BiomeTagCheck;
import net.zam.castingcaving.registry.ZAMEntities;

@EventBusSubscriber(modid = CastingCaving.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ZAMModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ZAMModelLayers.MARINE_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ZAMModelLayers.MARINE_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(ZAMModelLayers.OTTER, OtterModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ZAMEntities.OTTER.get(), OtterEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterLootConditions(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.LOOT_CONDITION_TYPE)) return;

        event.register(
                Registries.LOOT_CONDITION_TYPE,
                ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "biome_tag_check"),
                () -> BiomeTagCheck.BIOME_TAG_CHECK
        );
    }
}