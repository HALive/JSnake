package halive.jsnake.game.components;

import halive.jsnake.game.core.SnakeGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Point;

public class FoodRectangle extends Component {

    private Color color = Color.red;
    private Point gridPosition;

    public FoodRectangle(Point position) {
        super(new Point(position.x * SnakeGame.RECT_SIZE, position.y * SnakeGame.RECT_SIZE), Snake.RECT_DIM);
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
