package net.zam.castingcaving.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.advancement.FishAmountTrigger;
import net.zam.castingcaving.common.advancement.SnakeHighScoreTrigger;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Generates your mod's advancements, including fish-amount achievements
 * and a Snake high-score advancement that grants loot.
 */
public class ZAMAdvancementProvider extends AdvancementProvider {
    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     */
    public ZAMAdvancementProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, registries, existingFileHelper, List.of(new ZAMAdvancementGenerator()));
    }

    private static final class ZAMAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(
                HolderLookup.@NotNull Provider registries,
                @NotNull Consumer<AdvancementHolder> saver,
                @NotNull ExistingFileHelper existingFileHelper
        ) {
            // ----------------------------------------------------------------
            //  1) FIRST FISH
            // ----------------------------------------------------------------
            AdvancementHolder firstFish = Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("minecraft:story/root"))
                    .display(
                            new ItemStack(Items.TROPICAL_FISH),
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_name.first_fish"),
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_description.first_fish"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    )
                    .rewards(AdvancementRewards.Builder.experience(5))
                    .addCriterion(
                            // The name of the criterion. (Doesn't affect the final ID.)
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "first_fish").toString(),
                            FishAmountTrigger.TriggerInstance.withAmount(1)
                    )
                    .requirements(
                            AdvancementRequirements.allOf(
                                    List.of(ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "first_fish").toString())
                            )
                    )
                    .save(
                            saver,
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "first_fish"),
                            existingFileHelper
                    );

            // ----------------------------------------------------------------
            //  2) FIVE FISH
            // ----------------------------------------------------------------
            AdvancementHolder fiveFish = Advancement.Builder.advancement()
                    .parent(firstFish)
                    .display(
                            new ItemStack(Items.PUFFERFISH),
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_name.five_fishes"),
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_description.five_fishes"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    )
                    .rewards(AdvancementRewards.Builder.experience(20))
                    .addCriterion(
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "five_fishes").toString(),
                            FishAmountTrigger.TriggerInstance.withAmount(5)
                    )
                    .requirements(
                            AdvancementRequirements.allOf(
                                    List.of(ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "five_fishes").toString())
                            )
                    )
                    .save(
                            saver,
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "five_fishes"),
                            existingFileHelper
                    );

            // ----------------------------------------------------------------
            //  3) PERSISTENT FISHER (25 fish)
            // ----------------------------------------------------------------
            AdvancementHolder persistent = Advancement.Builder.advancement()
                    .parent(fiveFish)
                    .display(
                            new ItemStack(Items.COD),
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_name.persistent_fisher"),
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_description.persistent_fisher"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    )
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .addCriterion(
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "persistent_fisher").toString(),
                            FishAmountTrigger.TriggerInstance.withAmount(25)
                    )
                    .requirements(
                            AdvancementRequirements.allOf(
                                    List.of(ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "persistent_fisher").toString())
                            )
                    )
                    .save(
                            saver,
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "persistent_fisher"),
                            existingFileHelper
                    );

            // ----------------------------------------------------------------
            //  4) ETERNAL FISHER (100 fish)
            // ----------------------------------------------------------------
            AdvancementHolder eternal = Advancement.Builder.advancement()
                    .parent(persistent)
                    .display(
                            new ItemStack(Items.SALMON),
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_name.eternal_fisher"),
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_description.eternal_fisher"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    )
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .addCriterion(
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "eternal_fisher").toString(),
                            FishAmountTrigger.TriggerInstance.withAmount(100)
                    )
                    .requirements(
                            AdvancementRequirements.allOf(
                                    List.of(ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "eternal_fisher").toString())
                            )
                    )
                    .save(
                            saver,
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "eternal_fisher"),
                            existingFileHelper
                    );

            // ----------------------------------------------------------------
            //  5) SNAKE SCORE (score >= 20), awarding an item from a loot table
            // ----------------------------------------------------------------

            // Create a ResourceLocation referencing data/castingcaving/loot_tables/advancement_rewards/legendary.json
            ResourceLocation snakeScore20LootRL =
                    ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "advancement_rewards/legendary");

            // Wrap it as a ResourceKey for a LootTable
            ResourceKey<LootTable> snakeScore20LootKey =
                    ResourceKey.create(Registries.LOOT_TABLE, snakeScore20LootRL);

            AdvancementHolder snakeScore20 = Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("minecraft:story/root"))
                    .display(
                            new ItemStack(Items.EXPERIENCE_BOTTLE), // Icon for the advancement
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_name.snake_20"),
                            Component.translatable(CastingCaving.MOD_ID + ".gui.achievement_description.snake_20"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .rewards(AdvancementRewards.Builder.loot(snakeScore20LootKey))
                    .addCriterion(
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "snake_20").toString(),
                            SnakeHighScoreTrigger.TriggerInstance.snakeHighScore(MinMaxBounds.Ints.atLeast(100))
                    )
                    .requirements(
                            AdvancementRequirements.allOf(
                                    List.of(ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "snake_20").toString())
                            )
                    )
                    .save(
                            saver,
                            // Final ID for this advancement: "castingcaving:snake_score_20"
                            ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "snake_high_score"),
                            existingFileHelper
                    );
        }
    }
}
