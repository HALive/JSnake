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


public class Button extends HoverableComponent {

    private Color backgroundColor;
    private Color hoverBgColor;

    private String displayText;
    private Font textFont = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20), true);

    private ButtonClickListener listener;

    public Button(Point pos, Dimension size, Color backgroundColor, String displayText) {
        super(pos, size);
        this.backgroundColor = backgroundColor;
        hoverBgColor = SlickUtils.inverColor(backgroundColor);
        this.displayText = displayText;
    }

    public void setButtonClickListener(ButtonClickListener listener) {
        this.listener = listener;
    }

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

        void onClick(int x, int y, int button);
    }
}
