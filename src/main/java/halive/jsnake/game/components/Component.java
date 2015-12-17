package halive.jsnake.game.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

/**
 * This Class descibes a Component that can be rendered by the ComponentRender
 */
public abstract class Component {

    /**
     * Defines the Postion of the Component
     */
    protected Point position;
    /**
     * Defines the Rectangular size of the Component
     */
    protected Dimension size;

    public Component(Point position, Dimension size) {
        this.position = position;
        this.size = size;
    }

    /**
     * This method is called to Initialize things that might need the Container/StateBasedGame to Initialize
     *
     * @param c    The Game Container (Parameter from slick 2d)
     * @param game the StateBasedGame (Parameter from Slick 2d)
     */
    public void init(GameContainer c, StateBasedGame game) {
    }

    /**
     * This method is Called by the Component Renderes Update method which gets invoked by Slick2ds update Method
     *
     * @param c      The Game Container (Parameter from slick 2d)
     * @param sbg    the StateBasedGame (Parameter from Slick 2d)
     * @param d      the delta Value (used for Movement...) given from slick 2d
     * @param mouseX x Position of the mouse, gets Calculated by Component renderer if used with it
     * @param mouseY y Position of the mouse, gets Calculated by Component renderer if used with it.
     */
    public void update(GameContainer c, StateBasedGame sbg, int d, int mouseX, int mouseY) {
    }

    /**
     * This method is called to render the Component. (Has to Be Implemented)
     *
     * @param g   The Graphics object to draw the component with
     * @param c   The game Container (Slick 2d)
     * @param sbg the StateBasedGame (Slick 2d)
     */
    public abstract void render(Graphics g, GameContainer c, StateBasedGame sbg);
}
