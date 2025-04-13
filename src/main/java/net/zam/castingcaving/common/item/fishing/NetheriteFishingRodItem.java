package net.zam.castingcaving.common.item.fishing;

import net.minecraft.world.item.Item;

public class NetheriteFishingRodItem extends ZAMFishingRodItem {
    public NetheriteFishingRodItem() {
        super(new Properties().durability(512).fireResistant());
    }

    @Override public boolean supportsBait() { return true; }
    @Override public boolean supportsLure() { return true; }
    @Override public boolean supportsLavaFishing() { return true; }
    @Override public boolean hasBonusLoot() { return false; }
    @Override public int getMaxBaitStackSize() { return 64; }
    @Override public int getMaxLureCount() { return 1; }
}