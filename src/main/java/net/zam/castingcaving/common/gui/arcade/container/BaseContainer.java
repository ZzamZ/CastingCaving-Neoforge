package net.zam.castingcaving.common.gui.arcade.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public abstract class BaseContainer extends AbstractContainerMenu {

    protected BaseContainer(MenuType<?> type, int id) {
        super(type, id);
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isSpectator();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }
}