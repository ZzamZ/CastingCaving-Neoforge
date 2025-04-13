package net.zam.castingcaving;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ZAMConfig {
    public static final Common COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final LostOptions LOST_OPTIONS = new LostOptions(BUILDER);
    public static final BasicOptions BASIC_OPTIONS = new BasicOptions(BUILDER);

    static {
        Pair<Common, ModConfigSpec> commonSpecPair = BUILDER.configure(Common::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }

    public static class Common {
        public final ModConfigSpec.BooleanValue fadedShader;

        public final ModConfigSpec.BooleanValue addLostBountyToLoot;

        Common(ModConfigSpec.Builder builder) {
            builder.comment("General settings")
                    .push("General");

            fadedShader = builder
                    .comment("Enable or disable the faded shader")
                    .define("fadedShader", true);

            builder.pop();

            builder.comment("Lost options")
                    .push("LostOptions");

            addLostBountyToLoot = builder
                    .comment("Should Lost bounty be added as fishing loot? Very rare.")
                    .define("addLostBountyToLoot", true);

            builder.pop();
        }
    }

    public static class LostOptions {
        static final String LOST_OPTIONS = "neptunium options";
        public ModConfigSpec.BooleanValue addLostBountyToLoot;

        LostOptions(ModConfigSpec.Builder builder) {
            builder.push(LOST_OPTIONS);
            addLostBountyToLoot = builder.comment("Should Lost bounty be added as fishing loot? Very rare.").define("Add Lost Bounty as loot?", true);
            builder.pop();
        }
    }

    public static class BasicOptions {
        static final String BASIC_OPTIONS = "basic options";
        public ModConfigSpec.BooleanValue randomWeight;
        public ModConfigSpec.BooleanValue compostableFish;
        public ModConfigSpec.BooleanValue aqFishToBreedCats;
        public ModConfigSpec.IntValue messageInABottleAmount;
        public ModConfigSpec.BooleanValue debugMode;
        public ModConfigSpec.BooleanValue showFilletRecipesInJEI;

        BasicOptions(ModConfigSpec.Builder builder) {
            builder.push(BASIC_OPTIONS);
            randomWeight = builder.define("Enable weight for fish? Useful for fishing competitions", false);
            compostableFish = builder.define("Should fish be added as compostables for the composter/worm farm? (Based on fish, or weight if enabled)", true);
            aqFishToBreedCats = builder.define("Should Casting & Caving fish be able to be used to breed cats & ocelots?", true);
            debugMode = builder.define("Enable debug mode? (Enables additional logging)", false);
            messageInABottleAmount = builder.defineInRange("Amount of Message In A Bottle messages. Used to add additional custom messages", 29, 0, 255);
            showFilletRecipesInJEI = builder.define("Show Fillet recipes in JEI?", true);
            builder.pop();
        }
    }
}
