package net.zam.castingcaving.common.block.arcade;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zam.castingcaving.common.gui.arcade.ArcadeGame;
import net.zam.castingcaving.common.gui.arcade.ArcadeManager;
import net.zam.castingcaving.common.gui.arcade.ArcadeScreen;
import net.zam.castingcaving.common.gui.arcade.container.ArcadeContainer;
import net.zam.castingcaving.registry.ZAMBlockEntities;

public class ArcadeBlockEntity extends BlockEntity implements MenuProvider {

    public ArcadeScreen screen = null;
    public ArcadeGame game = null;
    public long time = 0;

    public ArcadeBlockEntity(BlockPos pos, BlockState state) {
        super(ZAMBlockEntities.ARCADE_MACHINE.get(), pos, state);
        screen = new ArcadeScreen();
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return ArcadeContainer.create(windowId, inv, getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Arcade Machine");
    }

    public static void tick(Level world, BlockPos pos, BlockState state, ArcadeBlockEntity te) {
        if (world.isClientSide() && te.game != null)
            te.game.frame(te.time++);
    }

    public void setGame(ArcadeManager.GameConstructor g) {
        this.game = g.construct(screen, this);
    }
}
