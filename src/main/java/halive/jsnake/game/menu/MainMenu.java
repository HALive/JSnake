package halive.jsnake.game.menu;

import halive.jsnake.game.ComponentRenderer;
import halive.jsnake.game.GameStates;
import halive.jsnake.game.components.Button;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

public class MainMenu extends BasicGameState {

    private Button startGameButton;
    private Button highscoreButton;
    private Button exitButton;

    private ComponentRenderer renderer;

    private Dimension screenDimension;
    private StateBasedGame game;

    @Override
    public int getID() {
        return GameStates.MAIN_MENU.getID();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        this.screenDimension = new Dimension(container.getWidth(), container.getHeight());
        renderer = new ComponentRenderer();

        startGameButton = new Button(new Point(200, 100), new Dimension(400, 80), Color.lightGray, "Start Game");
        highscoreButton = new Button(new Point(200, 200), new Dimension(400, 80), Color.lightGray, "Highscores");
        exitButton = new Button(new Point(200, 300), new Dimension(400, 80), Color.lightGray, "Exit");

        startGameButton.setButtonClickListener((x, y, b) -> startNewGame(b));
        highscoreButton.setButtonClickListener((x, y, b) -> showHighscores(b));
        exitButton.setButtonClickListener((x, y, b) -> exitGame(b));

        renderer.addComponentToRender(100, startGameButton);
        renderer.addComponentToRender(101, highscoreButton);
        renderer.addComponentToRender(102, exitButton);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        renderer.render(g, container, game);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int mouseX = Mouse.getX();
        int mouseY = (int) (screenDimension.getHeight() - Mouse.getY());
        renderer.update(container, game, delta, mouseX, mouseY);
    }

    private void startNewGame(int button) {
        if (button == 0) {
            this.game.enterState(GameStates.MAIN_GAME.getID());
        }
    }

    private void showHighscores(int button) {

    }

    private void exitGame(int button) {
        if (button == 0) {
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        List<Button> buttons = renderer.getButtons();
        buttons.forEach((btn) -> {
            if (btn.isOnComponent(x, y)) {
                btn.buttonClicked(x, y, button);
            }
        });
    }
}
