package net.zam.castingcaving.common.gui.arcade;

import net.minecraft.sounds.SoundEvents;
import net.zam.castingcaving.common.block.arcade.ArcadeBlockEntity;
import net.zam.castingcaving.util.Notes;

public abstract class ArcadeGame {
    public ArcadeScreen s;
    protected ArcadeBlockEntity te;

    public ArcadeGame(ArcadeScreen s, ArcadeBlockEntity te) {
        this.s = s;
        this.te = te;
    }

    public void frame(long time) {
    }

    public void start() {
    }

    public void onKeyPressed(int key) {
    }

    public void onKeyReleased(int key) {
    }

    public void playSound(int note) {
        Notes.playClientNote(SoundEvents.NOTE_BLOCK_BIT, note); // Removed .get()
    }

    public void playSound(int sound, int note) {
        switch (sound) {
            case 0:
                Notes.playClientNote(SoundEvents.NOTE_BLOCK_BASS, note); // Removed .get()
                break;
            case 2:
                Notes.playClientNote(SoundEvents.NOTE_BLOCK_HAT, note); // Removed .get()
                break;
            case 12:
                Notes.playClientNote(SoundEvents.NOTE_BLOCK_BIT, note); // Removed .get()
                break;
        }
    }

    public static boolean isAny(int key) {
        return key > 0;
    }

    public static boolean isLeft(int key) {
        return key == 263 || key == 65; // Left arrow or 'A'
    }

    public static boolean isRight(int key) {
        return key == 262 || key == 68; // Right arrow or 'D'
    }

    public static boolean isUp(int key) {
        return key == 265 || key == 87; // Up arrow or 'W'
    }

    public static boolean isDown(int key) {
        return key == 264 || key == 83; // Down arrow or 'S'
    }

    public static boolean isReset(int key) {
        return key == 269; // End key
    }

    public static String getKeyName(int key) {
        switch (key) {
            case 32:
                return "space";
            case 257:
                return "return";
            case 268:
                return "home";
            case 256:
                return "escape";
            case 290:
                return "help";
            case 263:
            case 65:
                return "left";
            case 262:
            case 68:
                return "right";
            case 265:
            case 87:
                return "up";
            case 264:
            case 83:
                return "down";
            case 269:
                return "reset";
        }
        return "unknown";
    }
}