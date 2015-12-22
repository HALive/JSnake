/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.config;

import com.google.gson.Gson;
import halive.jsnake.JSnake;
import halive.util.HashUtils;

import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class JSnakeConfig {

    private Dimension windowDimensions = new Dimension(800, 600);
    private String foodColor = "#FF0000";
    private String snakeHeadColor = "#CC5F00";
    private String[] snakeNodeColors = {"#A5A5A5", "#0015FF", "#FFA600", "#26FF00", "#00FFFC"};
    private boolean invertSnakeNodeColors = false;
    private int cyclesPerTick = 5;
    private int foodPerCrossing = 10;

    private transient String configSignature;

    private JSnakeConfig() {

    }

    private JSnakeConfig(File configFileDestination) throws IOException {
        JSnake.logger.log(Level.INFO, "Creating default Configuration file.");
        Gson gson = new Gson();
        FileWriter out = null;
        try {
            out = new FileWriter(configFileDestination);
            String configFile = gson.toJson(this);
            out.append(configFile);
        } catch (IOException e) {
            JSnake.logger.log(Level.SEVERE, "Could not wirte Default Config. Using Defaults.", e);
        } finally {
            out.close();
        }
    }

    public static JSnakeConfig getConfiguration(File f) {
        if (!f.exists()) {
            try {
                return new JSnakeConfig(f);
            } catch (IOException e) {
                JSnake.logger.log(Level.SEVERE, "The Config file Could not get Created. Using Defaults.", e);
                return new JSnakeConfig();
            }
        } else if (f.isDirectory() || !f.canRead()) {
            JSnake.logger.log(Level.SEVERE, "Using Default Config. Config is not Readable.");
            return new JSnakeConfig();
        }
        Gson gson = new Gson();
        FileReader r = null;
        try {
            r = new FileReader(f);
            Object o = gson.fromJson(r, JSnakeConfig.class);
            return (JSnakeConfig) o;
        } catch (IOException e) {
            JSnake.logger.log(Level.SEVERE, "Using Default Config. Config is not Readable.", e);
            return new JSnakeConfig();
        } finally {
            try {
                r.close();
            } catch (IOException e) {
            }
        }
    }

    public int getCyclesPerTick() {
        return cyclesPerTick;
    }

    public String getFoodColor() {
        return foodColor;
    }

    public int getFoodPerCrossing() {
        return foodPerCrossing;
    }

    public boolean isInvertSnakeNodeColors() {
        return invertSnakeNodeColors;
    }

    public String getSnakeHeadColor() {
        return snakeHeadColor;
    }

    public String[] getSnakeNodeColors() {
        return snakeNodeColors;
    }

    public Dimension getWindowDimensions() {
        return windowDimensions;
    }

    public void calculateSignature() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(out);
        try {
            dout.writeInt(windowDimensions.width);
            dout.writeInt(windowDimensions.height);
            dout.writeInt(cyclesPerTick);
            dout.writeInt(foodPerCrossing);
        } catch (IOException e) {
        }
        configSignature = HashUtils.getSHA256Hash(out.toByteArray());
        JSnake.logger.info("The Config Signature is: " + configSignature);
    }

    public String getConfigSignature() {
        return configSignature;
    }
}
