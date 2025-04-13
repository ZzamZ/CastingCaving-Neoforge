package net.zam.castingcaving.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.ItemAbilities;

import java.util.List;
import java.util.function.Supplier;

public class MarineBerryLeavesBlock extends LeavesBlock {
    private final Supplier<Item> berryItemSupplier;

    public MarineBerryLeavesBlock(Properties properties, Supplier<Item> berryItemSupplier) {
        super(properties);
        this.berryItemSupplier = berryItemSupplier;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(DISTANCE, 7)
                .setValue(PERSISTENT, false));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        ItemStack tool = builder.getParameter(LootContextParams.TOOL);
        Item berryItem = berryItemSupplier.get();

        if (tool != null && tool.canPerformAction(ItemAbilities.SHEARS_HARVEST) &&
                tool.getEnchantmentLevel(builder.getLevel().registryAccess()
                        .lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT)
                        .getOrThrow(net.minecraft.world.item.enchantment.Enchantments.SILK_TOUCH)) > 0) {
            return List.of(new ItemStack(this));
        } else {
            return List.of(new ItemStack(berryItem));
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE) >= 7) {
            dropResources(state, level, pos);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        int distance = getDistanceAt(facingState) + 1;
        if (distance > 7) {
            distance = 7;
        }
        if (distance != 1 || state.getValue(DISTANCE) != distance) {
            level.scheduleTick(currentPos, this, 1);
        }
        return state.setValue(DISTANCE, distance);
    }

    private static int getDistanceAt(BlockState neighbor) {
        if (neighbor.is(net.minecraft.tags.BlockTags.LOGS)) {
            return 0;
        } else if (neighbor.getBlock() instanceof LeavesBlock) {
            return neighbor.getValue(DISTANCE);
        }
        return 7;
    }
}