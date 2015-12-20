/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.core;


import halive.jsnake.game.core.components.Component;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

public class OverlayMenu extends Component {

    private Color bgColor;

    private boolean active;

    public OverlayMenu(Dimension size, Color backgroundColor) {
        super(new Point(0,0), size);
        this.bgColor = backgroundColor;
    }

    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        if (active) {
            g.setColor(bgColor);
            g.fillRect(position.x, position.y, size.width, size.height);
        }
    }

    @Override
    public void update(GameContainer c, StateBasedGame sbg, int d, int mouseX, int mouseY) {
        if(active) {

        }
    }

    public void onMouseClick(int x, int y, int btn) {
        if(active) {

        }
    }

    public boolean isActive() {
        return active;
    }

    public void toggleMenu() {
        active = !active;
    }

}
