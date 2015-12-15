package halive.jsnake.game;

import halive.jsnake.game.components.Button;
import halive.jsnake.game.components.Component;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class ComponentRenderer {

    private Set<RenderTask> components;

    public ComponentRenderer() {
        this.components = Collections.synchronizedSet(new HashSet<>());
    }

    public void addComponentToRender(int priority, Component c) {
        components.add(new RenderTask(c, priority));
    }

    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        final PriorityQueue<RenderTask> queue =
                new PriorityQueue<>((Comparator<RenderTask>) (o1, o2) -> o1.priority - o2.priority);
        components.forEach((o) -> queue.add(o));
        RenderTask task = null;
        while ((task = queue.poll()) != null) {
            task.component.render(g, c, sbg);
        }
    }

    public void update(GameContainer c, StateBasedGame sbg, int d, int mouseX, int mouseY) {
        components.forEach((o) -> o.component.update(c, sbg, d, mouseX, mouseY));
    }


    public List<Button> getButtons() {
        ArrayList<Button> btns = new ArrayList<>();
        components.forEach((o) -> {
            if (o.component instanceof Button) {
                btns.add((Button) o.component);
            }
        });
        return btns;
    }

    private static class RenderTask {

        private int priority;
        private Component component;

        public RenderTask(Component component, int priority) {
            this.component = component;
            this.priority = priority;
        }
    }
}
