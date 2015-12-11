package halive.jsnake.game;

import halive.jsnake.JSnake;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.logging.Level;

public class JSnakeGame extends StateBasedGame {

    public JSnakeGame(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        for (GameStates state : GameStates.values()) {
            try {
                GameState s = state.getStateClass().newInstance();
                addState(s);
            } catch (InstantiationException | IllegalAccessException e) {
                JSnake.logger.log(Level.SEVERE, "Could not Initialize GameStates.", e);
            }
        }
    }
}
