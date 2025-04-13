package net.zam.castingcaving.util.event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.item.fishing.ZAMFishingRodItem;
import net.zam.castingcaving.registry.ZAMDataComponents;
import net.zam.castingcaving.registry.ZAMItems;

import java.util.List;

@EventBusSubscriber(modid = CastingCaving.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ZAMGameEventBusEvents {

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        ItemStack rod = event.getEntity().getMainHandItem();
        if (!(rod.getItem() instanceof ZAMFishingRodItem customRod)) return;

        Player player = event.getEntity();
        RandomSource random = player.getRandom();
        List<ItemStack> drops = event.getDrops();

        // Consume bait after catching a fish
        if (customRod.supportsBait()) {
            ZAMDataComponents.BaitData baitData = rod.get(ZAMDataComponents.ATTACHED_BAIT.get());
            if (baitData != null && baitData.count() > 0) {
                int newCount = baitData.count() - 1;
                if (newCount > 0) {
                    rod.set(ZAMDataComponents.ATTACHED_BAIT.get(), new ZAMDataComponents.BaitData(baitData.id(), newCount));
                } else {
                    rod.remove(ZAMDataComponents.ATTACHED_BAIT.get());
                }
            }
        }

        // Apply lure effects
        if (customRod.supportsLure()) {
            ZAMDataComponents.LureData lureData = rod.get(ZAMDataComponents.ATTACHED_LURE.get());
            if (lureData != null) {
                String lureId = lureData.id();
                switch (lureId) {
                    case "castingcaving:luck_hook":
                        // Already applied via FishingRodItem's luck bonus
                        break;
                    case "castingcaving:double_hook":
                        if (random.nextFloat() < 0.25f) {
                            ItemStack extraLoot = getExtraLoot(random);
                            drops.add(extraLoot);
                        }
                        break;
                    case "castingcaving:treasure_hook":
                        if (random.nextFloat() < 1.0f) {
                            ItemStack treasure = new ItemStack(getRandomTreasure(random));
                            drops.add(treasure);
                        }
                        break;
                    // melody_hook sound is handled in FishingHookMixin
                    // lure_hook XP increase is handled in FishingHookMixin
                }
            }
        }

        // Lava fishing
        if (customRod.supportsLavaFishing() /* && hook was in lava */) {
            drops.clear();
            ItemStack lavaLoot = getLavaLoot(random);
            drops.add(lavaLoot);
        }

        // Bonus loot for Aquamarine
        if (customRod.hasBonusLoot() && random.nextFloat() < 0.15f) {
            ItemStack bonusLoot = new ItemStack(ZAMItems.PRISMATIC_SHARD.get());
            drops.add(bonusLoot);
        }
    }

    // Helper methods for lure effects
    private static ItemStack getExtraLoot(RandomSource random) {
        Item[] mediumFish = {
                ZAMItems.FROG_FISH.get(), ZAMItems.SLIME_FISH.get(), ZAMItems.SHROOM_FISH.get(),
                ZAMItems.KOI_FISH.get(), ZAMItems.NOSTALGIA_FISH.get(), ZAMItems.CARROT_FISH.get(),
                ZAMItems.LAVA_EEL.get(), ZAMItems.BONE_FISH.get(), ZAMItems.RAINBOW_TROUT.get(),
                ZAMItems.GHOST_FISH.get()
        };
        return new ItemStack(mediumFish[random.nextInt(mediumFish.length)]);
    }

    private static Item getRandomTreasure(RandomSource random) {
        Item[] treasures = {
                ZAMItems.PRISMATIC_FISH.get(), ZAMItems.ECLIPSE_KOI.get(), ZAMItems.CRIMSONFISH.get(),
                ZAMItems.GLIMMERING_STARFISH.get(), ZAMItems.THE_LEGEND.get(), ZAMItems.MOONLIGHT_JELLYFISH.get(),
                ZAMItems.SPECTRAL_ANGLER_FISH.get()
        };
        return treasures[random.nextInt(treasures.length)];
    }

    private static ItemStack getLavaLoot(RandomSource random) {
        Item[] lavaFish = {ZAMItems.LAVA_EEL.get(), ZAMItems.BONE_FISH.get(), ZAMItems.WITHERING_GUPPY.get()};
        return new ItemStack(lavaFish[random.nextInt(lavaFish.length)]);
    }
}