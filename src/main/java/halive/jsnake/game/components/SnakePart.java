package halive.jsnake.game.components;

import halive.jsnake.game.core.SnakeGame;
import halive.util.SlickUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

public class SnakePart<T extends SnakePart> extends Component {

    public static final Dimension RECT_DIM = new Dimension(SnakeGame.RECT_SIZE, SnakeGame.RECT_SIZE);

    protected Color snakeColor = Color.cyan;
    protected Color frameColor = SlickUtils.inverColor(snakeColor);

    protected Point gridPos;

    protected T childNode;

    public SnakePart(Point position) {
        super(convertToDisplayPos(position), RECT_DIM);
        gridPos = position;
    }

    public static Point convertToDisplayPos(Point position) {
        return new Point((1 + position.x) * SnakeGame.RECT_SIZE, (1 + position.y) * SnakeGame.RECT_SIZE);
    }

    public void updatePosition(Point p) {
        position = convertToDisplayPos(p);
        gridPos = p;
    }

    public boolean isOnSnake(Point p) {
        return (gridPos.x == (p.x - 1) && gridPos.y == (p.y - 1)) ||
                (childNode != null && childNode.isOnSnake(p));
    }

    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        g.setColor(snakeColor);
        g.fillRect(position.x, position.y, size.width, size.height);
        g.setColor(frameColor);
        g.drawRect(position.x, position.y, size.width, size.height);
        if (childNode != null) {
            childNode.render(g, c, sbg);
        }
    }
}
