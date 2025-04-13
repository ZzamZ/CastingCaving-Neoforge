package net.zam.castingcaving.common.gui.arcade;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.zam.castingcaving.CastingCaving;
import net.zam.castingcaving.common.block.arcade.ArcadeBlockEntity;
import net.zam.castingcaving.common.gui.arcade.container.ArcadeContainer;
import net.zam.castingcaving.common.gui.arcade.games.ArcadeSnake;


public class ArcadeGui extends AbstractContainerScreen<ArcadeContainer> {

    private final ArcadeContainer screenContainer;
    private final ArcadeBlockEntity te;
    public ArcadeScreen screen;
    public ArcadeGame game;

    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(CastingCaving.MOD_ID,
            "textures/gui/container/arcade_gui.png");

    public ArcadeGui(ArcadeContainer screenContainer, Inventory inv, Component tc) {
        super(screenContainer, inv, tc);
        this.screenContainer = screenContainer;
        this.te = (ArcadeBlockEntity) Minecraft.getInstance().level.getBlockEntity(screenContainer.pos);

        this.imageWidth = 403;
        this.imageHeight = 226;

        screen = te.screen;

        setGame(ArcadeSnake::new);
    }

    @Override
    protected void renderBg(GuiGraphics gg, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        int mainGuiWidth = 326;

        gg.blit(GUI, x, y, 0, 0, mainGuiWidth, this.imageHeight, 512, 256);

        int sidebarTextureX = 326;
        int sidebarTextureY = 0;
        int sidebarWidth = 77;
        int sidebarHeight = 226;

        int sidebarX = x + mainGuiWidth - 2;
        int sidebarY = y;

        gg.blit(GUI, sidebarX, sidebarY, sidebarTextureX, sidebarTextureY, sidebarWidth, sidebarHeight, 512, 256);
    }


    public void setGame(ArcadeManager.GameConstructor g) {
        screen.setBgRenderer(null);
        screen.setFgRenderer(null);
        te.setGame(g);
        game = te.game;
        if(game instanceof ArcadeSnake)
            ((ArcadeSnake)game).gui = this;
        game.start();
    }

    @Override
    public void resize(Minecraft p_96575_, int p_96576_, int p_96577_) {
        super.resize(p_96575_, p_96576_, p_96577_);
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean mouseClicked(double x, double y, int b) {
        return super.mouseClicked(x, y, b);
    }

    @Override
    public void render(GuiGraphics gg, int x, int y, float p_230430_4_) {
        super.render(gg, x, y, p_230430_4_);

        int snakeColor = 0xa;
        int deathColor = 0x4;

        screen.renderBackground(gg, this.width, this.height, snakeColor, deathColor);
        screen.renderForeground(gg, this.font, this.width, this.height);

        GlStateManager._disableBlend();
        this.renderTooltip(gg, x, y);
    }
    public BlockEntity getTE() {
        return te;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        game.onKeyPressed(keyCode);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        game.onKeyReleased(keyCode);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    protected void renderLabels(GuiGraphics gg, int mouseX, int mouseY) {
    }
}