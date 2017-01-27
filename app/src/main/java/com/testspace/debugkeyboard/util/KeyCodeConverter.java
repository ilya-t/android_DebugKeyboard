package com.testspace.debugkeyboard.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static android.view.KeyEvent.KEYCODE_DPAD_DOWN;
import static android.view.KeyEvent.KEYCODE_DPAD_UP;
import static android.view.KeyEvent.KEYCODE_MINUS;
import static android.view.KeyEvent.KEYCODE_PAGE_DOWN;
import static android.view.KeyEvent.KEYCODE_PAGE_UP;
import static android.view.KeyEvent.KEYCODE_PLUS;

/**
 * Created by i-ts on 27.01.17.
 */

public class KeyCodeConverter {

    private static final Set<Integer> upKeyCodes = new HashSet<>(Arrays.asList(
            KEYCODE_DPAD_UP,
            KEYCODE_PAGE_UP,
            KEYCODE_PLUS
    ));

    private static final Set<Integer> downKeyCodes = new HashSet<>(Arrays.asList(
            KEYCODE_DPAD_DOWN,
            KEYCODE_PAGE_DOWN,
            KEYCODE_MINUS
    ));


    public static boolean isActionUp(int keyCode) {
        return upKeyCodes.contains(keyCode);
    }

    public static boolean isActionDown(int keyCode) {
        return downKeyCodes.contains(keyCode);
    }
}
