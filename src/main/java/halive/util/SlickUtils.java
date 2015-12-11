package halive.util;

import org.newdawn.slick.Color;

public class SlickUtils {
    public static Color inverColor(Color c) {
        return new Color(~c.getRed() & 0xFF,~c.getGreen() & 0xFF, ~c.getBlue() & 0xFF);
    }
}
