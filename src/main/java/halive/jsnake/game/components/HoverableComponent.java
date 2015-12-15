package halive.jsnake.game.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

public abstract class HoverableComponent extends Component {

    /**
     * Is true if the component is currently hovered
     */
    protected boolean itemHovered = false;

    public HoverableComponent(Point position, Dimension size) {
        super(position, size);
    }

    /**
     * Returns true if the item is currently hovered
     * @return
     */
    public boolean isItemHovered() {
        return itemHovered;
    }

    @Override
    public void update(GameContainer c, StateBasedGame sbg, int d, int mouseX, int mouseY) {
        updateHover(mouseX, mouseY);
    }

    /**
     * This method cheks if the current mousePostion is on the Object.
     * @param x Mouse X Position
     * @param y Mouse Y Position
     */
    private void updateHover(int x, int y) {
        if (isOnComponent(x, y)) {
            onHovering(x, y);
            itemHovered = true;
        } else {
            itemHovered = false;
        }
    }

    /**
     * Returns true if the given coordinates are on the component
     * @param x x Position
     * @param y y Position
     * @return
     */
    public boolean isOnComponent(int x, int y) {
        return ((y >= position.y) && y <= (position.y + size.height)) &&
                ((x >= position.x) && (x <= (position.x + size.width)));
    }

    /**
     * This method is called by the update() Method if the metod gets hovered
     * @param x Mouse X Position
     * @param y Mouse Y Position
     */
    public void onHovering(int x, int y) {
    }
}
