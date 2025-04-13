package net.zam.castingcaving.common.component;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.zam.castingcaving.common.item.fishing.ZAMFishingRodItem;
import net.zam.castingcaving.registry.ZAMDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.zam.castingcaving.registry.ZAMItems;

public record FishingRodTooltipComponent(
        Item bait,
        int baitCount,
        int maxBaitSlots,
        ItemStack lureStack,
        int maxLureSlots,
        Item shadowBait,
        Item shadowLure
) implements TooltipComponent {
    public static FishingRodTooltipComponent fromStack(ItemStack stack) {
        // Ensure the stack is a ZAMFishingRodItem to get slot capacities
        if (!(stack.getItem() instanceof ZAMFishingRodItem rodItem)) {
            return new FishingRodTooltipComponent(null, 0, 0, null, 0, null, null);
        }

        ZAMDataComponents.BaitData baitData = stack.get(ZAMDataComponents.ATTACHED_BAIT.get());
        ZAMDataComponents.LureData lureData = stack.get(ZAMDataComponents.ATTACHED_LURE.get());
        Item bait = baitData != null ? BuiltInRegistries.ITEM.get(ResourceLocation.parse(baitData.id())) : null;
        int baitCount = baitData != null ? baitData.count() : 0;
        int maxBaitSlots = rodItem.getMaxBaitStackSize() > 0 ? 1 : 0; // 1 slot if bait is supported
        ItemStack lureStack = null;
        if (lureData != null) {
            Item lureItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(lureData.id()));
            lureStack = new ItemStack(lureItem);
            lureStack.set(DataComponents.DAMAGE, lureData.durability());
        }
        int maxLureSlots = rodItem.getMaxLureCount(); // Typically 1 for lures

        // Shadow items for empty slots (worm for bait, lure_hook for lure)
        Item shadowBait = ZAMItems.WORM.get();
        Item shadowLure = ZAMItems.LURE_HOOK.get();

        return new FishingRodTooltipComponent(bait, baitCount, maxBaitSlots, lureStack, maxLureSlots, shadowBait, shadowLure);
    }
}