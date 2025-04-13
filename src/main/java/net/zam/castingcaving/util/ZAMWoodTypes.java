package net.zam.castingcaving.util;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.zam.castingcaving.CastingCaving;

public class ZAMWoodTypes {
    public static final WoodType MARINE = WoodType.register(new WoodType(CastingCaving.MOD_ID + ":marine", BlockSetType.CHERRY));
}
