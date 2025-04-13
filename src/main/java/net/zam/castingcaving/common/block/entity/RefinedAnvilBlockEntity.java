package net.zam.castingcaving.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.zam.castingcaving.registry.ZAMBlockEntities;
import net.zam.castingcaving.registry.ZAMItems;

import java.util.List;

public class RefinedAnvilBlockEntity extends BlockEntity {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public RefinedAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ZAMBlockEntities.REFINED_ANVIL.get(), pos, state);
    }

    public boolean addItem(ItemStack stack) {
        if (isEmpty() && !stack.isEmpty()) {
            ItemStack copy = stack.copy();
            copy.setCount(1);
            inventory.set(0, copy);
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isEmpty()) {
            ItemStack stack = inventory.get(0);
            inventory.set(0, ItemStack.EMPTY);
            setChanged();
            if (level != null) level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public boolean isEmpty() {
        return inventory.get(0).isEmpty();
    }

    public ItemStack getStoredItem() {
        return inventory.get(0);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, inventory, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, inventory, registries);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean processStoredGeodeUsingHammer(ItemStack toolStack, Player player) {
        if (level == null || level.isClientSide || isEmpty()) {
            System.out.println("Server: Failed initial checks - Level: " + level + ", Client: " + level.isClientSide + ", Empty: " + isEmpty());
            return false;
        }
        if (toolStack.getItem() != ZAMItems.PERIDOT_HAMMER.get()) {
            if (player != null) {
                player.displayClientMessage(Component.literal("Use a Peridot Hammer to crack the geode!"), true);
            }
            System.out.println("Server: Tool is not a Peridot Hammer: " + toolStack);
            return false;
        }
        ItemStack storedGeode = removeItem();
        if (storedGeode.isEmpty()) {
            System.out.println("Server: No geode to process");
            return false;
        }
        ResourceKey<LootTable> lootTableKey = getLootTableForGeode(storedGeode);
        if (lootTableKey == null) {
            System.out.println("Server: No loot table for geode: " + storedGeode);
            return false;
        }
        toolStack.hurtAndBreak(1, (ServerLevel) level, player, (item) -> {});
        if (level instanceof ServerLevel serverLevel) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(lootTableKey);
            System.out.println("Server: Loot table retrieved: " + lootTableKey.location());
            if (lootTable == LootTable.EMPTY) {
                System.out.println("Server: Loot table is empty or not found: " + lootTableKey.location());
                return false;
            }
            LootParams lootParams = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .withParameter(LootContextParams.TOOL, toolStack)
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                    .create(LootContextParamSets.GIFT);
            System.out.println("Server: Loot params created: " + lootParams);
            List<ItemStack> generatedLoot = lootTable.getRandomItems(lootParams);
            System.out.println("Server: Generated loot: " + generatedLoot);
            BlockPos dropPos = worldPosition.above();
            for (ItemStack loot : generatedLoot) {
                System.out.println("Server: Dropping loot item: " + loot);
                Block.popResource(level, dropPos, loot.copy());
            }
            level.playSound(null, worldPosition, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return true;
        }
        System.out.println("Server: Level is not ServerLevel");
        return false;
    }

    private ResourceKey<LootTable> getLootTableForGeode(ItemStack geode) {
        String modId = "castingcaving";
        if (geode.getItem() == ZAMItems.GEODE.get()) {
            return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(modId, "refined_anvil/geode_loot"));
        } else if (geode.getItem() == ZAMItems.LAVA_GEODE.get()) {
            return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(modId, "refined_anvil/lava_geode_loot"));
        } else if (geode.getItem() == ZAMItems.MOSSY_GEODE.get()) {
            return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(modId, "refined_anvil/mossy_geode_loot"));
        } else if (geode.getItem() == ZAMItems.OCEAN_GEODE.get()) {
            return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(modId, "refined_anvil/ocean_geode_loot"));
        } else if (geode.getItem() == ZAMItems.ECLIPSE_GEODE.get()) {
            return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(modId, "refined_anvil/eclipse_geode_loot"));
        }
        System.out.println("Server: No matching loot table for: " + geode); // Debug
        return null;
    }
}