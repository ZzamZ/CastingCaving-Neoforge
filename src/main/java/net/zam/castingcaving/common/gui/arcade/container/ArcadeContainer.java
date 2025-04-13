package net.zam.castingcaving.common.gui.arcade.container;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.zam.castingcaving.registry.ZAMMenuTypes;

public class ArcadeContainer extends BaseContainer {

    public final BlockPos pos;

    protected ArcadeContainer(int id, BlockPos pos) {
        super(ZAMMenuTypes.ARCADE_CONTAINER.get(), id);
        this.pos = pos;
    }

    public static ArcadeContainer create(int windowId, Inventory playerInventory, BlockPos pos) {
        return new ArcadeContainer(windowId, pos);
    }

    public static ArcadeContainer create(int windowId, Inventory playerInventory, FriendlyByteBuf buf) {
        return new ArcadeContainer(windowId, buf.readBlockPos());
    }
}
