/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.highscore;

public class HighscoreEntry implements Comparable<HighscoreEntry> {

    private int score;
    private String name;
    private long timeStamp;
    private String configSignature;

    public HighscoreEntry(String name, int score, String configSignature) {
        this.configSignature = configSignature;
        this.name = name;
        this.score = score;
        this.timeStamp = System.currentTimeMillis();
    }

    public String getConfigSignature() {
        return configSignature;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public boolean isValidHighscoreEntry(String cfgSignature) {
        return cfgSignature.equals(configSignature);
    }

    public String getFileName() {
        return name + "-" + timeStamp + ".json";
    }

    @Override
    public int compareTo(HighscoreEntry o) {
        int scoreValueComp = (int) Math.signum(o.score - score);
        if (scoreValueComp != 0) {
            return scoreValueComp;
        }
        return (int) Math.signum(o.timeStamp - timeStamp);
    }
}
