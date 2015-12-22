/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.core;

import halive.jsnake.game.GameStates;
import halive.jsnake.game.JSnakeGame;
import halive.jsnake.game.core.components.Button;
import halive.jsnake.game.core.components.CenterLabel;
import halive.jsnake.game.highscore.HighscoreEntry;
import halive.jsnake.game.highscore.HighscoreSerializer;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

public class GameOverState extends BasicGameState {

    private JSnakeGame game;
    private CenterLabel label;
    private TextField nameTextField;

    private Button submitButton;

    @Override
    public int getID() {
        return GameStates.GAME_OVER.getID();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = (JSnakeGame) game;
        label = new CenterLabel(new Point(0, 50),
                new Dimension(this.game.getScreenDimension().width, 100), "Game Over!", 36);
        Font font = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36), true);
        Dimension textFieldDim = new Dimension(400, 40);
        nameTextField = new TextField(container, font, (this.game.getScreenDimension().width / 2) - textFieldDim.width / 2,
                200, textFieldDim.width, textFieldDim.height);
        submitButton = new Button(new Point((this.game.getScreenDimension().width / 2) - (textFieldDim.width / 2), 300),
                textFieldDim, Color.lightGray, "Submit Score");
        submitButton.setButtonClickListener(this::onSubmitButtonClicked);
    }

    private void onSubmitButtonClicked(int x, int y, int b) {
        HighscoreEntry entry = new HighscoreEntry(nameTextField.getText(), this.game.getLastHighScore(),
                this.game.getConfig().getConfigSignature());
        HighscoreSerializer serializer = new HighscoreSerializer(entry, this.game.getConfig().getHighscoreFolder());
        serializer.start();
        this.game.enterState(GameStates.MAIN_MENU.getID());
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        label.setMessage("Game Over! Your score: " + this.game.getLastHighScore());
        nameTextField.setText("Your Name");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        label.render(g, container, game);
        g.setColor(Color.lightGray);
        nameTextField.render(container, g);
        submitButton.render(g, container, game);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int mouseX = Mouse.getX();
        int mouseY = (int) (this.game.getScreenDimension().getHeight() - Mouse.getY());
        submitButton.update(container, game, delta, mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (submitButton.isOnComponent(x, y) && button == 0) {
            submitButton.buttonClicked(x, y, button);
        }
    }
}
