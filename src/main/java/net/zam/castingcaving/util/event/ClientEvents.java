package net.zam.castingcaving.util.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundSourceEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.block.trophy.TrophyBlockEntityRenderer;
import net.zam.castingcaving.common.client.block.renderer.LostBountyRenderer;
import net.zam.castingcaving.common.client.block.renderer.RefinedAnvilRenderer;
import net.zam.castingcaving.common.gui.arcade.ArcadeGui;
import net.zam.castingcaving.registry.ZAMBlockEntities;
import net.zam.castingcaving.registry.ZAMItems;
import net.zam.castingcaving.registry.ZAMMenuTypes;
import net.zam.castingcaving.registry.ZAMSounds;
import org.jetbrains.annotations.NotNull;

public class ClientEvents {

    @EventBusSubscriber(modid = CastingCaving.MOD_ID, value = Dist.CLIENT)
    public static class ForgeBus {

        @SubscribeEvent
        public static void onSoundPlayed(final @NotNull PlaySoundSourceEvent event) {
            Level level = Minecraft.getInstance().level;
            if (level == null) {
                return;
            }
            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }
            ItemStack heldItem = player.getMainHandItem();
            if (!heldItem.is(ZAMItems.MARINE_FISHING_ROD.get())) {
                return;
            }
            SoundInstance soundInstance = event.getSound();
            double distance = player.distanceToSqr(soundInstance.getX(), soundInstance.getY(), soundInstance.getZ());
            if (distance > 256) {
                return;
            }
            if (event.getSound() instanceof SimpleSoundInstance instance) {
                SoundEvent newEvent = switch (event.getSound().getLocation().getPath()) {
                    case "entity.fishing_bobber.throw" -> ZAMSounds.CAST.get();
                    case "entity.fishing_bobber.retrieve" -> ZAMSounds.PULL_ITEM.get();
                    case "entity.fishing_bobber.splash" -> {
                        Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(
                                ZAMSounds.FISH_BITE.get(),
                                instance.getSource(),
                                1.0F,
                                1.0F,
                                SoundInstance.createUnseededRandom(),
                                instance.getX(),
                                instance.getY(),
                                instance.getZ()));
                        yield null;
                    }
                    default -> null;
                };

                if (newEvent != null) {
                    event.getEngine().stop(instance);
                    event.getEngine().play(new SimpleSoundInstance(
                            newEvent,
                            instance.getSource(),
                            1.0F,
                            1.0F,
                            SoundInstance.createUnseededRandom(),
                            instance.getX(),
                            instance.getY(),
                            instance.getZ()));
                }
            }
        }

        @SubscribeEvent
        public static void onXpPickup(PlayerXpEvent.PickupXp event) {
            Player player = event.getEntity();
            ItemStack heldItem = player.getMainHandItem();

            if (!heldItem.is(ZAMItems.MARINE_FISHING_ROD.get())) {
                return;
            }

            ExperienceOrb orb = event.getOrb();
            if (orb != null) {
                event.setCanceled(true);
                // Play the COMPLETE sound client-side
                Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(
                        ZAMSounds.COMPLETE.get(),
                        SoundSource.PLAYERS,
                        1.0F,
                        (player.level().random.nextFloat() - player.level().random.nextFloat()) * 0.35F + 0.9F,
                        SoundInstance.createUnseededRandom(),
                        player.getX(),
                        player.getY(),
                        player.getZ()
                ));
                player.giveExperiencePoints(orb.value);
                orb.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }

    @EventBusSubscriber(modid = CastingCaving.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModBus {
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ZAMBlockEntities.LOST_BOUNTY.get(), LostBountyRenderer::new);
            event.registerBlockEntityRenderer(ZAMBlockEntities.MARINE_HANGING_SIGN.get(), HangingSignRenderer::new);
            event.registerBlockEntityRenderer(ZAMBlockEntities.MARINE_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(ZAMBlockEntities.REFINED_ANVIL.get(), RefinedAnvilRenderer::new);
            event.registerBlockEntityRenderer(ZAMBlockEntities.TROPHY.get(), TrophyBlockEntityRenderer::new);
            registerFishingRodModelProperties(ZAMItems.IRON_FISHING_ROD.get());
            registerFishingRodModelProperties(ZAMItems.GOLD_FISHING_ROD.get());
            registerFishingRodModelProperties(ZAMItems.DIAMOND_FISHING_ROD.get());
            registerFishingRodModelProperties(ZAMItems.NETHERITE_FISHING_ROD.get());
            registerFishingRodModelProperties(ZAMItems.MARINE_FISHING_ROD.get());
        }

        private static void registerFishingRodModelProperties(Item fishingRod) {
            ItemProperties.register(fishingRod, ResourceLocation.parse("cast"), (stack, level, entity, i) -> {
                if (entity == null) {
                    return 0.0F;
                } else {
                    boolean isMainhand = entity.getMainHandItem() == stack;
                    boolean isOffHand = entity.getOffhandItem() == stack;
                    if (entity.getMainHandItem().getItem() instanceof FishingRodItem) {
                        isOffHand = false;
                    }
                    return (isMainhand || isOffHand) && entity instanceof Player && ((Player) entity).fishing != null ? 1.0F : 0.0F;
                }
            });
        }

        @SubscribeEvent
        public static void onClientSetup(RegisterMenuScreensEvent event) {
            event.register(ZAMMenuTypes.ARCADE_CONTAINER.get(), ArcadeGui::new);
        }
    }
}