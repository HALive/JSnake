/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.core;

import halive.jsnake.game.ComponentRenderer;
import halive.jsnake.game.GameStates;
import halive.jsnake.game.JSnakeGame;
import halive.jsnake.game.core.components.CenterLabel;
import halive.jsnake.game.core.components.FoodRectangle;
import halive.jsnake.game.core.components.GridRectangle;
import halive.jsnake.game.core.components.Label;
import halive.jsnake.game.core.components.Snake;
import halive.util.SlickUtils;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

public class SnakeGameState extends BasicGameState {

    /**
     * Defines the size of a Rectangle
     */
    public static final int RECT_SIZE = 20;

    public static final Color FADE_COLOR =
            new Color(Color.lightGray.r, Color.lightGray.g, Color.lightGray.b, 0.5F);

    /**
     * Stores the FoodColor loaded from the Config file
     */
    private Color foodColor;
    /**
     * Stores the Snake Head Color loaded from the config file
     */
    private Color snakeHeadColor;
    /**
     * Stores the Colors of the Nodes Loaded from the config file
     */
    private Color[] snakeNodeColors;
    /**
     * Stores the intervall of cycles per Snake update (loaded from the cfg)
     */
    private int snakeCyclesPerTick;
    /**
     * Stores the food Eaten needed to get another crossing. (Loaded from the config)
     */
    private int snakeFoodPerCrossing;

    /**
     * Stores the Grid Of Rectangles rendered in the background
     */
    private GridRectangle[][] grid;

    /**
     * This references to the Most important component in the game. The Snake.
     */
    private Snake snake;
    /**
     * Stores the current FoodRectangle object
     */
    private FoodRectangle food;

    /**
     * References to the StateBasedGame, this gets captured in the init Mehtod
     */
    private JSnakeGame game;
    /**
     * This Renderer Handles the Rendering of most
     * of the components in the game (like the grid, the snake and the labels)
     * <p>
     * The only Component that is not Rendered by the Renderer is the FoodRectangle
     */
    private ComponentRenderer renderer;

    /**
     * This Random Number Generator is used to Generate The colors
     * of Snake Child Nodes and to generate the positons of the food.
     */
    private Random random = new Random();

    /**
     * Stores the Resulution the game runs at
     */
    private Dimension screenDimension;

    /**
     * Stores the current score (Starts at 1)
     */
    private int score = 1;

    /**
     * This Label Displays the Score
     */
    private Label scoreLabel;
    /**
     * This Label is used to Display the Remaining Crossings
     */
    private Label remainingCrossingsLabel;

    /**
     * Flag, thats set to true if the Game should end.
     */
    private boolean gameOver = false;

    /**
     * This Flag is True if the Game should get paused.
     */
    private boolean paused = false;
    private Label pausedLabel;

    /**
     * This stores a Reference to the overlay menu
     */
    private OverlayMenu overlayMenu;

    @Override
    public int getID() {
        return GameStates.MAIN_GAME.getID();
    }

    /**
     * Misc initialisation
     *
     * @param container
     * @param game
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = (JSnakeGame) game;
        this.screenDimension = new Dimension(container.getWidth(), container.getHeight());
        foodColor = SlickUtils.getColorFromRGBString(this.game.getConfig().getFoodColor());
        snakeHeadColor = SlickUtils.getColorFromRGBString(this.game.getConfig().getSnakeHeadColor());
        String[] a = this.game.getConfig().getSnakeNodeColors();
        boolean invert = this.game.getConfig().isInvertSnakeNodeColors();
        snakeNodeColors = new Color[a.length * (invert ? 2 : 1)];
        final int[] idx = {0};
        for (String o : a) {
            snakeNodeColors[idx[0]] = SlickUtils.getColorFromRGBString(o.toString());
            idx[0]++;
            if (invert) {
                snakeNodeColors[idx[0]] = SlickUtils.invertColor(snakeNodeColors[idx[0] - 1]);
                idx[0]++;
            }
        }

        snakeCyclesPerTick = this.game.getConfig().getCyclesPerTick();
        snakeFoodPerCrossing = this.game.getConfig().getFoodPerCrossing();
    }

    /**
     * Reinitializes the Components to a Fresh state.
     *
     * @param container
     * @param game
     * @throws SlickException
     */
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        gameOver = false;
        score = 1;
        renderer = new ComponentRenderer();

        //<editor-fold desc="Initialize Components Rendered By the Renderer">
        //Initialize the Grid
        grid = new GridRectangle[(screenDimension.width / RECT_SIZE) - 2][(screenDimension.height / RECT_SIZE) - 2];
        int prio = 0;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Point pos = new Point(((x + 1) * RECT_SIZE), ((y + 1) * RECT_SIZE));
                grid[x][y] = new GridRectangle(pos, new Dimension(RECT_SIZE, RECT_SIZE), Color.gray);
                renderer.addComponentToRender(prio, grid[x][y]);
                prio++;
            }
        }
        //Initialize the Snake
        snake = new Snake(new Point(random.nextInt(grid.length), random.nextInt(grid[0].length)), snakeHeadColor,
                snakeNodeColors, snakeCyclesPerTick, snakeFoodPerCrossing, this);
        renderer.addComponentToRender(prio, snake);
        prio++;
        //initialize the Labels
        scoreLabel = new CenterLabel(new Point(0, 5), new Dimension(screenDimension.width, 10),
                "Score :" + score);
        renderer.addComponentToRender(prio, scoreLabel);
        prio++;
        remainingCrossingsLabel = new Label(new Point(RECT_SIZE, 1), "Remaining Crossings: " + 0);
        renderer.addComponentToRender(prio, remainingCrossingsLabel);
        prio++;

        //Initialize the overlayMenu
        overlayMenu = new OverlayMenu(screenDimension, FADE_COLOR, this);
        renderer.addComponentToRender(prio, overlayMenu);
        renderer.init(container, game);
        //</editor-fold>

        spawnNewFoodRectangle();
        pausedLabel = new CenterLabel(new Point(0, (screenDimension.height / 2) - 20),
                new Dimension(screenDimension.width, 20),
                "Paused. Press \"p\" to unpause...");
    }

    /**
     * Renders all Components
     *
     * @param container
     * @param game
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        renderer.render(g, container, game);
        food.render(g, container, game);
        if (paused) {
            g.setColor(FADE_COLOR);
            g.fillRect(0, 0, screenDimension.width, screenDimension.height);
            pausedLabel.render(g, container, game);
        }
    }

    /**
     * Updates All Components in the State
     *
     * @param container
     * @param game
     * @param delta
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int mouseX = Mouse.getX();
        int mouseY = (int) (screenDimension.getHeight() - Mouse.getY());
        if (!paused && !overlayMenu.isActive()) {
            renderer.update(container, game, delta, mouseX, mouseY);
            if (gameOver) {
                this.game.setLastHighScore(score);
                game.enterState(GameStates.GAME_OVER.getID());
            }
        } else if (overlayMenu.isActive()) {
            overlayMenu.update(container, game, delta, mouseX, mouseY);
        }
    }

    /**
     * Handles mouse input if the Overlay Menu is Active.
     *
     * @param button
     * @param x
     * @param y
     * @param clickCount
     */
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        overlayMenu.onMouseClick(x, y, button);
    }

    /**
     * Handles Key input
     *
     * @param key
     * @param c
     */
    @Override
    public void keyPressed(int key, char c) {
        if (isGameRunning()) {
            if (KeyControls.UP.isKeycodeVaild(key) && isGameRunning()) {
                snake.moveUp();
                return;
            } else if (KeyControls.DOWN.isKeycodeVaild(key)) {
                snake.moveDown();
                return;
            } else if (KeyControls.LEFT.isKeycodeVaild(key)) {
                snake.moveLeft();
                return;
            } else if (KeyControls.RIGHT.isKeycodeVaild(key)) {
                snake.moveRight();
                return;
            }
        }
        if (KeyControls.PAUSE.isKeycodeVaild(key) && !overlayMenu.isActive()) {
            pauseGame();
        } else if (KeyControls.ESCAPE.isKeycodeVaild(key)) {
            overlayMenu.toggleMenu();
        }
    }

    /**
     * Returns the current Food Rectangle
     *
     * @return the Current Food Rectangle
     */
    public FoodRectangle getFood() {
        return food;
    }

    /**
     * Spawns a new Food Rectangle at a random positon thats not on the snake
     */
    public void spawnNewFoodRectangle() {
        Point point = null;
        do {
            point = new Point(random.nextInt(grid.length) + 1, random.nextInt(grid[0].length) + 1);
            if (snake.isOnSnake(point)) {
                continue;
            }
        } while (false);
        food = new FoodRectangle(point, foodColor);
    }

    /**
     * Returns the dimesion of the Grid
     *
     * @return The Dimension of the GameGrid
     */
    public Dimension getGridDimension() {
        return new Dimension(grid.length, grid[0].length);
    }

    /**
     * Retruns the Random Number Generator generated by this class
     *
     * @return the Random Number Generator
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Updates the Score Value to the current length of the snake.
     *
     * @param score the Score to set to
     */
    public void updateScore(int score) {
        this.score = score;
        scoreLabel.setMessage("Score: " + score);
    }

    /**
     * Changes the value in the ReaminingCrossings Label to the given integer
     *
     * @param crossings the Number of Remaining Crossings.
     */
    public void updateRemainingCrossings(int crossings) {
        this.remainingCrossingsLabel.setMessage("Remaining Crossings: " + crossings);
    }

    /**
     * Once this method is called all updating is stopped and the game is over.
     */
    public void setGameOver() {
        gameOver = true;
    }

    /**
     * Pauses/Unpauses the Game
     */
    private void pauseGame() {
        paused = !paused;
    }

    /**
     * Returns true if the game is not paused and the overlay menu does not get rendered
     *
     * @return
     */
    public boolean isGameRunning() {
        return !(paused || overlayMenu.isActive());
    }

    public JSnakeGame getGame() {
        return game;
    }
}
