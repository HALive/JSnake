/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.config;

import halive.jsnake.JSnake;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class JSnakeConfig {

    private File configFileDestination;
    private JSONObject configFileContents;

    public JSnakeConfig(File configFileDestination) throws IOException {
        this.configFileDestination = configFileDestination;
        configFileContents = new JSONObject();
        if (configFileDestination.exists() &&
                configFileDestination.canRead() &&
                !configFileDestination.isDirectory()) {
            try {
                FileInputStream in = new FileInputStream(configFileDestination);
                JSONTokener loader = new JSONTokener(in);
                configFileContents = new JSONObject(loader);
                in.close();
            } catch (IOException e) {
                JSnake.logger.log(Level.ALL, "Could not Load the ConfigFile. Using Defaults");
                initializeWithDefaults(true);
            }
        } else if (configFileDestination.isDirectory()) {
            throw new IOException("Invalid Config file. This is a Directory.");
        } else {
            initializeWithDefaults(false);
        }
    }

    private void initializeWithDefaults(boolean causedByError) {
        JSnake.logger.log(Level.INFO, "Creating default Configuration file.");
        for (ConfigEntryManager.ConfigEntry entry : ConfigEntryManager.entries) {
            configFileContents.put(entry.getKey(), entry.getDefaultValue());
        }
        if (!causedByError) {
            JSnake.logger.log(Level.INFO, "Saving configuration file to disk.");
            FileWriter out = null;
            try {
                out = new FileWriter(configFileDestination);
                configFileContents.write(out);
            } catch (IOException e) {
                JSnake.logger.log(Level.ALL, "Could not save config File", e);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        JSnake.logger.log(Level.ALL, "Could not close Stream.", e);
                    }
                }
            }
        }
    }

    public JSONObject getContents() {
        return configFileContents;
    }
}
