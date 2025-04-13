package net.zam.castingcaving.util.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.component.FishingRodTooltipComponent;
import net.zam.castingcaving.registry.ZAMItems;

import java.util.Arrays;
import java.util.List;

@EventBusSubscriber(modid = CastingCaving.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ZAMClientEvents {
    private static final ResourceLocation INVENTORY_SLOT = ResourceLocation.withDefaultNamespace("textures/gui/sprites/container/slot.png");

    @SubscribeEvent
    public static void registerTooltipFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(FishingRodTooltipComponent.class, FishingRodClientTooltip::new);
    }

    public static class FishingRodClientTooltip implements ClientTooltipComponent {
        private final Item bait;
        private final int baitCount;
        private final int maxBaitSlots;
        private final ItemStack lureStack;
        private final int maxLureSlots;
        private final Item shadowBait;
        private final Item shadowLure;

        // Lists of all possible baits and hooks for shuffling
        private static final List<Item> ALL_BAITS = Arrays.asList(
                ZAMItems.WORM.get(),
                ZAMItems.LEECH.get(),
                ZAMItems.MINNOW.get()
        );
        private static final List<Item> ALL_HOOKS = Arrays.asList(
                ZAMItems.LUCK_HOOK.get(),
                ZAMItems.DOUBLE_HOOK.get(),
                ZAMItems.LURE_HOOK.get(),
                ZAMItems.MELODY_HOOK.get(),
                ZAMItems.TREASURE_HOOK.get()
        );

        // Tick counter for shuffling animation
        private static int tickCounter = 0;
        private static int currentBaitIndex = 0;
        private static int currentHookIndex = 0;
        private static final int TICKS_PER_ITEM = 120; // Cycle every 120 ticks (6 seconds)

        public FishingRodClientTooltip(FishingRodTooltipComponent component) {
            this.bait = component.bait();
            this.baitCount = component.baitCount();
            this.maxBaitSlots = component.maxBaitSlots();
            this.lureStack = component.lureStack();
            this.maxLureSlots = component.maxLureSlots();
            this.shadowBait = component.shadowBait();
            this.shadowLure = component.shadowLure();
        }

        // Update the tick counter and indices for shuffling
        private void updateShuffling() {
            tickCounter++;
            if (tickCounter >= TICKS_PER_ITEM) {
                tickCounter = 0;
                // Update indices to cycle through items
                currentBaitIndex = (currentBaitIndex + 1) % ALL_BAITS.size();
                currentHookIndex = (currentHookIndex + 1) % ALL_HOOKS.size();
            }
        }

        @Override
        public int getHeight() {
            // Height includes the slots (18px)
            return 18; // 16px item + 2px spacing
        }

        @Override
        public int getWidth(Font font) {
            // Width includes the slots (18px each)
            int slots = maxBaitSlots + maxLureSlots; // Total slots based on rod capacity
            return slots * 18; // 18px per slot (16px item + 2px spacing)
        }

        @Override
        public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
            // Update the shuffling indices
            updateShuffling();

            Minecraft mc = Minecraft.getInstance();
            int slotSize = 18;
            int currentX = x;

            // Render bait slot (without shadow effect)
            if (maxBaitSlots > 0) {
                guiGraphics.blit(INVENTORY_SLOT, currentX, y, 0, 0, slotSize, slotSize, slotSize, slotSize);
                if (bait != null) {
                    // Render the actual bait if present
                    ItemStack baitStack = new ItemStack(bait, baitCount);
                    guiGraphics.renderItem(baitStack, currentX + 1, y + 1);
                    guiGraphics.renderItemDecorations(font, baitStack, currentX + 1, y + 1);
                } else {
                    // Render the cycling bait item (without shadow effect)
                    ItemStack cyclingBaitStack = new ItemStack(ALL_BAITS.get(currentBaitIndex));
                    guiGraphics.renderItem(cyclingBaitStack, currentX + 1, y + 1);
                }
                currentX += slotSize;
            }

            // Render lure slot (without shadow effect)
            if (maxLureSlots > 0) {
                guiGraphics.blit(INVENTORY_SLOT, currentX, y, 0, 0, slotSize, slotSize, slotSize, slotSize);
                if (lureStack != null) {
                    // Render the actual lure if present
                    guiGraphics.renderItem(lureStack, currentX + 1, y + 1);
                    guiGraphics.renderItemDecorations(font, lureStack, currentX + 1, y + 1);
                } else {
                    // Render the cycling lure item (without shadow effect)
                    ItemStack cyclingLureStack = new ItemStack(ALL_HOOKS.get(currentHookIndex));
                    guiGraphics.renderItem(cyclingLureStack, currentX + 1, y + 1);
                }
                currentX += slotSize;
            }
        }
    }
}