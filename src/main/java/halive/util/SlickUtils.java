/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.util;

import org.newdawn.slick.Color;

public class SlickUtils {

    public static Color invertColor(Color c) {
        return new Color(~c.getRed() & 0xFF, ~c.getGreen() & 0xFF, ~c.getBlue() & 0xFF);
    }

    public static Color getColorFromRGBString(String s) {
        int color = Integer.parseInt(s.replace("#", ""), 16) | (0xFF << 24);
        return new Color(color);
    }
}
