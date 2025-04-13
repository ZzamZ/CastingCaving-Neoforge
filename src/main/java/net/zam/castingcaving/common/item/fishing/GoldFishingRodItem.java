package net.zam.castingcaving.common.item.fishing;

public class GoldFishingRodItem extends ZAMFishingRodItem {
    public GoldFishingRodItem() {
        super(new Properties().durability(64));
    }

    @Override public boolean supportsBait() { return false; }
    @Override public boolean supportsLure() { return true; }
    @Override public boolean supportsLavaFishing() { return false; }
    @Override public boolean hasBonusLoot() { return false; }
    @Override public int getMaxBaitStackSize() { return 0; }
    @Override public int getMaxLureCount() { return 1; }
}