package net.zam.castingcaving.common.gui.arcade.games;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zam.castingcaving.common.block.arcade.ArcadeBlockEntity;
import net.zam.castingcaving.common.gui.arcade.ArcadeGame;
import net.zam.castingcaving.common.gui.arcade.ArcadeGui;
import net.zam.castingcaving.common.gui.arcade.ArcadeScreen;
import net.zam.castingcaving.common.gui.arcade.NameScreen;
import net.zam.castingcaving.common.network.SnakeHighScorePacket;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class ArcadeSnake extends ArcadeGame {
    public ArcadeGui gui = null;
    private final ArcadeBlockEntity arcadeTileEntity;
    private boolean advancementTriggered = false; // Track if advancement was triggered this game

    private String getHighScoresFilePath() {
        MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server != null) {
            File worldDirectory = server.getWorldPath(LevelResource.ROOT).toFile();
            return new File(worldDirectory, "arcade_snake_high_scores.json").getAbsolutePath();
        }
        return "arcade_snake_high_scores.json"; // Fallback in case of an issue
    }

    private static final List<HighScoreEntry> DEFAULT_HIGH_SCORES = List.of(
            new HighScoreEntry("ZAM", 100),
            new HighScoreEntry("LEY", 80),
            new HighScoreEntry("MRG", 60),
            new HighScoreEntry("BRZ", 40),
            new HighScoreEntry("DRP", 20)
    );

    public static class HighScoreEntry implements Comparable<HighScoreEntry> {
        int score;
        String playerName;

        public HighScoreEntry(String playerName, int score) {
            this.score = score;
            this.playerName = playerName;
        }

        @Override
        public int compareTo(HighScoreEntry other) {
            return Integer.compare(other.score, this.score); // For descending order
        }
    }

    public static List<HighScoreEntry> highScores = new ArrayList<>();

    public ArcadeSnake(ArcadeScreen s, ArcadeBlockEntity te) {
        super(s, te);
        this.arcadeTileEntity = te;
        pw = s.width - 5;
        ph = s.height;
        loadHighScores();
    }

    int px = 0;
    int py = 0;
    int lpx = 0;
    int lpy = 0;
    int dir = -1;
    int ldir = -1;
    int pw;
    int ph;
    long deadTimer = Long.MAX_VALUE;
    boolean isDead = false;
    int snakeColor = 0xa;
    int foodColor = 0xe;
    int score;
    boolean initScreen = true;
    Tail last = null;
    Tail first = null;
    int tailExt = 0;

    @Override
    public void start() {
        super.start();
        s.clear();
        s.testScreen();
        s.setColors(0x0, 0xf);
        s.clear(0, 1, s.width, s.height - 2);
        s.print(1, 1, "Arcade Snake v1.0");
        String msg = " Press any key to continue. ";
        s.print(s.width / 2 - msg.length() / 2, s.height / 2, msg);

        initScreen = false;
        begin();
        dir = -1;
    }

    private void begin() {
        s.setColors(0x0, 0xf);
        s.clear();
        int BORDER_MARGIN = 2;
        int LEFT_BOUND = BORDER_MARGIN - 1;
        int RIGHT_BOUND = pw - BORDER_MARGIN - 4;
        int TOP_BOUND = BORDER_MARGIN - 1;
        int BOTTOM_BOUND = ph - BORDER_MARGIN;

        px = (LEFT_BOUND + RIGHT_BOUND) / 2;
        py = (TOP_BOUND + BOTTOM_BOUND) / 2;
        lpx = px;
        lpy = py;
        dir = -1;
        ldir = -1;
        score = 0;
        isDead = false;
        deadTimer = Long.MAX_VALUE;
        last = null;
        first = null;
        tailExt = 0;
        advancementTriggered = false; // Reset advancement trigger flag

        s.setColors(0x8, 0xa);
        s.clear(pw, 0, s.width - pw, s.height);
        s.print(pw, 13, score + "");
        updateHighScoresDisplay();
        s.print(pw - 3, s.height - 8, "Arrows");
        s.print(pw - 3, s.height - 7, "or");
        s.print(pw - 3, s.height - 6, "WASD");
        spawnFood(LEFT_BOUND, RIGHT_BOUND, TOP_BOUND, BOTTOM_BOUND);
        spawnFood(LEFT_BOUND, RIGHT_BOUND, TOP_BOUND, BOTTOM_BOUND);
    }

    private void updateHighScoresDisplay() {
        s.setColors(0x8, 0xa);
        int highScoresStart = 1;

        for (int i = 0; i < highScores.size() && i < 5; i++) { // Show up to 5 scores
            HighScoreEntry entry = highScores.get(i);
            String highScoreText = entry.playerName + ":" + entry.score;
            s.print(pw - 3, highScoresStart + i, highScoreText);
        }
    }

    public int randomRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void spawnFood(int leftBound, int rightBound, int topBound, int bottomBound) {
        Set<String> snakePositions = new HashSet<>();

        Tail current = last;
        while (current != null) {
            snakePositions.add(current.x + "," + current.y);
            current = current.next;
        }

        int growthX = px, growthY = py;
        switch (dir) {
            case 0 -> growthY--; // Moving up
            case 1 -> growthX++; // Moving right
            case 2 -> growthY++; // Moving down
            case 3 -> growthX--; // Moving left
        }
        snakePositions.add(growthX + "," + growthY);

        int x, y;
        do {
            x = randomRange(leftBound, rightBound);
            y = randomRange(topBound, bottomBound);
        } while (snakePositions.contains(x + "," + y));

        s.setColors(0x0, foodColor);
        s.print(x, y, "o");
        s.setColors(0x8, 0xa);
        s.print(pw - 2, s.height - 1, repeat(">", 7 - tickRate(score)));
    }

    private String repeat(String t, int n) {
        String r = "";
        for (int i = 0; i < n; i++)
            r += t;
        return r;
    }

    private int tickRate(int score) {
        if (score > 20) return 2;
        if (score > 10) return 3;
        if (score > 7) return 4;
        if (score > 3) return 5;
        return 6;
    }

    public void updateMovement(long time) {
        int BORDER_MARGIN = 2;
        int LEFT_BOUND = BORDER_MARGIN - 1;
        int RIGHT_BOUND = pw - BORDER_MARGIN - 4;
        int TOP_BOUND = BORDER_MARGIN - 1;
        int BOTTOM_BOUND = ph - BORDER_MARGIN + 1;

        lpx = px;
        lpy = py;

        if (dir >= 0) {
            switch (dir) {
                case 0 -> py--;
                case 1 -> px++;
                case 2 -> py++;
                case 3 -> px--;
            }

            int deathX = px;
            int deathY = py;

            // Collision and boundary check for death condition
            if (px < LEFT_BOUND || px > RIGHT_BOUND || py < TOP_BOUND || py > BOTTOM_BOUND || s.getBackground(px, py) == snakeColor) {
                isDead = true;
                deadTimer = time + 60;

                if (px < LEFT_BOUND) deathX = LEFT_BOUND;
                else if (px > RIGHT_BOUND) deathX = RIGHT_BOUND;
                if (py < TOP_BOUND) deathY = TOP_BOUND;
                else if (py > BOTTOM_BOUND) deathY = BOTTOM_BOUND;

                s.setColors(0x4, 0xf);
                s.print(deathX, deathY, "x");
                playSound(0);

                if (isHighScore(score)) {
                    Minecraft.getInstance().setScreen(new NameScreen(this, score, arcadeTileEntity));
                }
                return;
            }

            if (s.getBackground(px, py) == snakeColor) {
                deadTimer = time + 60;
                isDead = true;
                s.setColors(0x4, 0xf);
                s.print(px, py, "x");
                playSound(0);
                return;
            }

            // Handle food collection and score increment
            if (s.getChar(px, py) == 'o') {
                score++;
                s.setColors(0x8, 0xa);
                s.print(pw, 13, String.valueOf(score));
                spawnFood(LEFT_BOUND, RIGHT_BOUND, TOP_BOUND, BOTTOM_BOUND);
                tailExt = 3;
                playSound(30);

                if (score >= 100 && !advancementTriggered) {
                    if (Minecraft.getInstance().level != null) {
                        PacketDistributor.sendToServer(new SnakeHighScorePacket(score));
                        advancementTriggered = true; // Prevent repeated triggers in this game
                    }
                }
            }

            // Move the snake's tail
            if (last != null && tailExt <= 0) {
                s.setColors(0x0, 0xf);
                s.print(last.x, last.y, " ");
                last = last.next;
            }

            s.setColors(snakeColor, 0xf);
            s.print(lpx, lpy, getToken(dir, ldir));
            ldir = dir;
            Tail t = new Tail(lpx, lpy, null);
            if (first != null) first.next = t;
            first = t;
            if (last == null) last = t;
            tailExt = Math.max(tailExt - 1, 0);
        }
        s.setColors(snakeColor, 0xf);
        s.print(px, py, "@");
    }

    private String getToken(int d, int ld) {
        if (d == ld) {
            return dir == 0 || dir == 2 ? "-" : "|";
        } else if ((d == 0 && ld == 1) || (d == 1 && ld == 0))
            return "\\";
        else if ((d == 0 && ld == 3) || (d == 3 && ld == 0))
            return "/";
        else if ((d == 2 && ld == 1) || (d == 1 && ld == 2))
            return "/";
        else if ((d == 2 && ld == 3) || (d == 3 && ld == 2))
            return "\\";
        return "-";
    }

    @Override
    public void frame(long time) {
        super.frame(time);
        if (time > deadTimer) begin();
        if (time % tickRate(score) == 0 && !isDead) updateMovement(time);
    }

    @Override
    public void onKeyPressed(int key) {
        super.onKeyPressed(key);
        if (initScreen && isAny(key)) {
            initScreen = false;
            begin();
        }
        if (isUp(key) && ldir != 2) dir = 0;
        else if (isRight(key) && ldir != 3) dir = 1;
        else if (isDown(key) && ldir != 0) dir = 2;
        else if (isLeft(key) && ldir != 1) dir = 3;
    }

    @Override
    public void onKeyReleased(int key) {
        super.onKeyReleased(key);
        if (isReset(key)) begin();
    }

    public void addHighScore(String playerName, int score) {
        highScores.add(new HighScoreEntry(playerName, score));
        Collections.sort(highScores);
        if (highScores.size() > 5) highScores.remove(5); // Keep only the top 5 scores
        saveHighScores();
    }

    public void saveHighScores() {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(getHighScoresFilePath())) {
            gson.toJson(highScores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHighScores() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(getHighScoresFilePath())) {
            Type highScoreListType = new TypeToken<ArrayList<HighScoreEntry>>() {}.getType();
            highScores = gson.fromJson(reader, highScoreListType);
            if (highScores == null) highScores = new ArrayList<>(DEFAULT_HIGH_SCORES);
        } catch (FileNotFoundException e) {
            highScores = new ArrayList<>(DEFAULT_HIGH_SCORES);
        } catch (IOException e) {
            e.printStackTrace();
            highScores = new ArrayList<>(DEFAULT_HIGH_SCORES);
        }
    }

    private boolean isHighScore(int score) {
        return highScores.size() < 5 || score > highScores.get(highScores.size() - 1).score;
    }

    private class Tail {
        public int x, y;
        public Tail next;

        public Tail(int x, int y, Tail next) {
            this.x = x;
            this.y = y;
            this.next = next;
        }
    }
}