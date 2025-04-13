package net.zam.castingcaving.common.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.registry.ZAMItems;

import java.util.List;

public class PeridotHammerItem extends Item {

    public PeridotHammerItem(Properties properties) {
        super(properties);
    }

    /**
     * Called when right-clicking a block with the hammer.
     * Only triggers if:
     * - The block is an anvil.
     * - The hammer is in the MAIN_HAND.
     * - The off-hand contains a valid geode.
     *
     * If conditions are met, the hammer swings, the geode is consumed,
     * loot is generated, a sound is played, and the hammer loses 1 durability.
     * The interaction is then fully consumed to prevent the anvil GUI from opening.
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        var pos = context.getClickedPos();

        // Only trigger on an anvil and when used from the main hand.
        if (level.getBlockState(pos).getBlock() == Blocks.ANVIL && context.getHand() == InteractionHand.MAIN_HAND) {
            Player player = context.getPlayer();
            if (player != null) {
                // Ensure the MAIN_HAND holds the hammer.
                ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (mainHandStack.getItem() != this) {
                    return InteractionResult.PASS;
                }
                // The off-hand must contain a valid geode.
                ItemStack offhandStack = player.getItemInHand(InteractionHand.OFF_HAND);
                if (!offhandStack.isEmpty()) {
                    ResourceLocation lootTableId = getLootTableForGeode(offhandStack);
                    if (lootTableId != null) {
                        // Swing animation on client side.
                        if (level.isClientSide) {
                            player.swing(context.getHand(), true);
                        }
                        // On server side, perform the action.
                        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
                            // Consume one geode.
                            offhandStack.shrink(1);
                            // Generate loot.
                            generateLoot(serverLevel, player, mainHandStack, lootTableId);
                            // Play sound at the anvil.
                            level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                            // Damage the hammer by 1 point.
                            context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                        }
                        // Return a fully consumed action to prevent the anvil GUI from opening.
                        return InteractionResult.sidedSuccess(level.isClientSide());
                    }
                }
            }
            return InteractionResult.PASS;
        }
        return super.useOn(context);
    }

    /**
     * Returns the loot table ResourceLocation for the given geode ItemStack.
     */
    private ResourceLocation getLootTableForGeode(ItemStack stack) {
        if (stack.getItem() == ZAMItems.GEODE.get()) {
            return ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "geode/geode_loot");
        } else if (stack.getItem() == ZAMItems.LAVA_GEODE.get()) {
            return ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "geode/lava_geode_loot");
        } else if (stack.getItem() == ZAMItems.MOSSY_GEODE.get()) {
            return ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "geode/mossy_geode_loot");
        } else if (stack.getItem() == ZAMItems.OCEAN_GEODE.get()) {
            return ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "geode/ocean_geode_loot");
        } else if (stack.getItem() == ZAMItems.ECLIPSE_GEODE.get()) {
            return ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID, "geode/eclipse_geode_loot");
        }
        return null;
    }

    /**
     * Generates loot from the specified loot table and gives it to the player
     * (or drops it if the inventory is full).
     */
    private void generateLoot(ServerLevel serverLevel, Player player, ItemStack tool, ResourceLocation lootTableId) {
        LootTable lootTable = serverLevel.getServer().registryAccess()
                .registryOrThrow(Registries.LOOT_TABLE)
                .get(lootTableId);

        LootParams lootParams = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .withParameter(LootContextParams.TOOL, tool)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                .create(LootContextParamSets.GIFT);

        List<ItemStack> generatedLoot = lootTable.getRandomItems(lootParams);

        for (ItemStack dropStack : generatedLoot) {
            if (!player.addItem(dropStack)) {
                player.drop(dropStack, false);
            }
        }
    }
}
