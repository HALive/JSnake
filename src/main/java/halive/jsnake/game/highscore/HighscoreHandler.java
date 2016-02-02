/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.highscore;

import com.google.gson.Gson;
import halive.jsnake.JSnake;
import halive.jsnake.config.JSnakeConfig;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class HighscoreHandler {

    private File highscoreFolder;
    private List<HighscoreEntry> highscores;
    private String currentConfigSignature;

    public HighscoreHandler(File highscoreFolder, JSnakeConfig cfg) {
        this.highscoreFolder = highscoreFolder;
        this.currentConfigSignature = cfg.getConfigSignature();
        highscores = new ArrayList<>();
    }

    public void loadFiles() {
        highscores = new ArrayList<>();
        Gson gson = new Gson();
        for (File file : highscoreFolder.listFiles()) {
            if (!file.isDirectory() && file.getPath().toLowerCase().endsWith(".json")) {
                JSnake.logger.info("Loading: " + file.getName());
                try {
                    HighscoreEntry highscoreEntry = gson.fromJson(new FileReader(file), HighscoreEntry.class);
                    if (highscoreEntry.isValidHighscoreEntry(currentConfigSignature)) {
                        highscores.add(highscoreEntry);
                    }
                } catch (Exception e) {
                    JSnake.logger.log(Level.SEVERE, "Could not load Highscore Entry", e);
                }
            }
        }
        Collections.sort(highscores);
    }

    public List<HighscoreEntry> getEntries() {
        return highscores;
    }
}
