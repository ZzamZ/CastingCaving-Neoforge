package net.zam.castingcaving.util;

import net.minecraft.nbt.NbtAccounter;

public interface IEntityDataSaver {
    NbtAccounter getPersistentData();
}