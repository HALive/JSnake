package halive.jsnake.game.components;

import halive.jsnake.game.core.SnakeGameState;
import halive.util.SlickUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Point;

/**
 * This Class describes The Snake Head to whitch the child nodes are attached
 */
public class Snake extends SnakePart<Snake.SnakeNode> {

    /**
     * This defines the Cycles (update() calls) to toggle one tick
     */
    private static final int CYCLES_PER_TICK = 5;
    /**
     * You have to eat the amount of food given in the constant to get another crossing.
     */
    private static final int FOOD_EATEN_PER_CROSSING = 10;

    /**
     * Defines the Possible Colors of SnakeNodes
     */
    private static final Color[] NODE_COLORS = {Color.blue, Color.green, Color.orange, Color.yellow,
            Color.lightGray, Color.white};

    /**
     * Stores the Current movement vector
     */
    private Point movingDirection;

    /**
     * Stores the Reamining number of allowed Crossings.
     * Every time a new game starts this is set to zero and every time you
     * eat ten food items this gets incremented by one.
     * It gets decremented if you cross yourself. once the number is smaller then 0 you loose.
     */
    private int remainingCrossings = 0;
    /**
     * Stores the length of the snake;
     */
    private int length;

    /**
     * Used to reference back
     */
    private SnakeGameState game;

    /**
     * Counts the cycles, used to determine the tick point
     */
    private int cycleCounter = 0;

    /**
     * Creates a new Snake at the given grid Positon
     *
     * @param position
     * @param game
     */
    public Snake(Point position, SnakeGameState game) {
        super(position);
        movingDirection = new Point(0, 0);
        this.game = game;
        setColor(Color.red);
    }

    /**
     * Sets the movement Direction to move up
     */
    public void moveUp() {
        move(new Point(0, -1));
    }

    /**
     * Sets the movement direction to move down
     */
    public void moveDown() {
        move(new Point(0, 1));
    }

    /**
     * Stes the Movement direction to move Left
     */
    public void moveLeft() {
        move(new Point(-1, 0));
    }

    /**
     * Sets the movement direction to move right
     */
    public void moveRight() {
        move(new Point(1, 0));
    }

    /**
     * Sets the Movement direction to a specific direction
     *
     * @param direction should be eiter (1,0),(-1,0),(0,1) or (0,-1)
     */
    private void move(Point direction) {
        if ((direction.y == movingDirection.y && direction.x == movingDirection.x) ||
                ((direction.x + movingDirection.x) == 0 && (direction.y + movingDirection.y) == 0)) {
            return;
        }
        movingDirection = direction;
    }

    @Override
    public void update(GameContainer c, StateBasedGame sbg, int d, int mouseX, int mouseY) {
        if (cycleCounter == 0) {
            Point oldGridPos = gridPos;
            Point newPos = new Point(gridPos.x + movingDirection.x, gridPos.y + movingDirection.y);
            newPos.x = (newPos.x % game.getGridDimension().width);
            if (newPos.x < 0) {
                newPos.x = game.getGridDimension().width - 1;
            }
            newPos.y = (newPos.y % game.getGridDimension().height);
            if (newPos.y < 0) {
                newPos.y = game.getGridDimension().height - 1;
            }
            if (isOnSnake(newPos) && childNode != null) {
                remainingCrossings--;
                game.updateRemainigCrossings(remainingCrossings);
                if (remainingCrossings < 0) {
                    game.setGameOver();
                }
                if (childNode != null) {
                    childNode.swapColors();
                }
            }

            updatePosition(newPos);
            Point foodPosition = game.getFood().getGridPosition();
            if (gridPos.x == (foodPosition.x - 1) && gridPos.y == (foodPosition.y - 1)) {
                game.spawnNewFoodRectangle();
                addNode(oldGridPos);
                length = this.getSnakeLength();
                game.updateScore(length);
                remainingCrossings += ((length % FOOD_EATEN_PER_CROSSING) == 0) ? 1 : 0;
                game.updateRemainigCrossings(remainingCrossings);
            }
            if (childNode != null) {
                childNode.updatePosition(oldGridPos);
            }
        }
        cycleCounter++;
        cycleCounter = cycleCounter % CYCLES_PER_TICK;
    }

    /**
     * Adds a node to the end
     *
     * @param oldGridPos
     */
    private void addNode(Point oldGridPos) {
        if (childNode != null) {
            childNode.addNodeToEndAndUpdatePos(oldGridPos);
        } else {
            childNode = new SnakeNode(oldGridPos);
        }
    }

    /**
     * This class descibes a Childnode of the snakeHead
     */
    public class SnakeNode extends SnakePart<SnakeNode> {

        /**
         * Creates a new SnakeNode with the given Grid Positon.
         * <p>
         * This Constructor also determines a random Color for the created child node
         *
         * @param p
         */
        public SnakeNode(Point p) {
            super(p);
            Color color = NODE_COLORS[game.getRandom().nextInt(NODE_COLORS.length)];
            boolean invert = game.getRandom().nextBoolean();
            setColor(invert ? SlickUtils.inverColor(color) : color);
        }

        /**
         * Adds a new node at the end of the snake. The given positon is a gridPostion
         *
         * @param np
         */
        private void addNodeToEndAndUpdatePos(Point np) {
            Point oldPos = gridPos;
            this.position = new Point((np.x + 1) * SnakeGameState.RECT_SIZE, (np.y + 1) * SnakeGameState.RECT_SIZE);
            gridPos = np;
            if (childNode != null) {
                childNode.addNodeToEndAndUpdatePos(oldPos);
            } else {
                childNode = new SnakeNode(oldPos);
            }
        }

        /**
         * Updates postions o the current node and its child nodes
         *
         * @param np
         */
        public void updatePosition(Point np) {
            Point oldPos = gridPos;
            this.position = new Point((np.x + 1) * SnakeGameState.RECT_SIZE, (np.y + 1) * SnakeGameState.RECT_SIZE);
            gridPos = np;
            if (childNode != null) {
                childNode.updatePosition(oldPos);
            }
        }
    }
}
