package net.zam.castingcaving.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.zam.castingcaving.common.item.fishing.ZAMFishingRodItem;
import net.zam.castingcaving.registry.ZAMDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @Shadow private int timeUntilLured;
    @Shadow private int timeUntilHooked;

    @Shadow public abstract Player getPlayerOwner();

    private boolean hasAppliedBaitReduction = false;
    private boolean hasPlayedMelodyBiteSound = false;
    private int melodySoundCooldown = 0;

    // Apply bait speed reduction and melody_hook sound
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void applyBaitSpeedBoost(CallbackInfo ci) {
        FishingHook hook = (FishingHook)(Object)this;
        Player player = getPlayerOwner();
        if (player != null) {
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            ItemStack rod = (mainHand.getItem() instanceof ZAMFishingRodItem) ? mainHand : offHand;

            // Apply bait speed reduction
            if (!hasAppliedBaitReduction && rod.getItem() instanceof ZAMFishingRodItem customRod && customRod.supportsBait()) {
                ZAMDataComponents.BaitData baitData = rod.get(ZAMDataComponents.ATTACHED_BAIT.get());
                if (baitData != null && baitData.count() > 0) {
                    if (this.timeUntilLured >= 100 && this.timeUntilLured <= 600) {
                        int reduction = switch (baitData.id()) {
                            case "castingcaving:leech" -> 60;  // 3 seconds
                            case "castingcaving:worm" -> 120;  // 6 seconds
                            case "castingcaving:minnow" -> 180; // 9 seconds
                            default -> 0;
                        };
                        if (reduction > 0) {
                            int oldTime = this.timeUntilLured;
                            this.timeUntilLured = Math.max(0, this.timeUntilLured - reduction);
                            hasAppliedBaitReduction = true;
                            System.out.println("Applied one-time bait speed boost: " + baitData.id() + ", reduced timeUntilLured from " + oldTime + " to " + this.timeUntilLured);
                        }
                    }
                }
            }

            // Play melody_hook sound when close to biting or on bite
            if (rod.getItem() instanceof ZAMFishingRodItem customRod && customRod.supportsLure()) {
                ZAMDataComponents.LureData lureData = rod.get(ZAMDataComponents.ATTACHED_LURE.get());
                if (lureData != null && "castingcaving:melody_hook".equals(lureData.id())) {
                    // Play "ding ding ding" while close to biting (timeUntilLured <= 20)
                    if (this.timeUntilLured > 0 && this.timeUntilLured <= 20) {
                        if (melodySoundCooldown <= 0) {
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS, 1.0f, 1.0f); // Normal pitch
                            melodySoundCooldown = 10; // Play every 0.5 seconds (10 ticks)
                            System.out.println("melody_hook played close-to-bite sound (timeUntilLured: " + this.timeUntilLured + ")");
                        }
                        melodySoundCooldown--;
                    }
                    // Play higher-pitched sound when the fish bites (timeUntilLured == 0 and timeUntilHooked > 0)
                    if (!hasPlayedMelodyBiteSound && this.timeUntilLured == 0 && this.timeUntilHooked > 0) {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS, 1.0f, 1.5f); // Higher pitch
                        hasPlayedMelodyBiteSound = true;
                        System.out.println("melody_hook played bite sound");
                    }
                }
            }
        }
    }

    // Increase XP orbs with lure_hook
    @ModifyVariable(
            method = "retrieve(Lnet/minecraft/world/item/ItemStack;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ExperienceOrb;<init>(Lnet/minecraft/world/level/Level;DDDI)V"
            ),
            ordinal = 0
    )
    private int increaseExperienceOrbs(int originalXp) {
        FishingHook hook = (FishingHook)(Object)this;
        Player player = getPlayerOwner();
        if (player != null) {
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            ItemStack rod = (mainHand.getItem() instanceof ZAMFishingRodItem) ? mainHand : offHand;

            if (rod.getItem() instanceof ZAMFishingRodItem customRod && customRod.supportsLure()) {
                ZAMDataComponents.LureData lureData = rod.get(ZAMDataComponents.ATTACHED_LURE.get());
                if (lureData != null && "castingcaving:lure_hook".equals(lureData.id())) {
                    int newXp = originalXp * 2; // Double the XP (1-6 becomes 2-12)
                    System.out.println("Increased XP with lure_hook: original " + originalXp + ", new " + newXp);
                    return newXp;
                }
            }
        }
        return originalXp;
    }

    // Apply durability damage to the lure when a fish is caught, respecting Unbreaking
    @Inject(
            method = "retrieve(Lnet/minecraft/world/item/ItemStack;)I",
            at = @At("HEAD")
    )
    private void applyLureDurabilityDamage(ItemStack rod, CallbackInfoReturnable<Integer> cir) {
        FishingHook hook = (FishingHook)(Object)this;
        Player player = getPlayerOwner();
        if (player != null) {
            if (rod.getItem() instanceof ZAMFishingRodItem customRod && customRod.supportsLure()) {
                ZAMDataComponents.LureData lureData = rod.get(ZAMDataComponents.ATTACHED_LURE.get());
                if (lureData != null) {
                    Item lureItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(lureData.id()));
                    ItemStack lureStack = new ItemStack(lureItem);
                    lureStack.setDamageValue(lureData.durability());

                    // Check Unbreaking enchantment on the rod
                    int unbreakingLevel = EnchantmentHelper.getItemEnchantmentLevel(player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.UNBREAKING), rod);
                    boolean shouldTakeDamage = true;
                    if (unbreakingLevel > 0) {
                        // Unbreaking reduces the chance of taking durability damage
                        // Chance to not take damage = unbreakingLevel / (unbreakingLevel + 1)
                        float chanceToNotTakeDamage = (float) unbreakingLevel / (unbreakingLevel + 1);
                        if (player.getRandom().nextFloat() < chanceToNotTakeDamage) {
                            shouldTakeDamage = false;
                        }
                    }

                    if (shouldTakeDamage) {
                        // Damage the lure by 1 (increases damage value)
                        lureStack.setDamageValue(lureStack.getDamageValue() + 1);
                        System.out.println("Lure " + lureData.id() + " damaged on catch, new damage value: " + lureStack.getDamageValue());
                        // If the lure is broken (damage >= max damage), remove it from the rod
                        if (lureStack.getDamageValue() >= lureStack.getMaxDamage()) {
                            rod.remove(ZAMDataComponents.ATTACHED_LURE.get());
                            System.out.println("Lure " + lureData.id() + " broke due to durability reaching max");
                        } else {
                            // Update the lure's durability in the rod's data component
                            rod.set(ZAMDataComponents.ATTACHED_LURE.get(), new ZAMDataComponents.LureData(lureData.id(), lureStack.getDamageValue()));
                        }
                    }
                }
            }
        }
    }

    // Reset flags when the hook is retrieved
    @Inject(method = "retrieve", at = @At("HEAD"))
    private void resetBaitReduction(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        this.hasAppliedBaitReduction = false;
        this.hasPlayedMelodyBiteSound = false;
        this.melodySoundCooldown = 0;
    }

    // Reset flags when the hook is removed (discarded)
    @Inject(method = "remove(Lnet/minecraft/world/entity/Entity$RemovalReason;)V", at = @At("HEAD"))
    private void resetBaitReductionOnRemove(Entity.RemovalReason reason, CallbackInfo ci) {
        this.hasAppliedBaitReduction = false;
        this.hasPlayedMelodyBiteSound = false;
        this.melodySoundCooldown = 0;
    }
}