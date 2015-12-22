/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game;

import halive.jsnake.JSnake;
import halive.jsnake.config.JSnakeConfig;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.util.logging.Level;

public class JSnakeGame extends StateBasedGame {

    private JSnakeConfig config;

    private int lastHighScore = -1;

    private Dimension screenDimension;

    public JSnakeGame(JSnakeConfig config) {
        super("JSnake");
        this.config = config;
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        screenDimension = new Dimension(container.getWidth(), container.getHeight());
        for (GameStates state : GameStates.values()) {
            try {
                GameState s = state.getStateClass().newInstance();
                addState(s);
            } catch (InstantiationException | IllegalAccessException e) {
                JSnake.logger.log(Level.ALL, "Could not Initialize GameStates.", e);
            }
        }
    }

    public int getLastHighScore() {
        return lastHighScore;
    }

    public void setLastHighScore(int lastHighScore) {
        this.lastHighScore = lastHighScore;
    }

    public Dimension getScreenDimension() {
        return screenDimension;
    }

    public JSnakeConfig getConfig() {
        return config;
    }
}
