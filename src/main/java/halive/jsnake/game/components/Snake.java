package halive.jsnake.game.components;

import halive.jsnake.game.core.SnakeGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Point;

public class Snake extends SnakePart<Snake.SnakeNode> {

    private static final int CYCLES_PER_TICK = 10;

    private Point movingDirecton;

    private SnakeGame game;

    private int cycleCounter = 0;

    public boolean addNode = false;

    public Snake(Point position, SnakeGame game) {
        super(position);
        movingDirecton = new Point(0, 0);
        this.game = game;
    }


    public void moveUp() {
        movingDirecton = new Point(0, -1);
    }

    public void moveDown() {
        movingDirecton = new Point(0, 1);
    }

    public void moveLeft() {
        movingDirecton = new Point(-1, 0);
    }

    public void moveRight() {
        movingDirecton = new Point(1, 0);
    }

    @Override
    public void update(GameContainer c, StateBasedGame sbg, int d, int mouseX, int mouseY) {
        if (cycleCounter == 0) {
            Point oldGridPos = gridPos;
            Point newPos = new Point(gridPos.x + movingDirecton.x, gridPos.y + movingDirecton.y);
            newPos.x = (newPos.x % game.getGridDimension().width);
            if (newPos.x < 0) {
                newPos.x = game.getGridDimension().width - 1;
            }
            newPos.y = (newPos.y % game.getGridDimension().height);
            if (newPos.y < 0) {
                newPos.y = game.getGridDimension().height - 1;
            }

            updatePosition(newPos);
            Point foodPosition = game.getFood().getGridPosition();
            if (gridPos.x == (foodPosition.x - 1) && gridPos.y == (foodPosition.y - 1) || addNode) {
                game.spawnNewFoodRectangle();
                addNode(oldGridPos);
                addNode = false;
            }
            if (childNode != null) {
                childNode.updatePosition(oldGridPos);
            }
        }
        cycleCounter++;
        cycleCounter = cycleCounter % CYCLES_PER_TICK;
    }

    private void addNode(Point oldGridPos) {
        if (childNode != null) {
            childNode.addNodeToEndAndUpdatePos(oldGridPos);
        } else {
            childNode = new SnakeNode(oldGridPos);
        }
    }

    public class SnakeNode extends SnakePart<SnakeNode> {

        public SnakeNode(Point p) {
            super(p);
            snakeColor = Color.blue;
        }

        private void addNodeToEndAndUpdatePos(Point np) {
            Point oldPos = gridPos;
            this.position = new Point((np.x + 1) * SnakeGame.RECT_SIZE, (np.y + 1) * SnakeGame.RECT_SIZE);
            gridPos = np;
            if (childNode != null) {
                childNode.addNodeToEndAndUpdatePos(oldPos);
            } else {
                childNode = new SnakeNode(oldPos);
            }
        }

        public void updatePosition(Point np) {
            Point oldPos = gridPos;
            this.position = new Point((np.x + 1) * SnakeGame.RECT_SIZE, (np.y + 1) * SnakeGame.RECT_SIZE);
            gridPos = np;
            if (childNode != null) {
                childNode.updatePosition(oldPos);
            }
        }
    }
}
