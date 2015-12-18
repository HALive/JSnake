/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

/**
 * This class describes a Rectangle displayed in The Background of the snake Game
 * The drawn rectangle is not filled its ust a frame
 */
public class GridRectangle extends Component {

    /**
     * Stores the Color of the Rectangle
     */
    private Color color;

    public GridRectangle(Point position, Dimension size, Color color) {
        super(position, size);
        this.color = color;
    }

    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        g.setColor(color);
        g.drawRect(position.x, position.y, size.width, size.height);
    }
}
