package net.zam.castingcaving.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;

public class OpalWandItem extends Item {

    public OpalWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();

        if (block instanceof WeatheringCopper) {
            WeatheringCopper weatheringBlock = (WeatheringCopper) block;
            Optional<BlockState> nextState = weatheringBlock.getNext(blockState);

            if (nextState.isPresent() && level instanceof ServerLevel) {
                level.setBlock(blockPos, nextState.get(), 11);
                level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);

                ((ServerLevel) level).sendParticles(
                        ParticleTypes.WAX_OFF,
                        blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5,
                        10,
                        0.3, 0.3, 0.3,
                        0.05
                );

                if (context.getPlayer() != null) {
                    playSound(context.getPlayer());
                    context.getPlayer().swing(context.getHand());
                }
                Player player = context.getPlayer();
                context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));

                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    private void playSound(Player player) {
        player.level().playSound(
                null,
                player.blockPosition(),
                SoundEvents.AXE_SCRAPE,
                SoundSource.AMBIENT,
                1.0F,
                1.0F
        );
    }
}
