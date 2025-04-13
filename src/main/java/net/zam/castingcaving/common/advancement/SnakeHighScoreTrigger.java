package net.zam.castingcaving.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zam.castingcaving.CastingCaving;

import java.util.Optional;

public class SnakeHighScoreTrigger extends SimpleCriterionTrigger<SnakeHighScoreTrigger.TriggerInstance> {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "snake_high_score");

    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, int score) {
        this.trigger(player, (instance) -> instance.matches(score));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints score) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((builder) ->
                builder.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("score", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::score)
                ).apply(builder, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> snakeHighScore(MinMaxBounds.Ints score) {
            return new Criterion<>(CastingCaving.SNAKE_HIGH_SCORE_TRIGGER.get(), new TriggerInstance(Optional.empty(), score));
        }

        public boolean matches(int score) {
            return this.score.matches(score);
        }

        public Optional<ContextAwarePredicate> player() {
            return this.player;
        }

        public MinMaxBounds.Ints score() {
            return this.score;
        }
    }
}