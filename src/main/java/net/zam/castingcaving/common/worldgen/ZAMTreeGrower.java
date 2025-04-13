package net.zam.castingcaving.common.worldgen;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.zam.castingcaving.CastingCaving;

import java.util.Optional;

public class ZAMTreeGrower {
    public static final TreeGrower MARINE = new TreeGrower(CastingCaving.MOD_ID + ":marine",
            Optional.empty(), Optional.of(ZAMConfiguredFeatures.MARINE_KEY), Optional.empty());
}
