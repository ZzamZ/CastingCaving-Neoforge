/**
 * @author ...
 * Created at: 19.03.2025
 * Copyright ...
 *
 * This code is licensed under "Arc's License of Common Sense"
 * ...
 */

package net.zam.castingcaving.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.zam.castingcaving.CastingCaving;
import org.jetbrains.annotations.NotNull;

/**
 * Tracks per-player achievements or stats, like total fish caught and
 * highest Snake score. Then triggers relevant advancements.
 */
@EventBusSubscriber(modid = CastingCaving.MOD_ID)
public class AchievementLogic {
    public static final String ACHIEVEMENT_LOGIC = "achievement_logic";

    public static final Codec<AchievementLogic> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("fish_amount").forGetter(AchievementLogic::getFishAmount),
                    Codec.INT.fieldOf("snake_high_score").forGetter(AchievementLogic::getSnakeHighScore)
            ).apply(instance, AchievementLogic::new)
    );

    private int fishAmount;
    private int snakeHighScore; // tracks player's best Snake score

    public AchievementLogic() {
        this.fishAmount = 0;
        this.snakeHighScore = 0;
    }

    public AchievementLogic(int fishAmount, int snakeHighScore) {
        this.fishAmount = fishAmount;
        this.snakeHighScore = snakeHighScore;
    }

    // -------------------------------------------------------
    // Fish‐Tracking
    // -------------------------------------------------------
    public int getFishAmount() {
        return fishAmount;
    }

    public void incrFishAmount(int count) {
        this.fishAmount += count;
    }

    public void incrFishAmount() {
        incrFishAmount(1);
    }

    // -------------------------------------------------------
    // Snake Score‐Tracking
    // -------------------------------------------------------
    public int getSnakeHighScore() {
        return snakeHighScore;
    }

    /**
     * Update local record of the player's best snake score.
     * Returns true if the newScore set a new record.
     */
    public boolean updateSnakeHighScore(int newScore) {
        if (newScore > this.snakeHighScore) {
            this.snakeHighScore = newScore;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------
    // Fishing Event → FISH_AMOUNT_TRIGGER
    // -------------------------------------------------------
    @SubscribeEvent
    public static void fishedEvent(final @NotNull ItemFishedEvent event) {
        // Make sure we're on the server
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) {
            return;
        }

        // Count how many fish items were in the loot
        int fishCount = event.getDrops().stream()
                .filter(stack -> stack.is(ItemTags.FISHES))
                .mapToInt(ItemStack::getCount)
                .sum();
        if (fishCount <= 0) {
            return;
        }

        // Access or init the player's AchievementLogic
        Player player = event.getEntity();
        AchievementLogic logic;
        if (player.hasData(CastingCaving.ACHIEVEMENTS)) {
            logic = player.getData(CastingCaving.ACHIEVEMENTS);
        } else {
            logic = new AchievementLogic();
        }

        // Increment fish count, save updated logic
        logic.incrFishAmount(fishCount);
        player.setData(CastingCaving.ACHIEVEMENTS, logic);

        // Fire the fish-amount advancement trigger
        if (player instanceof ServerPlayer serverPlayer) {
            CastingCaving.FISH_AMOUNT_TRIGGER.get().trigger(serverPlayer);
        }
    }

    // -------------------------------------------------------
    // Custom Helper → SnakeHighScoreTrigger
    // -------------------------------------------------------
    /**
     * Called whenever the player completes or updates their Snake game score.
     * - Updates stored high score in AchievementLogic
     * - Fires the SnakeHighScoreTrigger for advancement checking
     *
     * @param player The player who scored
     * @param newScore The newly achieved Snake score
     */
    public static void recordSnakeScore(ServerPlayer player, int newScore) {
        if (player == null) return;

        AchievementLogic logic;
        if (player.hasData(CastingCaving.ACHIEVEMENTS)) {
            logic = player.getData(CastingCaving.ACHIEVEMENTS);
        } else {
            logic = new AchievementLogic();
        }

        boolean isNewHigh = logic.updateSnakeHighScore(newScore);
        player.setData(CastingCaving.ACHIEVEMENTS, logic);

        CastingCaving.SNAKE_HIGH_SCORE_TRIGGER.get().trigger(player, newScore);
    }
}
