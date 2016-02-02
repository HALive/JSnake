/*
 * Copyright (c) 2016, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.highscore.display;

import halive.jsnake.game.core.KeyControls;
import halive.jsnake.game.core.components.Component;
import halive.jsnake.game.highscore.HighscoreEntry;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HighscoreList extends Component {

    private List<HighscoreEntry> entries;
    private int entryPos = 0;
    private int maxRenderableEntries;
    private Font boldListFont = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18), true);
    private Font listFont = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18), true);

    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:MM.ss");

    public HighscoreList(Point position, Dimension size) {
        super(position, size);
        maxRenderableEntries = (int) (size.getHeight() / 20);
    }

    public void updateEntries(List<HighscoreEntry> entries) {
        entryPos = 0;
        this.entries = entries;
    }

    @Override
    public void render(Graphics g, GameContainer c, StateBasedGame sbg) {
        for (int i = 0; i < maxRenderableEntries; i++) {
            g.setColor((i % 2) == 0 ? Color.gray : Color.lightGray);
            g.fillRect(position.x, position.y + i * 20, size.width, 20);
        }
        g.setFont(boldListFont);
        g.setColor(Color.black);
        g.drawString("Name", position.x + 5, position.y);
        g.drawString("Timestamp", position.x + size.width / 2, position.y);
        g.drawString("Score", position.x + 5 * (size.width / 6), position.y);
        g.setFont(listFont);
        if (entries == null || entries.size() == 0) {
            g.drawString("No valid Highscores Found", position.x + 5, position.y + 20);
        } else {
            for (int i = 0; i < entries.size() - entryPos; i++) {
                HighscoreEntry entry = entries.get(i + entryPos);
                renderEntry(g, i, entry);
            }
        }
    }

    private void renderEntry(Graphics g, int pos, HighscoreEntry entry) {
        g.drawString(entry.getName(), position.x + 5, position.y + pos * 20 + 20);
        g.drawString(format.format(new Date(entry.getTimeStamp())),
                position.x + size.width / 2, position.y + pos * 20 + 20);
        g.drawString(String.valueOf(entry.getScore()),
                position.x + 5 * (size.width / 6), position.y + pos * 20 + 20);
    }

    public void onKeyPressed(int code, char c) {
        if (entries != null && entries.size() > maxRenderableEntries) {
            if (KeyControls.DOWN.isKeycodeVaild(code)) {
                scrollDown();
            } else if (KeyControls.UP.isKeycodeVaild(code)) {
                scrollUp();
            }
        }
    }

    private void scrollUp() {
        if (entryPos != 0) {
            entryPos--;
        }
    }

    private void scrollDown() {
        if (entryPos != (entries.size() - maxRenderableEntries + 1)) {
            entryPos++;
        }
    }

    public void onScrolled(int v) {
        if (v > 0) {
            scrollDown();
        } else if (v < 0) {
            scrollUp();
        }
    }
}
