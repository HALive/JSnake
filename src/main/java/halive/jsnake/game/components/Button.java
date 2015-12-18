/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.components;

import halive.util.SlickUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Describes a Simple Button (Two Colors and some Text)
 * <p>
 * While the button is hovered, it Inverts the colors (Text color becomes BackgroundColor and Reverse)
 */
public class Button extends HoverableComponent {

    /**
     * Stores the Background Color of the Button
     */
    private Color backgroundColor;
    /**
     * Stores the Hover BackgroundColor (by default the Inverted Background Color)
     */
    private Color hoverBgColor;

    /**
     * Stores the Text that should be displayed on the button
     */
    private String displayText;
    /**
     * Stores the Font of the DisplayText
     */
    private Font textFont = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20), true);

    /**
     * Stores the method reference to the onClickListener
     */
    private ButtonClickListener listener;

    public Button(Point pos, Dimension size, Color backgroundColor, String displayText) {
        super(pos, size);
        this.backgroundColor = backgroundColor;
        hoverBgColor = SlickUtils.invertColor(backgroundColor);
        this.displayText = displayText;
    }

    /**
     * Sets the Reference to the OnClickListener
     *
     * @param listener
     */
    public void setButtonClickListener(ButtonClickListener listener) {
        this.listener = listener;
    }

    /**
     * This Method has to be Called by the Game/GameState if the button gets clicked
     *
     * @param x      Mouse x pos
     * @param y      Mouse y pos
     * @param button Mouse Button (0=Left, 1=Middle, 2=Right)
     */
    public void buttonClicked(int x, int y, int button) {
        if (listener != null) {
            listener.onClick(x, y, button);
        }
    }

    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        g.setColor(isItemHovered() ? hoverBgColor : backgroundColor);
        g.fillRect(position.x, position.y, size.width, size.height);
        FontUtils.drawCenter(textFont, displayText, position.x, position.y + (size.height / 2) - 10, size.width,
                !isItemHovered() ? hoverBgColor : backgroundColor);
    }

    public interface ButtonClickListener {

        /**
         * This Method is called when someone Clicks on the button with any mouseButton
         *
         * @param x      x Mouse Pos
         * @param y      y Mouse Pos
         * @param button MouseButton
         */
        void onClick(int x, int y, int button);
    }
}
