/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game;

import halive.jsnake.game.core.GameOverState;
import halive.jsnake.game.core.SnakeGameState;
import halive.jsnake.game.highscore.display.HighscoreDisplayState;
import halive.jsnake.game.menu.MainMenu;
import org.newdawn.slick.state.GameState;

public enum GameStates {
    MAIN_MENU(0, MainMenu.class),
    MAIN_GAME(1, SnakeGameState.class),
    GAME_OVER(2, GameOverState.class),
    HIGHSCORE_DISPLAY(3, HighscoreDisplayState.class);

    private int id;
    private Class<? extends GameState> stateClass;

    GameStates(int id, Class<? extends GameState> stateClass) {
        this.id = id;
        this.stateClass = stateClass;
    }

    public int getID() {
        return id;
    }

    public Class<? extends GameState> getStateClass() {
        return stateClass;
    }
}
