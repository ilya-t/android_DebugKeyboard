package com.testspace.debugkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;

/**
 * Created by i-ts on 26.01.17.
 */
public class FixedSizeKeyboard extends Keyboard {
    private int width;
    private int height;

    public FixedSizeKeyboard(Context context, int resId, int modeChange, int width, int height) {
        super(context, resId, modeChange, width, height);
        this.height = height;
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getMinWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
