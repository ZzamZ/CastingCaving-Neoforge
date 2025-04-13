package net.zam.castingcaving.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.zam.castingcaving.registry.ZAMSounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TotemOfReturning extends Item {
    private static final int MAX_USE_DURATION = 72000;

    public TotemOfReturning(Properties props) {
        super(props);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return MAX_USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.getCooldowns().addCooldown(this, 40);
            level.playSound(null, player.blockPosition(), ZAMSounds.TOTEM.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

            Level finalLevel = level; // Make effectively final for lambda
            ItemStack finalStack = stack; // Make effectively final for lambda
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // 2 seconds = 40 ticks at 20 ticks/second
                } catch (InterruptedException e) {
                    return;
                }

                if (player.isUsingItem() && player.getUseItem() == finalStack) {
                    ((ServerLevel) finalLevel).getServer().execute(() -> {
                        if (player instanceof ServerPlayer sp) {
                            finalStack.shrink(1);
                            sp.awardStat(Stats.ITEM_USED.get(this));
                            sp.getCooldowns().addCooldown(this, 40);
                            finalLevel.playSound(null, sp.blockPosition(), ZAMSounds.WARP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

                            List<Mob> mobs = getLeashedMobs(sp);
                            teleportPlayer(sp);
                            teleportLeashedMobs(mobs, sp);
                        }
                    });
                }
            }).start();
        }

        if (level.isClientSide) {
            ItemStack finalStack = stack; // Make effectively final for lambda
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return;
                }

                if (player.isUsingItem() && player.getUseItem() == finalStack) {
                    Minecraft.getInstance().execute(() -> {
                        Minecraft.getInstance().gameRenderer.displayItemActivation(finalStack.copy());
                        Minecraft.getInstance().particleEngine.createTrackingEmitter(
                                player, net.minecraft.core.particles.ParticleTypes.TOTEM_OF_UNDYING, 30
                        );
                    });
                }
            }).start();
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        // No action here; thread in `use` handles the logic
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof ServerPlayer sp) || level.isClientSide) {
            return stack;
        }

        stack.shrink(1);
        sp.awardStat(Stats.ITEM_USED.get(this));
        sp.getCooldowns().addCooldown(this, 40);
        level.playSound(null, sp.blockPosition(), ZAMSounds.WARP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

        List<Mob> mobs = getLeashedMobs(sp);
        teleportPlayer(sp);
        teleportLeashedMobs(mobs, sp);

        return stack;
    }

    /**
     * Retrieves all mobs leashed to the player, including those in a leash chain.
     * @param player The server player whose leashed mobs are to be collected.
     * @return A list of all mobs in the leash chain.
     */
    private List<Mob> getLeashedMobs(ServerPlayer player) {
        ServerLevel lvl = (ServerLevel) player.level();
        List<Mob> result = new ArrayList<>();
        getAllLeashedMobs(player, lvl, result);
        return result;
    }

    /**
     * Recursively collects all mobs leashed to the given holder within a 256-block radius.
     * @param holder The entity (player or mob) whose leashed mobs are being collected.
     * @param level The server level to search in.
     * @param result The list to accumulate the leashed mobs.
     */
    private void getAllLeashedMobs(Entity holder, ServerLevel level, List<Mob> result) {
        AABB area = new AABB(holder.blockPosition()).inflate(256);
        for (Mob mob : level.getEntitiesOfClass(Mob.class, area)) {
            if (mob.getLeashHolder() == holder && !result.contains(mob)) {
                result.add(mob);
                getAllLeashedMobs(mob, level, result); // Recursively find mobs leashed to this mob
            }
        }
    }

    private void teleportPlayer(ServerPlayer player) {
        ServerLevel curr = (ServerLevel) player.level();
        ServerLevel respawnDimension = player.getServer().getLevel(player.getRespawnDimension());
        if (respawnDimension == null) {
            respawnDimension = curr.getServer().overworld();
        }

        Optional<Vec3> spawnOpt = findSpawn(respawnDimension, player);
        ServerLevel finalRespawnDimension = respawnDimension;
        Vec3 pos = spawnOpt.orElseGet(() -> {
            BlockPos w = finalRespawnDimension.getSharedSpawnPos();
            player.connection.send(new ClientboundGameEventPacket(
                    ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE, 0
            ));
            return new Vec3(w.getX() + 0.5, w.getY(), w.getZ() + 0.5);
        });

        if (curr != respawnDimension) {
            DimensionTransition transition = new DimensionTransition(
                    respawnDimension, pos, Vec3.ZERO, player.getYRot(), player.getXRot(), false, null
            );
            player.changeDimension(transition);
        } else {
            player.teleportTo(pos.x, pos.y, pos.z);
        }
        player.fallDistance = 0.0F;
    }

    private void teleportLeashedMobs(List<Mob> mobs, ServerPlayer player) {
        ServerLevel dst = (ServerLevel) player.level();
        Vec3 playerPos = player.position();

        for (Mob oldMob : mobs) {
            // Drop the leash temporarily to avoid issues during teleportation
            oldMob.dropLeash(true, false);

            // Add a small random offset (-1 to 1 block on X and Z axes)
            double offsetX = (Math.random() - 0.5) * 2;
            double offsetZ = (Math.random() - 0.5) * 2;
            Vec3 mobPos = playerPos.add(offsetX, 0, offsetZ);

            if (oldMob.level() != dst) {
                // Cross-dimension teleportation
                DimensionTransition transition = new DimensionTransition(
                        dst, mobPos, Vec3.ZERO, player.getYRot(), player.getXRot(), false, null
                );
                Entity e = oldMob.changeDimension(transition);
                if (e instanceof Mob newMob) {
                    newMob.setPos(mobPos.x, mobPos.y, mobPos.z);
                    newMob.fallDistance = 0.0F; // Prevent fall damage
                    newMob.setLeashedTo(player, true); // Re-leash to player
                    dst.addDuringTeleport(newMob);
                    player.connection.send(new ClientboundTeleportEntityPacket(newMob)); // Sync with client
                }
            } else {
                // Same-dimension teleportation
                oldMob.teleportTo(mobPos.x, mobPos.y, mobPos.z);
                oldMob.fallDistance = 0.0F;
                oldMob.setLeashedTo(player, true);
                player.connection.send(new ClientboundTeleportEntityPacket(oldMob)); // Sync with client
            }
        }
    }

    private Optional<Vec3> findSpawn(ServerLevel level, ServerPlayer sp) {
        BlockPos respawn = sp.getRespawnPosition();
        if (respawn == null) return Optional.empty();

        BlockState st = level.getBlockState(respawn);
        Block b = st.getBlock();

        if (b instanceof RespawnAnchorBlock anchor) {
            if (st.getValue(RespawnAnchorBlock.CHARGE) > 0 && RespawnAnchorBlock.canSetSpawn(level)) {
                return RespawnAnchorBlock.findStandUpPosition(sp.getType(), level, respawn);
            }
        } else if (b instanceof BedBlock bed) {
            if (BedBlock.canSetSpawn(level)) {
                return BedBlock.findStandUpPosition(sp.getType(), level, respawn, sp.getDirection(), sp.getYRot());
            }
        }
        return Optional.empty();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(
                    Component.translatable("item.castingcaving.totem_of_returning.tooltip").withStyle(ChatFormatting.AQUA)
            );
            tooltipComponents.add(
                    Component.translatable("item.castingcaving.totem_of_returning.usage").withStyle(ChatFormatting.AQUA)
            );
        } else {
            tooltipComponents.add(
                    Component.translatable("tooltip.castingcaving.hold_shift")
                            .withStyle(style -> style.withItalic(true).withColor(ChatFormatting.AQUA))
            );
        }
    }
}