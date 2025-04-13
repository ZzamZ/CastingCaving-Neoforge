package net.zam.castingcaving.common.client.item.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.component.FishingRodTooltipComponent;
import net.zam.castingcaving.registry.ZAMItems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishingRodTooltipRenderer implements ClientTooltipComponent {
    private static final ResourceLocation INVENTORY_SLOT = ResourceLocation.withDefaultNamespace("textures/gui/sprites/container/slot.png");

    private final FishingRodTooltipComponent component;

    // Lists of original items for cycling
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

    // Map of normal items to their template texture paths
    private static final Map<Item, ResourceLocation> TEMPLATE_TEXTURES = new HashMap<>();

    static {
        // Map bait items to their template textures
        TEMPLATE_TEXTURES.put(ZAMItems.WORM.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "item/template_worm"));
        TEMPLATE_TEXTURES.put(ZAMItems.LEECH.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "item/template_leech"));
        TEMPLATE_TEXTURES.put(ZAMItems.MINNOW.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "item/template_minnow"));
        // Map hook items to their template textures
        TEMPLATE_TEXTURES.put(ZAMItems.LUCK_HOOK.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "item/template_luck_hook"));
        TEMPLATE_TEXTURES.put(ZAMItems.DOUBLE_HOOK.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "item/template_double_hook"));
        TEMPLATE_TEXTURES.put(ZAMItems.LURE_HOOK.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "item/template_lure_hook"));
        TEMPLATE_TEXTURES.put(ZAMItems.MELODY_HOOK.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "item/template_melody_hook"));
        TEMPLATE_TEXTURES.put(ZAMItems.TREASURE_HOOK.get(), ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "item/template_treasure_hook"));
    }

    // Tick counter for shuffling animation
    private static int tickCounter = 0;
    private static int currentBaitIndex = 0;
    private static int currentHookIndex = 0;
    private static final int TICKS_PER_ITEM = 120; // Cycle every 120 ticks (6 seconds)

    public FishingRodTooltipRenderer(FishingRodTooltipComponent component) {
        this.component = component;
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
        // Height depends on whether bait and/or lure slots are present, plus 1px gap
        int height = 0;
        if (component.maxBaitSlots() > 0) {
            height += 18; // Space for bait slot
        }
        if (component.maxLureSlots() > 0) {
            height += 18; // Space for lure slot
        }
        // Add 1px gap to shift text down
        height += 1;
        return height;
    }

    @Override
    public int getWidth(Font font) {
        // Width includes the slots (18px each)
        int slots = component.maxBaitSlots() + component.maxLureSlots();
        return slots * 18; // 18px per slot (16px item + 2px spacing)
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        // Update the shuffling indices
        updateShuffling();

        Minecraft mc = Minecraft.getInstance();
        int slotSize = 18;
        int currentX = x;

        // Render bait slot (with cycling placeholder if empty)
        if (component.maxBaitSlots() > 0) {
            guiGraphics.blit(INVENTORY_SLOT, currentX, y, 0, 0, slotSize, slotSize, slotSize, slotSize);
            if (component.bait() != null) {
                // Render the actual bait if present
                ItemStack baitStack = new ItemStack(component.bait(), component.baitCount());
                guiGraphics.renderItem(baitStack, currentX + 1, y + 1);
                guiGraphics.renderItemDecorations(font, baitStack, currentX + 1, y + 1, component.baitCount() > 1 ? String.valueOf(component.baitCount()) : null);
            } else {
                // Render the cycling bait placeholder with custom texture
                Item baitItem = ALL_BAITS.get(currentBaitIndex);
                ResourceLocation templateTexture = TEMPLATE_TEXTURES.get(baitItem);
                if (templateTexture != null) {
                    guiGraphics.blit(templateTexture, currentX + 1, y + 1, 0, 0, 16, 16, 16, 16);
                } else {
                    // Fallback to normal rendering if texture not found
                    ItemStack cyclingBaitStack = new ItemStack(baitItem);
                    guiGraphics.renderItem(cyclingBaitStack, currentX + 1, y + 1);
                }
            }
            currentX += 18;
        }

        // Render lure slot (with cycling placeholder if empty)
        if (component.maxLureSlots() > 0) {
            guiGraphics.blit(INVENTORY_SLOT, currentX, y, 0, 0, slotSize, slotSize, slotSize, slotSize);
            if (component.lureStack() != null) {
                // Render the actual lure if present
                guiGraphics.renderItem(component.lureStack(), currentX + 1, y + 1);
                guiGraphics.renderItemDecorations(font, component.lureStack(), currentX + 1, y + 1);
            } else {
                // Render the cycling lure placeholder with custom texture
                Item hookItem = ALL_HOOKS.get(currentHookIndex);
                ResourceLocation templateTexture = TEMPLATE_TEXTURES.get(hookItem);
                if (templateTexture != null) {
                    guiGraphics.blit(templateTexture, currentX + 1, y + 1, 0, 0, 16, 16, 16, 16);
                } else {
                    // Fallback to normal rendering if texture not found
                    ItemStack cyclingLureStack = new ItemStack(hookItem);
                    guiGraphics.renderItem(cyclingLureStack, currentX + 1, y + 1);
                }
            }
            currentX += 18;
        }
    }
}