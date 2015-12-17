package halive.jsnake.config;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JSnakeConfig {

    private File configFileDestination;
    private JSONObject configFileContents;

    public JSnakeConfig(File configFileDestination) throws IOException {
        this.configFileDestination = configFileDestination;
        configFileContents = new JSONObject();
        if (configFileDestination.exists() &&
                configFileDestination.canRead() &&
                !configFileDestination.isDirectory()) {
            FileInputStream in = new FileInputStream(configFileDestination);
            JSONTokener loader = new JSONTokener(in);
            configFileContents = new JSONObject(loader);
            in.close();
        } else if (configFileDestination.isDirectory()) {
            throw new IOException("Invalid Config file. This is a Directory.");
        } else {

        }
    }
}
