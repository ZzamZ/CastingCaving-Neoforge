package net.zam.castingcaving.registry;

import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.block.arcade.ArcadeBlockEntity;
import net.zam.castingcaving.common.block.entity.LostBountyBlockEntity;
import net.zam.castingcaving.common.block.entity.RefinedAnvilBlockEntity;
import net.zam.castingcaving.common.block.entity.ZAMHangingSignBlockEntity;
import net.zam.castingcaving.common.block.entity.ZAMSignBlockEntity;
import net.zam.castingcaving.common.block.trophy.TrophyBlockEntity;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ZAMBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CastingCaving.MOD_ID);

    public static final Supplier<BlockEntityType<LostBountyBlockEntity>> LOST_BOUNTY = register("lost_bounty", () -> BlockEntityType.Builder.of(LostBountyBlockEntity::new, ZAMBlocks.LOST_BOUNTY.get()));
    public static final Supplier<BlockEntityType<ArcadeBlockEntity>> ARCADE_MACHINE = register("arcade_machine", () -> BlockEntityType.Builder.of(ArcadeBlockEntity::new, ZAMBlocks.ARCADE_MACHINE.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrophyBlockEntity>> TROPHY = BLOCK_ENTITIES.register("trophy", () -> BlockEntityType.Builder.of(TrophyBlockEntity::new, ZAMBlocks.TROPHY_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<ZAMSignBlockEntity>> MARINE_SIGN =
            BLOCK_ENTITIES.register("marine_sign", () ->
                    BlockEntityType.Builder.of(ZAMSignBlockEntity::new,
                            ZAMBlocks.MARINE_SIGN.get(), ZAMBlocks.MARINE_WALL_SIGN.get()).build(null));

    public static final Supplier<BlockEntityType<ZAMHangingSignBlockEntity>> MARINE_HANGING_SIGN =
            BLOCK_ENTITIES.register("marine_hanging_sign", () ->
                    BlockEntityType.Builder.of(ZAMHangingSignBlockEntity::new,
                            ZAMBlocks.MARINE_HANGING_SIGN.get(), ZAMBlocks.MARINE_WALL_HANGING_SIGN.get()).build(null));

    public static final Supplier<BlockEntityType<RefinedAnvilBlockEntity>> REFINED_ANVIL = BLOCK_ENTITIES.register("refined_anvil",
            () -> BlockEntityType.Builder.of(RefinedAnvilBlockEntity::new, ZAMBlocks.REFINED_ANVIL.get()).build(null));

    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(@Nonnull String name, @Nonnull Supplier<BlockEntityType.Builder<T>> initializer) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, CastingCaving.MOD_ID + ":" + name);
        return BLOCK_ENTITIES.register(name, () -> initializer.get().build(type));
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
