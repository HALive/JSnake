package halive.jsnake.game.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

public class Label extends Component {

    protected Color color = Color.white;
    protected Font font = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12), true);
    protected String message;

    /**
     * Contstucts a new Label
     * <p>
     * By Default the font is Arial 12 As A TrueTypeFont using AntiAliasing
     * The Default color is White.
     * These values can be schanged using the setColor/Font methods
     *
     * @param position The Positon (Top Left) of the TextLabel
     * @param text     The inital Message the label should display
     */
    public Label(Point position, String text) {
        super(position, new Dimension(1, 1));
        message = text;
    }

    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(message, position.x, position.y);
    }


    /**
     * Returns the Current TextColor
     *
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the TextColor of the Label
     *
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the Current font
     *
     * @return
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the Font of the TextLabel
     *
     * @param font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Returns the current message of the Label
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets teh Message of the label to the given string.
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
