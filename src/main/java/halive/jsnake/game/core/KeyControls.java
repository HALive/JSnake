package halive.jsnake.game.core;

import static org.newdawn.slick.Input.*;

public enum KeyControls {
    UP(KEY_UP, KEY_W),
    DOWN(KEY_DOWN, KEY_S),
    LEFT(KEY_LEFT, KEY_A),
    RIGHT(KEY_RIGHT, KEY_D),
    ESCAPE(KEY_ESCAPE);
    private int[] validKeycodes;

    KeyControls(int... validKeycodes) {
        this.validKeycodes = validKeycodes;
    }

    public int[] getValidKeycodes() {
        return validKeycodes;
    }

    public boolean isKeycodeVaild(int code) {
        for (int keycode : validKeycodes) {
            if (code == keycode) {
                return true;
            }
        }
        return false;
    }
}
