package net.zam.castingcaving.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMTags;
import net.zam.castingcaving.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Frog.class)
public abstract class FrogVariantsMixin implements IEntityDataSaver {

    @Inject(
            method = {"finalizeSpawn"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/frog/FrogAi;initMemories(Lnet/minecraft/world/entity/animal/frog/Frog;Lnet/minecraft/util/RandomSource;)V"
            )}
    )

    private void changeFrogs(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {

        Frog frog = (Frog) (Object) this;
        BlockPos spawnPos = frog.blockPosition();
        Holder<Biome> holder = level.getBiome(spawnPos);
        if (holder.is(ZAMTags.SPAWNS_ANCIENT_VARIANT_FROGS))
            frog.setVariant(Holder.direct(CastingCaving.ANCIENT_FROG.get()));
    }
}
