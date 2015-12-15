package halive.jsnake.game.components;

import halive.jsnake.game.core.SnakeGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Point;

/**
 * Descirbes a Food Rectangle that can be eaten by a Snake
 */
public class FoodRectangle extends Component {

    /**
     * Describes the Color of the food Rectangle
     */
    private Color color = Color.red;
    /**
     * Stores teh GridPositon of the Object
     */
    private Point gridPosition;

    public FoodRectangle(Point position) {
        super(new Point(position.x * SnakeGameState.RECT_SIZE, position.y * SnakeGameState.RECT_SIZE), Snake.RECT_DIM);
        this.gridPosition = position;
    }

    /**
     * Renders the Component
     *
     * @param g   The Graphics object to draw the component with
     * @param c   The game Container (Slick 2d)
     * @param sbg the StateBasedGame (Slick 2d)
     */
    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        g.setColor(color);
        g.fillRect(position.x, position.y, size.width, size.height);
    }

    /**
     * Returns the GridPosition
     *
     * @return
     */
    public Point getGridPosition() {
        return gridPosition;
    }
}
