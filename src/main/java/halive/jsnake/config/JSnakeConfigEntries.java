/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake.config;

public enum JSnakeConfigEntries {
    ;
    private String key;
    private Object defaultValue;

    JSnakeConfigEntries(String key, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }



    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getKey() {
        return key;
    }
}
