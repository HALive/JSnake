/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.config;

public enum ConfigKeys {
    WINDOW_DIMENSION("windowDimension"),
    WINDOW_HEIGHT("height"),
    WINDOW_WIDTH("width"),
    FOOD_COLOR("foodColor"),
    SNAKE_HEAD_COLOR("snakeHeadColor"),
    SNAKE_NODE_COLORS("snakeNodeColors"),
    SNAKE_INVERT_NODE_COLORS("invertNodeColors"),
    CYCLES_PER_TICK("cyclesPerTick"),
    FOOD_PER_CROSSING("foodPerCrossing");

    private String key;

    ConfigKeys(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    /**
     * Returns the JSON key for a specific Value.
     *
     * @return
     */
    public String getKey() {
        return key;
    }
}
