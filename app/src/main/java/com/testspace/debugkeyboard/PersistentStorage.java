package com.testspace.debugkeyboard;

import android.content.Context;
import android.content.SharedPreferences;

import com.testspace.debugkeyboard.util.DisplayInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersistentStorage {
    private static final String PREF_NAME = "prefs";
    private static final String KEY_KEYBOARD_HEIGHT = "keyboard_height";

    private final Context context;
    private final DisplayInfo displayInfo;

    private int keyboardHeight = -1;

    @Inject
    public PersistentStorage(Context c, DisplayInfo di) {
        context = c;
        displayInfo = di;
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public int getSavedHeightPx() {
        if (keyboardHeight != -1) {
            return keyboardHeight;
        }
        return getPrefs().getInt(KEY_KEYBOARD_HEIGHT, getDefaultKeyboardHeight());
    }

    public void setSavedHeightPx(int heightPx) {
        if (heightPx > 0) {
            keyboardHeight = heightPx;
            getPrefs().edit().putInt(KEY_KEYBOARD_HEIGHT, heightPx).apply();
        }
    }

    public int getDefaultKeyboardHeight() {
        return displayInfo.getHeight() / 3;
    }
}
