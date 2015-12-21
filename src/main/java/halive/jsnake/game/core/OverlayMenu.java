/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.core;


import halive.jsnake.game.GameStates;
import halive.jsnake.game.core.components.Button;
import halive.jsnake.game.core.components.Component;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

public class OverlayMenu extends Component {

    private Color bgColor;

    private boolean active;

    private Button mainMenuButton;
    private Button resumeGameButton;

    private SnakeGameState state;

    public OverlayMenu(Dimension size, Color backgroundColor, SnakeGameState gameState) {
        super(new Point(0, 0), size);
        this.bgColor = backgroundColor;
        mainMenuButton = new Button(new Point((size.width / 2) - 200, 200), new Dimension(400, 80),
                Color.lightGray, "Main Menu");
        mainMenuButton.setButtonClickListener(this::onEnterMainMenuButtonClicked);
        resumeGameButton = new Button(new Point((size.width / 2) - 200, 100), new Dimension(400, 80),
                Color.lightGray, "Resume Game");
        resumeGameButton.setButtonClickListener(this::onResumeButtonClicked);
        this.state = gameState;
    }

    private void onResumeButtonClicked(int x, int y, int btn) {
        toggleMenu();
    }

    private void onEnterMainMenuButtonClicked(int x, int y, int btn) {
        state.getGame().enterState(GameStates.MAIN_MENU.getID());
    }

    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        if (active) {
            g.setColor(bgColor);
            g.fillRect(position.x, position.y, size.width, size.height);
            resumeGameButton.render(g, c, sbg);
            mainMenuButton.render(g, c, sbg);
        }
    }

    @Override
    public void update(GameContainer c, StateBasedGame sbg, int d, int mouseX, int mouseY) {
        if (active) {
            resumeGameButton.update(c, sbg, d, mouseX, mouseY);
            mainMenuButton.update(c, sbg, d, mouseX, mouseY);
        }
    }



    public void onMouseClick(int x, int y, int btn) {
        if (active && btn == 0) {
            if(mainMenuButton.isOnComponent(x,y)) {
                mainMenuButton.buttonClicked(x, y, btn);
            }else if (resumeGameButton.isOnComponent(x, y)) {
                resumeGameButton.buttonClicked(x, y, btn);
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void toggleMenu() {
        active = !active;
    }
}
