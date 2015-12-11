package halive.jsnake.game.core;

import halive.jsnake.game.ComponentRenderer;
import halive.jsnake.game.GameStates;
import halive.jsnake.game.components.FoodRectangle;
import halive.jsnake.game.components.GridRectangle;
import halive.jsnake.game.components.Snake;
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

public class SnakeGame extends BasicGameState {

    public static final int RECT_SIZE = 20;

    private GridRectangle[][] grid;

    private Snake snake;
    private FoodRectangle food;

    private StateBasedGame game;
    private ComponentRenderer renderer;

    private Random random = new Random();

    private Dimension screenDimension;

    @Override
    public int getID() {
        return GameStates.MAIN_GAME.getID();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        this.screenDimension = new Dimension(container.getWidth(), container.getHeight());

        renderer = new ComponentRenderer();

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
        snake = new Snake(new Point(random.nextInt(grid.length), random.nextInt(grid[0].length)), this);
        renderer.addComponentToRender(prio, snake);
        prio++;
        spawnNewFoodRectangle();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        renderer.render(g, container, game);
        food.render(g,container,game);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int mouseX = Mouse.getX();
        int mouseY = (int) (screenDimension.getHeight() - Mouse.getY());
        renderer.update(container, game, delta, mouseX, mouseY);
    }

    @Override
    public void keyPressed(int key, char c) {
        System.out.println(key);
        if(KeyControls.UP.isKeycodeVaild(key)) {
            snake.moveUp();
        } else if(KeyControls.DOWN.isKeycodeVaild(key)) {
            snake.moveDown();
        } else if(KeyControls.LEFT.isKeycodeVaild(key)) {
            snake.moveLeft();
        } else if(KeyControls.RIGHT.isKeycodeVaild(key)) {
            snake.moveRight();
        } else if(KeyControls.ESCAPE.isKeycodeVaild(key)) {
            snake.addNode = true;
        }
    }

    public FoodRectangle getFood() {
        return food;
    }

    public void spawnNewFoodRectangle() {
        Point point = null;
        do {
            point = new Point(random.nextInt(grid.length)+1, random.nextInt(grid[0].length)+1);
        }while(snake.isOnSnake(point));
        System.out.printf("Spawning food at x=%d y=%d\n", point.x, point.y);
        food = new FoodRectangle(point);
    }

    public Dimension getGridDimension() {
        return new Dimension(grid.length, grid[0].length);
    }
}
