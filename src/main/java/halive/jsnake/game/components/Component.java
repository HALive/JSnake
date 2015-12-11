package halive.jsnake.game.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;

public abstract class Component {

    protected Point position;
    protected Dimension size;

    public Component(Point position, Dimension size) {
        this.position = position;
        this.size = size;
    }

    public void update(GameContainer c, StateBasedGame sbg, int d, int mouseX,int mouseY) {}
    public abstract void render(Graphics g, GameContainer c, StateBasedGame sbg);
}
