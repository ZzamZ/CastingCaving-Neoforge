package net.zam.castingcaving.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zam.castingcaving.common.block.entity.RefinedAnvilBlockEntity;
import net.zam.castingcaving.registry.ZAMItems;

public class RefinedAnvilBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<RefinedAnvilBlock> CODEC = simpleCodec(RefinedAnvilBlock::new);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape BASE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    protected static final VoxelShape X_LEG1 = Block.box(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D);
    protected static final VoxelShape X_LEG2 = Block.box(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    protected static final VoxelShape X_TOP = Block.box(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    protected static final VoxelShape Z_LEG1 = Block.box(4.0D, 4.0D, 3.0D, 12.0D, 5.0D, 13.0D);
    protected static final VoxelShape Z_LEG2 = Block.box(6.0D, 5.0D, 4.0D, 10.0D, 10.0D, 12.0D);
    protected static final VoxelShape Z_TOP = Block.box(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D);
    protected static final VoxelShape X_AXIS_AABB = Shapes.or(BASE, X_LEG1, X_LEG2, X_TOP);
    protected static final VoxelShape Z_AXIS_AABB = Shapes.or(BASE, Z_LEG1, Z_LEG2, Z_TOP);

    public RefinedAnvilBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return facing.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getClockWise())
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof RefinedAnvilBlockEntity anvilEntity) {
            ItemStack mainStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offStack = player.getItemInHand(InteractionHand.OFF_HAND);

            // Step 1: Handle placement when anvil is empty
            if (anvilEntity.isEmpty() && hand == InteractionHand.MAIN_HAND) {
                // Hammer in main hand, geode in offhand: place from offhand
                if (mainStack.getItem() == ZAMItems.PERIDOT_HAMMER.get() && isValidGeode(offStack)) {
                    if (level.isClientSide) {
                        player.swing(InteractionHand.OFF_HAND, true); // Offhand swing on client
                        return ItemInteractionResult.SUCCESS;
                    }
                    // Server: Place from offhand
                    if (anvilEntity.addItem(offStack)) {
                        level.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        if (!player.getAbilities().instabuild) {
                            offStack.shrink(1);
                            System.out.println("Server: Shrunk offhand stack to: " + offStack); // Debug
                        }
                        return ItemInteractionResult.CONSUME; // Suppress main hand swing
                    }
                }
                // Geode in main hand: place normally
                else if (isValidGeode(mainStack)) {
                    if (anvilEntity.addItem(mainStack)) {
                        level.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        if (!player.getAbilities().instabuild) {
                            mainStack.shrink(1);
                            System.out.println("Server: Shrunk main hand stack to: " + mainStack); // Debug
                        }
                        return ItemInteractionResult.SUCCESS;
                    }
                }
                // No hammer, geode in offhand: place from offhand
                else if (isValidGeode(offStack)) {
                    if (level.isClientSide) {
                        player.swing(InteractionHand.OFF_HAND, true); // Offhand swing on client
                        return ItemInteractionResult.SUCCESS;
                    }
                    if (anvilEntity.addItem(offStack)) {
                        level.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        if (!player.getAbilities().instabuild) {
                            offStack.shrink(1);
                            System.out.println("Server: Shrunk offhand stack to: " + offStack); // Debug
                        }
                        return ItemInteractionResult.CONSUME;
                    }
                }
            }

            // Step 2: Break geode if hammer is in main hand and a geode is on the anvil
            if (hand == InteractionHand.MAIN_HAND && mainStack.getItem() == ZAMItems.PERIDOT_HAMMER.get() && !anvilEntity.isEmpty()) {
                if (level.isClientSide) {
                    player.swing(InteractionHand.MAIN_HAND, true); // Main hand swing on client
                    return ItemInteractionResult.SUCCESS;
                }
                System.out.println("Server: Hammer detected in MAIN_HAND, breaking geode"); // Debug
                ItemStack geodeStack = anvilEntity.getStoredItem().copy();
                if (anvilEntity.processStoredGeodeUsingHammer(mainStack, player)) {
                    spawnBreakingParticles(level, pos, geodeStack, 5);
                    return ItemInteractionResult.SUCCESS;
                } else {
                    return ItemInteractionResult.FAIL;
                }
            }

            // Step 3: Remove geode with empty hand or non-hammer item
            if (!level.isClientSide && !anvilEntity.isEmpty() && hand == InteractionHand.MAIN_HAND) {
                System.out.println("Server: Removing geode with main hand"); // Debug
                ItemStack removed = anvilEntity.removeItem();
                if (!removed.isEmpty()) {
                    if (!player.getInventory().add(removed)) {
                        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), removed);
                    }
                    level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private boolean isValidGeode(ItemStack stack) {
        return stack.getItem() == ZAMItems.GEODE.get()
                || stack.getItem() == ZAMItems.LAVA_GEODE.get()
                || stack.getItem() == ZAMItems.MOSSY_GEODE.get()
                || stack.getItem() == ZAMItems.OCEAN_GEODE.get()
                || stack.getItem() == ZAMItems.ECLIPSE_GEODE.get();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RefinedAnvilBlockEntity(pos, state);
    }

    public static void spawnBreakingParticles(Level level, BlockPos pos, ItemStack stack, int count) {
        System.out.println("Server: Spawning particles for " + stack + " at " + pos); // Debug
        for (int i = 0; i < count; ++i) {
            Vec3 vec3d = new Vec3(((double) level.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double) level.random.nextFloat() - 0.5D) * 0.1D);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack),
                        pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F,
                        1, vec3d.x, vec3d.y + 0.05D, vec3d.z, 0.0D);
            } else {
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack),
                        pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F,
                        vec3d.x, vec3d.y + 0.05D, vec3d.z);
            }
        }
    }
}