package net.zam.castingcaving.common.item.fishing;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.zam.castingcaving.common.component.FishingRodTooltipComponent;
import net.zam.castingcaving.registry.ZAMDataComponents;

import java.util.List;
import java.util.Optional;

public abstract class ZAMFishingRodItem extends FishingRodItem {
    public ZAMFishingRodItem(Properties properties) {
        super(properties);
    }

    public abstract boolean supportsBait();
    public abstract boolean supportsLure();
    public abstract boolean supportsLavaFishing();
    public abstract boolean hasBonusLoot();
    public abstract int getMaxBaitStackSize();
    public abstract int getMaxLureCount();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack rodStack = player.getItemInHand(hand);
        return super.use(level, player, hand);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack rodStack, Slot slot, ClickAction action, Player player) {
        if (rodStack.getCount() != 1 || action != ClickAction.SECONDARY) return false;

        ItemStack slotStack = slot.getItem();
        if (slotStack.isEmpty()) {
            ZAMDataComponents.BaitData baitData = rodStack.get(ZAMDataComponents.ATTACHED_BAIT.get());
            ZAMDataComponents.LureData lureData = rodStack.get(ZAMDataComponents.ATTACHED_LURE.get());

            // Remove only one at a time, prioritizing lure
            if (lureData != null) {
                Item lureItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(lureData.id()));
                ItemStack lureStack = new ItemStack(lureItem);
                // Set the lure's damage value to reflect its current durability
                lureStack.set(DataComponents.DAMAGE, lureData.durability());
                slot.safeInsert(lureStack);
                rodStack.remove(ZAMDataComponents.ATTACHED_LURE.get());
                playRemoveOneSound(player);
                return true;
            } else if (baitData != null && baitData.count() > 0) {
                Item baitItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(baitData.id()));
                ItemStack baitStack = new ItemStack(baitItem, baitData.count());
                slot.safeInsert(baitStack);
                rodStack.remove(ZAMDataComponents.ATTACHED_BAIT.get());
                playRemoveOneSound(player);
                return true;
            }
            return false;
        } else if (canAttachItem(rodStack, slotStack)) {
            int added = attachBulkItems(rodStack, slotStack);
            if (added > 0) {
                slotStack.shrink(added);
                playInsertSound(player);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack rodStack, ItemStack otherStack, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (rodStack.getCount() != 1 || action != ClickAction.SECONDARY || !slot.allowModification(player)) return false;

        if (otherStack.isEmpty()) {
            ZAMDataComponents.BaitData baitData = rodStack.get(ZAMDataComponents.ATTACHED_BAIT.get());
            ZAMDataComponents.LureData lureData = rodStack.get(ZAMDataComponents.ATTACHED_LURE.get());

            // Remove only one at a time, prioritizing lure
            if (lureData != null) {
                Item lureItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(lureData.id()));
                ItemStack lureStack = new ItemStack(lureItem);
                // Set the lure's damage value to reflect its current durability
                lureStack.set(DataComponents.DAMAGE, lureData.durability());
                access.set(lureStack);
                rodStack.remove(ZAMDataComponents.ATTACHED_LURE.get());
                playRemoveOneSound(player);
                return true;
            } else if (baitData != null && baitData.count() > 0) {
                Item baitItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(baitData.id()));
                ItemStack baitStack = new ItemStack(baitItem, baitData.count());
                access.set(baitStack);
                rodStack.remove(ZAMDataComponents.ATTACHED_BAIT.get());
                playRemoveOneSound(player);
                return true;
            }
            return false;
        } else if (canAttachItem(rodStack, otherStack)) {
            int added = attachBulkItems(rodStack, otherStack);
            if (added > 0) {
                otherStack.shrink(added);
                playInsertSound(player);
                return true;
            }
        }

        return false;
    }

    private boolean canAttachItem(ItemStack rod, ItemStack attachment) {
        if (attachment.isEmpty()) return false;

        if (attachment.getItem() instanceof BaitItem && this.supportsBait()) {
            ZAMDataComponents.BaitData currentBait = rod.get(ZAMDataComponents.ATTACHED_BAIT.get());
            String attachmentId = BuiltInRegistries.ITEM.getKey(attachment.getItem()).toString();
            int maxBait = getMaxBaitStackSize();
            if (currentBait == null) return maxBait > 0;
            return currentBait.id().equals(attachmentId) && currentBait.count() < maxBait;
        }

        if (attachment.getItem() instanceof LureItem && this.supportsLure()) {
            ZAMDataComponents.LureData currentLure = rod.get(ZAMDataComponents.ATTACHED_LURE.get());
            return currentLure == null && getMaxLureCount() > 0;
        }

        return false;
    }

    private int attachBulkItems(ItemStack rod, ItemStack attachment) {
        if (attachment.getItem() instanceof BaitItem && this.supportsBait()) {
            String baitId = BuiltInRegistries.ITEM.getKey(attachment.getItem()).toString();
            ZAMDataComponents.BaitData currentBait = rod.get(ZAMDataComponents.ATTACHED_BAIT.get());
            int currentCount = currentBait != null ? currentBait.count() : 0;
            int maxBait = getMaxBaitStackSize();
            int availableSpace = maxBait - currentCount;
            int toAdd = Math.min(attachment.getCount(), availableSpace);
            if (toAdd > 0) {
                rod.set(ZAMDataComponents.ATTACHED_BAIT.get(), new ZAMDataComponents.BaitData(baitId, currentCount + toAdd));
                return toAdd;
            }
        } else if (attachment.getItem() instanceof LureItem && this.supportsLure()) {
            ZAMDataComponents.LureData currentLure = rod.get(ZAMDataComponents.ATTACHED_LURE.get());
            if (currentLure == null && getMaxLureCount() > 0) {
                String lureId = BuiltInRegistries.ITEM.getKey(attachment.getItem()).toString();
                // Use the lure's current damage value (instead of resetting to 0)
                int lureDamage = attachment.get(DataComponents.DAMAGE);
                rod.set(ZAMDataComponents.ATTACHED_LURE.get(), new ZAMDataComponents.LureData(lureId, lureDamage));
                return 1;
            }
        }
        return 0;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(FishingRodTooltipComponent.fromStack(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        // Add the lure durability text to the tooltip if advanced tooltips are enabled
        if (flag.isAdvanced()) {
            ZAMDataComponents.LureData lureData = stack.get(ZAMDataComponents.ATTACHED_LURE.get());
            if (lureData != null) {
                Item lureItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(lureData.id()));
                ItemStack lureStack = new ItemStack(lureItem);
                lureStack.set(DataComponents.DAMAGE, lureData.durability());
                int maxDurability = lureStack.getMaxDamage();
                int remainingDurability = maxDurability - lureStack.get(DataComponents.DAMAGE);
                tooltip.add(Component.literal("Lure Durability: " + remainingDurability + "/" + maxDurability));
            }
        }
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }
}