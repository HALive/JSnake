/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.highscore;

import halive.jsnake.config.JSnakeConfig;

import java.io.File;
import java.util.List;

public class HighscoreHandler {

    private File highscoreFolder;
    private List<HighscoreEntry> highscores;
    private String currenConfigSignature;

    public HighscoreHandler(File highscoreFolder, JSnakeConfig cfg) {
        this.highscoreFolder = highscoreFolder;
        this.currenConfigSignature = cfg.getConfigSignature();
    }

    private void loadFiles() {

    }
}
