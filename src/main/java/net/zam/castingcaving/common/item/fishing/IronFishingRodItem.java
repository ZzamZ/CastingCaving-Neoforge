package net.zam.castingcaving.common.item.fishing;

import net.minecraft.world.item.Item;

public class IronFishingRodItem extends ZAMFishingRodItem {
    public IronFishingRodItem() {
        super(new Properties().durability(128));
    }

    @Override public boolean supportsBait() { return true; }
    @Override public boolean supportsLure() { return false; }
    @Override public boolean supportsLavaFishing() { return false; }
    @Override public boolean hasBonusLoot() { return false; }
    @Override public int getMaxBaitStackSize() { return 64; }
    @Override public int getMaxLureCount() { return 0; }
}