package net.zam.castingcaving;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.zam.castingcaving.common.advancement.AchievementLogic;
import net.zam.castingcaving.common.advancement.FishAmountTrigger;
import net.zam.castingcaving.common.advancement.SnakeHighScoreTrigger;
import net.zam.castingcaving.common.client.entity.renderer.MarineBoatRenderer;
import net.zam.castingcaving.common.client.entity.renderer.OtterRenderer;
import net.zam.castingcaving.common.client.item.renderer.FishingRodTooltipRenderer;
import net.zam.castingcaving.common.component.FishingRodTooltipComponent;
import net.zam.castingcaving.common.entity.skins.FrogSkins;
import net.zam.castingcaving.common.entity.skins.WolfSkins;
import net.zam.castingcaving.common.item.sculkbomb.SculkBombRenderer;
import net.zam.castingcaving.common.loot.BiomeTagCheck;
import net.zam.castingcaving.common.network.SnakeHighScorePacket;
import net.zam.castingcaving.registry.*;
import net.zam.castingcaving.util.ZAMWoodTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

@Mod(CastingCaving.MOD_ID)
public class CastingCaving {
    public static final String MOD_ID = "castingcaving";
    public static final Logger LOGGER = LogManager.getLogger();
    public CastingCaving(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerRenderers);
        modEventBus.addListener(this::registerPayloadHandlers);

        ZAMItems.register(modEventBus);
        ZAMSounds.register(modEventBus);
        ZAMBlocks.register(modEventBus);
        ZAMBlockEntities.register(modEventBus);
        ZAMCreativeModeTab.register(modEventBus);
        ZAMEntities.register(modEventBus);
        ZAMEffects.register(modEventBus);
        ZAMArmorMaterial.register(modEventBus);
        ZAMMenuTypes.register(modEventBus);
        ZAMEnchantmentEffects.register(modEventBus);
        ZAMDataComponents.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, ZAMConfig.COMMON_SPEC);

        FROG_VARIANTS.register(modEventBus);
        ATTACHMENT_TYPES.register(modEventBus);
        TRIGGER_TYPES.register(modEventBus);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.FROG, FrogSkins::new);
        event.registerEntityRenderer(EntityType.WOLF, WolfSkins::new);
        event.registerEntityRenderer(ZAMEntities.SCULK_BOMB_ENTITY.get(), SculkBombRenderer::new);
    }

    public static final DeferredRegister<FrogVariant> FROG_VARIANTS = DeferredRegister.create(Registries.FROG_VARIANT, MOD_ID);
    public static final Supplier<FrogVariant> ANCIENT_FROG = FROG_VARIANTS.register("ancient_frog", () -> new FrogVariant(ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/frog/ancient_frog.png")));

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES = DeferredRegister.create(Registries.TRIGGER_TYPE, CastingCaving.MOD_ID);
    public static final DeferredHolder<CriterionTrigger<?>, FishAmountTrigger> FISH_AMOUNT_TRIGGER = TRIGGER_TYPES.register("fish_amount_trigger", FishAmountTrigger :: new);
    public static final DeferredHolder<CriterionTrigger<?>, SnakeHighScoreTrigger> SNAKE_HIGH_SCORE_TRIGGER = TRIGGER_TYPES.register("snake_high_score_trigger", SnakeHighScoreTrigger :: new);

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CastingCaving.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AchievementLogic>> ACHIEVEMENTS = ATTACHMENT_TYPES.register(AchievementLogic.ACHIEVEMENT_LOGIC,
            () -> AttachmentType.builder(AchievementLogic::new).serialize(AchievementLogic.CODEC).copyOnDeath().build());

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ZAMBlocks.MARINE_SAPLING.getId(), ZAMBlocks.POTTED_MARINE_SAPLING);
        });
    }

    public static ResourceLocation id(String s) {
        return ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, s);
    }

    @SubscribeEvent
    private void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(SnakeHighScorePacket.TYPE, SnakeHighScorePacket.STREAM_CODEC, SnakeHighScorePacket::handle);
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Sheets.addWoodType(ZAMWoodTypes.MARINE);
                EntityRenderers.register(ZAMEntities.MARINE_BOAT.get(), context -> new MarineBoatRenderer(context, false));
                EntityRenderers.register(ZAMEntities.MARINE_CHEST_BOAT.get(), context -> new MarineBoatRenderer(context, true));
                EntityRenderers.register(ZAMEntities.OTTER.get(), OtterRenderer::new);
            });
        }
    }

    @SubscribeEvent
    public static void registerTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(FishingRodTooltipComponent.class, FishingRodTooltipRenderer::new);
    }

    @SubscribeEvent
    public static void registerFishies(RegisterEvent event) {
        if (!event.getRegistry().equals(Registries.LOOT_CONDITION_TYPE)) return;
        event.register(
                Registries.LOOT_CONDITION_TYPE,
                ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "biome_tag_check"),
                () -> BiomeTagCheck.BIOME_TAG_CHECK
        );
    }
}