/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConfigEntryManager {

    private static int DEFAULT_WIDTH = 800;
    private static int DEFAULT_HEIGHT = 600;

    private static String DEFAULT_FOOD_COLOR = "FF0000";
    private static String DEFAULT_SNAKE_HEAD_COLOR = "CC5F00";
    private static String[] DEFAULT_NODE_COLORS = {"A5A5A5", "0015FF", "FFA600", "26FF00", "00FFFC"};

    public static ConfigEntry[] entries;
    static {
        ArrayList<ConfigEntry> entryArrayList = new ArrayList<>();
        //Add the Window Resolution Options
        JSONObject windowResoultion = new JSONObject("{\"width\": " + DEFAULT_WIDTH
                + ", \"height\": " + DEFAULT_HEIGHT + "}");
        entryArrayList.add(new ConfigEntry<>(ConfigKeys.WINDOW_DIMENSION.getKey(), windowResoultion));
        //Add the Default food Color Code
        entryArrayList.add(new ConfigEntry<>(ConfigKeys.FOOD_COLOR.getKey(), DEFAULT_FOOD_COLOR));
        //Add the Default Snake head Color
        entryArrayList.add(new ConfigEntry<>(ConfigKeys.SNAKE_HEAD_COLOR.getKey(), DEFAULT_SNAKE_HEAD_COLOR));
        //Add the Default node Color Codes
        JSONArray array = new JSONArray();
        for (String code : DEFAULT_NODE_COLORS) {
            array.put(code);
        }
        entryArrayList.add(new ConfigEntry<>(ConfigKeys.SNAKE_NODE_COLORS.getKey(), DEFAULT_NODE_COLORS));

        entries = new ConfigEntry[entryArrayList.size()];
        entryArrayList.toArray(entries);
    }

    public static class ConfigEntry<T> {

        private String key;
        private T defaultValue;

        private ConfigEntry(String key, T defaultValue) {
            this.defaultValue = defaultValue;
            this.key = key;
        }

        public T getDefaultValue() {
            return defaultValue;
        }

        public String getKey() {
            return key;
        }
    }

    public enum ConfigKeys {
        WINDOW_DIMENSION("windowDimension"),
        WINDOW_HEIGHT("height"),
        WINDOW_WIDTH("width"),
        FOOD_COLOR("foodColor"),
        SNAKE_HEAD_COLOR("snakeHeadColor"),
        SNAKE_NODE_COLORS("snakeNodeColors");

        private String key;

        ConfigKeys(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return key;
        }

        public String getKey() {
            return key;
        }

        public class SNAKE_HEAD_COLOR {

        }
    }
}
