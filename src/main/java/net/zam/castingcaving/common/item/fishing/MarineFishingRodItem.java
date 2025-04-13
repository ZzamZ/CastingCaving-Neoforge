package net.zam.castingcaving.common.item.fishing;

import net.minecraft.world.item.Item;

public class MarineFishingRodItem extends ZAMFishingRodItem {
    public MarineFishingRodItem() {
        super(new Properties().durability(384));
    }

    @Override public boolean supportsBait() { return true; }
    @Override public boolean supportsLure() { return true; }
    @Override public boolean supportsLavaFishing() { return true; }
    @Override public boolean hasBonusLoot() { return true; }
    @Override public int getMaxBaitStackSize() { return 64; }
    @Override public int getMaxLureCount() { return 1; }
}