/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This Management Class Contains a Array that contains every ConfigEntry
 */
public class ConfigEntryManager {

    //Default Window Dimensions
    private static int DEFAULT_WIDTH = 800;
    private static int DEFAULT_HEIGHT = 600;

    //Default Color Settings
    private static String DEFAULT_FOOD_COLOR = "#FF0000";
    private static String DEFAULT_SNAKE_HEAD_COLOR = "#CC5F00";
    private static String[] DEFAULT_NODE_COLORS = {"#A5A5A5",
            "#0015FF", "#FFA600", "#26FF00", "#00FFFC"};

    //Default Game Settings
    private static int DEFAULT_CYCLES_PER_TICK = 5;
    private static int DEFAULT_FOOD_PER_CROSSING = 10;

    /**
     * This array Contains all ConfigEntries
     */
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
        //Add the Invert Colors option
        entryArrayList.add(new ConfigEntry<>(ConfigKeys.SNAKE_INVERT_NODE_COLORS.getKey(), true));

        //Set Some default Game Constants
        entryArrayList.add(new ConfigEntry<>(ConfigKeys.CYCLES_PER_TICK.getKey(), DEFAULT_CYCLES_PER_TICK));
        entryArrayList.add(new ConfigEntry<>(ConfigKeys.FOOD_PER_CROSSING.getKey(), DEFAULT_FOOD_PER_CROSSING));

        //Save to the Array
        entries = new ConfigEntry[entryArrayList.size()];
        entryArrayList.toArray(entries);
    }

    /**
     * The class that Stores the Entries Key and the Default Value
     *
     * @param <T>
     */
    public static class ConfigEntry<T> {

        private String key;
        private T defaultValue;

        private ConfigEntry(String key, T defaultValue) {
            this.defaultValue = defaultValue;
            this.key = key;
        }

        /**
         * Returns the Default Value
         *
         * @return
         */
        public T getDefaultValue() {
            return defaultValue;
        }

        /**
         * Returns the Key
         *
         * @return
         */
        public String getKey() {
            return key;
        }
    }
}
