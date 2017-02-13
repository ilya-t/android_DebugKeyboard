package com.testspace.debugkeyboard.util;

import com.testspace.debugkeyboard.KeyboardController;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InfoRepresenter {
    private final KeyboardController keyboardController;
    private final DisplayInfo displayInfo;

    @Inject
    public InfoRepresenter(DisplayInfo displayInfo,
                           KeyboardController keyboardController) {
        this.keyboardController = keyboardController;
        this.displayInfo = displayInfo;
    }
    public static String represent(float value, float total) {
        int percent = (int) ((value / total) * 100f);

        return String.format(Locale.US,
                "%d%% (%d / %d px)", percent, (int) value, (int) total);
    }

    public String getInfo() {
        return represent(keyboardController.getKeyboardSize(), displayInfo.getHeight());
    }
}
