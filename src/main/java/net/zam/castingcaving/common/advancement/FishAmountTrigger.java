/**
 * @author ArcAnc
 * Created at: 22.03.2025
 * Copyright (c) 2025
 * <p>
 * This code is licensed under "Arc's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package net.zam.castingcaving.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.zam.castingcaving.CastingCaving;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FishAmountTrigger extends SimpleCriterionTrigger<FishAmountTrigger.TriggerInstance>
{
    @Override
    public @NotNull Codec<FishAmountTrigger.TriggerInstance> codec()
    {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player)
    {
        this.trigger(player, triggerInstance -> triggerInstance.matches(player));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<MinMaxBounds.Ints> amount) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance :: player),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("amount").forGetter(TriggerInstance :: amount)).
                apply(instance, TriggerInstance :: new));

        public boolean matches(@NotNull ServerPlayer player)
        {
            AchievementLogic logic = player.getData(CastingCaving.ACHIEVEMENTS);
            return this.amount().map(ints -> ints.matches(logic.getFishAmount())).orElse(false);
        }

        public static @NotNull Criterion<TriggerInstance> withAmount(int amount)
        {
            return CastingCaving.FISH_AMOUNT_TRIGGER.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.of(MinMaxBounds.Ints.exactly(amount))));
        }
    }
}
