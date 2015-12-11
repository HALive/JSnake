package halive.jsnake.game.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

public abstract class HoverableComponent extends Component {

    protected boolean itemHovered = false;

    public HoverableComponent(Point position, Dimension size) {
        super(position, size);
    }

    public boolean isItemHovered() {
        return itemHovered;
    }

    @Override
    public void update(GameContainer c, StateBasedGame sbg, int d, int mouseX, int mouseY) {
        updateHover(mouseX, mouseY);
    }

    public void updateHover(int x, int y) {
        if (isOnComponent(x, y)) {
            onHovering(x, y);
            itemHovered = true;
        } else {
            itemHovered = false;
        }
    }

    public boolean isOnComponent(int x, int y) {
        return ((y >= position.y) && y <= (position.y + size.height)) &&
                ((x >= position.x) && (x <= (position.x + size.width)));
    }

    public void onHovering(int x, int y) {
    }
}
