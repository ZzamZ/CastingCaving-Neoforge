package net.zam.castingcaving.common.block.trophy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zam.castingcaving.registry.ZAMDataComponents;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TrophyBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape BASE = Block.box(3, 0, 3, 13, 1, 13);
    private static final VoxelShape MIDDLE = Block.box(4, 1, 4, 12, 3, 12);
    private static final VoxelShape TROPHY_BOTTOM = Block.box(5.5, 3, 5.5, 10.5, 6, 10.5);
    private static final VoxelShape TROPHY_MIDDLE = Block.box(5, 6, 5, 11, 7, 11);
    private static final VoxelShape TROPHY_MAIN_1 = Block.box(4, 7, 5, 5, 15, 11);
    private static final VoxelShape TROPHY_MAIN_2 = Block.box(4, 7, 4, 11, 15, 5);
    private static final VoxelShape TROPHY_MAIN_3 = Block.box(4, 7, 11, 12, 15, 12);
    private static final VoxelShape TROPHY_MAIN_4 = Block.box(11, 7, 4, 12, 15, 11);

    private static final VoxelShape TROPHY_SHAPE = Shapes.or(BASE, MIDDLE, TROPHY_BOTTOM, TROPHY_MIDDLE,
            TROPHY_MAIN_1, TROPHY_MAIN_2, TROPHY_MAIN_3, TROPHY_MAIN_4);

    public TrophyBlock() {
        super(Properties.of()
                .strength(0.0F, 0.0F)
                .sound(SoundType.METAL)
                .instabreak());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, net.minecraft.core.Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide && itemstack.isEmpty()) {
            if (level.getBlockEntity(pos) instanceof TrophyBlockEntity trophyTileEntity) {
                if (!player.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }

                String ownerName = trophyTileEntity.getPlayerName();
                ItemStack trophyStack = new ItemStack(this.asItem());
                trophyStack.set(ZAMDataComponents.OWNER_NAME.get(), ownerName);
                trophyStack.set(DataComponents.CUSTOM_NAME, Component.literal(ownerName + "'s Trophy"));

                player.setItemSlot(EquipmentSlot.HEAD, trophyStack);
                level.playSound((Entity) null, pos, (SoundEvent) SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.PLAYERS, 1.0F, 1.0F);
                level.removeBlock(pos, false);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return TROPHY_SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TrophyBlockEntity(pos, state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return Collections.emptyList();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (level.getBlockEntity(pos) instanceof TrophyBlockEntity trophyTileEntity) {
            String ownerName = stack.get(ZAMDataComponents.OWNER_NAME.get()); // Use Data Component
            if (ownerName != null && !ownerName.isEmpty()) {
                trophyTileEntity.setPlayerName(ownerName);
            }
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof TrophyBlockEntity trophyTileEntity) {
            ItemStack stack = new ItemStack(this);
            String ownerName = trophyTileEntity.getPlayerName();
            if (!ownerName.isEmpty()) {
                stack.set(ZAMDataComponents.OWNER_NAME.get(), ownerName);
                stack.set(DataComponents.CUSTOM_NAME, Component.literal(ownerName + "'s Trophy"));
            }
            popResource(level, pos, stack);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}