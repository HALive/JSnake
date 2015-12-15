package halive.jsnake.game.components;

import halive.jsnake.game.core.SnakeGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Point;

public class FoodRectangle extends Component {

    /**
     *  Describes the Color of the food Rectangle
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

    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        g.setColor(color);
        g.fillRect(position.x, position.y, size.width,size.height);
    }

    public Point getGridPosition() {
        return gridPosition;
    }
}
