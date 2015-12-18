/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.components;

import halive.jsnake.game.core.SnakeGameState;
import halive.util.SlickUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Describes the abstract Portion of a SnakeHead and its chils nodes
 *
 * @param <T>
 */
public abstract class SnakePart<T extends SnakePart> extends Component {

    public static final Dimension RECT_DIM = new Dimension(SnakeGameState.RECT_SIZE, SnakeGameState.RECT_SIZE);

    /**
     * Stores the Color of the Rectangle
     */
    protected Color snakeColor = null;
    /**
     * Stores the Color of the Frame (Around the Rectangle)
     */
    protected Color frameColor = null;

    /**
     * If this is true a Frame is Drawn
     */
    protected boolean drawFrame = true;

    /**
     * Stores the Gridpositon of the SnakeElement
     */
    protected Point gridPos;

    /**
     * Sotres the attached ChildNOde
     */
    protected T childNode;

    /**
     * Creates a new Snake Part objet at the given grid Postion
     *
     * @param position
     */
    public SnakePart(Point position) {
        super(convertToDisplayPos(position), RECT_DIM);
        gridPos = position;
    }

    /**
     * Converts a GridPositon to a On Screen Positon
     *
     * @param position
     * @return
     */
    public static Point convertToDisplayPos(Point position) {
        return new Point((1 + position.x) * SnakeGameState.RECT_SIZE, (1 + position.y) * SnakeGameState.RECT_SIZE);
    }

    /**
     * Sets the Position to the Given GridPosition
     *
     * @param p
     */
    public void updatePosition(Point p) {
        position = convertToDisplayPos(p);
        gridPos = p;
    }

    /**
     * Returns true if the given grid positon is on the Snake
     *
     * @param p
     * @return
     */
    public boolean isOnSnake(Point p) {
        return (childNode != null && childNode.isOnSnake(p)) ||
                (gridPos.x == (p.x) && gridPos.y == (p.y));
    }

    /**
     * Called to render the SnakePart
     *
     * @param g
     * @param c
     * @param sbg
     */
    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        if (childNode != null) {
            childNode.render(g, c, sbg);
        }
        g.setColor(snakeColor);
        g.fillRect(position.x, position.y, size.width, size.height);
        if (drawFrame) {
            g.setColor(frameColor);
            g.drawRect(position.x, position.y, size.width, size.height);
        }
    }

    /**
     * Swaps the Frame and RectangleColor (Invokes this method on the childNode)
     */
    public void swapColors() {
        Color store = frameColor;
        frameColor = snakeColor;
        snakeColor = store;
        if (childNode != null) {
            childNode.swapColors();
        }
    }

    /**
     * Sets the color of the current node and its attached nodes
     *
     * @param color
     */
    public void setColor(Color color) {
        snakeColor = color;
        frameColor = SlickUtils.invertColor(color);
        if (childNode != null) {
            childNode.setColor(color);
        }
    }

    /**
     * Returns the Length of the snake from the Current point. (i Called Externally)
     * It returns the length ( Amount of nodes ) of the snake.
     *
     * @return The returnvalue is at least 1.
     */
    public int getSnakeLength() {
        return childNode == null ? 1 : 1 + childNode.getSnakeLength();
    }
}
