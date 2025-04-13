package net.zam.castingcaving.common.gui.arcade;

import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.zam.castingcaving.common.block.arcade.ArcadeBlockEntity;
import net.zam.castingcaving.common.gui.arcade.games.ArcadeSnake;

import java.util.ArrayList;
import java.util.List;

public class ArcadeManager {
    public static GameConstructor SNAKE_GAME = ArcadeSnake::new; // Static reference to ArcadeSnake game

    public interface GameConstructor {
        ArcadeGame construct(ArcadeScreen s, ArcadeBlockEntity te);
    }

    public static ArcadeManager instance = null;
    private final ArrayList<GameConstructor> games;
    private final ArrayList<Component> names;

    public ArcadeManager() {
        if (instance == null) {
            instance = this;
        }
        games = new ArrayList<>();
        names = new ArrayList<>();
    }

    public void add(GameConstructor game, String key) {
        games.add(game);
        names.add(Component.literal("Arcade Machine " + key));
    }

    public List<GameConstructor> getGames() {
        return this.games;
    }

    public GameConstructor getGame(int index) {
        return this.games.get(index);
    }

    public String getGameName(int index) {
        return this.names.get(index).getString();
    }

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        new ArcadeManager();
        instance.add(SNAKE_GAME, "snake");
    }
}
