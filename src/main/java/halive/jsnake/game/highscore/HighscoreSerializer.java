/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.game.highscore;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HighscoreSerializer implements Runnable {

    private HighscoreEntry entry;
    private Thread serializerThread;
    private File outputFolder;
    private String fileName;

    public HighscoreSerializer(HighscoreEntry entry, File outputFolder) {
        this.entry = entry;
        fileName = entry.getFileName();
        serializerThread = new Thread(this, "Serializer for " + fileName);
        this.outputFolder = outputFolder;
    }

    public void start() {
        serializerThread.start();
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        try {
            FileWriter writer = new FileWriter(new File(outputFolder, fileName));
            gson.toJson(entry, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
