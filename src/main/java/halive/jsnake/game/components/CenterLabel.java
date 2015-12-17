package halive.jsnake.game.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import java.awt.Dimension;
import java.awt.Point;

public class CenterLabel extends Label {

    /**
     * Contstucts a new Label
     * <p>
     * By Default the font is Arial 12 As A TrueTypeFont using AntiAliasing
     * The Default color is White.
     * These values can be schanged using the setColor/Font methods
     *
     * @param position      The Positon (Top Center) of the TextLabel
     * @param boxToCenterIn Defines the size of the Box in which the text should be centered
     * @param text          The inital Message the label should display
     */
    public CenterLabel(Point position, Dimension boxToCenterIn, String text) {
        super(position, text);
        this.size = boxToCenterIn;
    }

    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        FontUtils.drawCenter(font, message, position.x, position.y + (size.height / 2) - 10, size.width, color);
    }
}
