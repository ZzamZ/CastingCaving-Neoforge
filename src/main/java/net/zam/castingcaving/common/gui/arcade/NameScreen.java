package net.zam.castingcaving.common.gui.arcade;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.zam.castingcaving.common.block.arcade.ArcadeBlockEntity;
import net.zam.castingcaving.common.gui.arcade.games.ArcadeSnake;

public class NameScreen extends Screen {
    private final ArcadeSnake game;
    private final int score;
    private final ArcadeBlockEntity arcadeTileEntity;
    private LimitedEditBox nameField;

    public NameScreen(ArcadeSnake game, int score, ArcadeBlockEntity arcadeTileEntity) {
        super(Component.literal("New High Score! Enter Name Below!"));
        this.game = game;
        this.score = score;
        this.arcadeTileEntity = arcadeTileEntity;
    }

    @Override
    protected void init() {
        int width = this.width / 2;
        int height = this.height / 4;

        this.nameField = new LimitedEditBox(this.font, width - 100, height, 200, 20, Component.literal("Enter name"), 3);
        this.nameField.setValue("AAA");
        this.addRenderableWidget(nameField);

        this.addRenderableWidget(new TooltipButton(
                width - 100, height + 40, 200, 20,
                Component.literal("Submit"),
                button -> {
                    game.addHighScore(nameField.getValue(), score);
                    game.saveHighScores();
                    Minecraft.getInstance().setScreen(null);
                },
                Component.literal("Click to submit your name")
        ));
    }

    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float delta) {
        this.renderBackground(gg, mouseX, mouseY, delta);
        super.render(gg, mouseX, mouseY, delta);
        gg.drawCenteredString(this.font, "New High Score! Enter Name Below", this.width / 2, this.height / 4 - 20, 0xFFFFFF);
    }

    @Override
    public void renderBackground(GuiGraphics gg, int mouseX, int mouseY, float delta) {
        super.renderBackground(gg, mouseX, mouseY, delta);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static class TooltipButton extends Button {
        private final Component tooltip;

        public TooltipButton(int x, int y, int width, int height, Component message, OnPress onPress, Component tooltip) {
            super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
            this.tooltip = tooltip;
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
            super.renderWidget(guiGraphics, mouseX, mouseY, delta);
            if (this.isHoveredOrFocused()) {
                guiGraphics.renderTooltip(Minecraft.getInstance().font, tooltip, mouseX, mouseY);
            }
        }
    }

    private static class LimitedEditBox extends EditBox {
        private final int maxLength;

        public LimitedEditBox(Font font, int x, int y, int width, int height, Component message, int maxLength) {
            super(font, x, y, width, height, message);
            this.maxLength = maxLength;
        }

        @Override
        public boolean charTyped(char codePoint, int modifiers) {
            if (this.getValue().length() >= maxLength) {
                return false;
            }
            return super.charTyped(codePoint, modifiers);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // Escape key
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
    }
}