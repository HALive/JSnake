/*
 * Copyright (c) 2016, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.highscore.display;

import halive.jsnake.game.GameStates;
import halive.jsnake.game.JSnakeGame;
import halive.jsnake.game.core.components.Button;
import halive.jsnake.game.core.components.CenterLabel;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;


public class HighscoreDisplayState extends BasicGameState {

    private Button backButton;

    private CenterLabel heading;

    private JSnakeGame game;
    private GameContainer container;

    private Dimension screenDimension;

    @Override
    public int getID() {
        return GameStates.HIGHSCORE_DISPLAY.getID();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = (JSnakeGame) game;
        this.container = container;
        screenDimension = new Dimension(container.getWidth(), container.getHeight());
        backButton = new Button(new Point(10, container.getHeight() - 80), new Dimension(container.getWidth() - 20, 70),
                Color.lightGray, "Back");
        backButton.setButtonClickListener((x, y, b) -> {
            if (b == 0) {
                game.enterState(GameStates.MAIN_MENU.getID());
            }
        });

        heading = new CenterLabel(new Point(container.getWidth() / 2 - 100, 10),
                new Dimension(200, 50), "Highscores",40);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        heading.render(g, container, game);
        backButton.render(g, container, game);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int mouseX = Mouse.getX();
        int mouseY = (int) (screenDimension.getHeight() - Mouse.getY());
        backButton.update(container, game, delta, mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (backButton.isOnComponent(x, y)) {
            backButton.buttonClicked(x, y, button);
        }
    }
}
