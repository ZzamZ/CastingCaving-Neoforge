package net.zam.castingcaving.util.event.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMItems;
import net.zam.castingcaving.registry.ZAMDataComponents;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

@EventBusSubscriber(modid = CastingCaving.MOD_ID)
public class TrophyAwardHandler {
    private static final Set<UUID> recentlyAwardedPlayers = Collections.newSetFromMap(new WeakHashMap<>());

    @SubscribeEvent
    public static void onAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (event.getAdvancement().id().toString().equals("castingcaving:snake_high_score")) {
            boolean hasTrophy = player.getInventory().items.stream()
                    .anyMatch(stack -> {
                        String ownerName = stack.get(ZAMDataComponents.OWNER_NAME.get());
                        return stack.getItem() == ZAMItems.TROPHY.get()
                                && ownerName != null
                                && ownerName.equals(player.getName().getString());
                    });

            if (!hasTrophy && recentlyAwardedPlayers.add(player.getUUID())) {
                ItemStack trophyWithOwner = ZAMDataComponents.createTrophyWithOwner(player.getName().getString());
                if (!player.getInventory().add(trophyWithOwner)) {
                    Vec3 playerPos = player.position();
                    player.level().addFreshEntity(new net.minecraft.world.entity.item.ItemEntity(player.level(),
                            playerPos.x, playerPos.y, playerPos.z, trophyWithOwner));
                    CastingCaving.LOGGER.info("Dropped trophy for player: " + player.getName().getString());
                } else {
                    CastingCaving.LOGGER.info("Awarded trophy to player: " + player.getName().getString());
                }
            }
        }
    }
}