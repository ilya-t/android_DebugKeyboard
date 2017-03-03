package com.testspace.debugkeyboard.util;

import android.content.Context;
import android.util.DisplayMetrics;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DisplayInfo {
    private final Context context;

    @Inject
    public DisplayInfo(Context context) {
        this.context = context;
    }

    public int getWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public int getHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public float getDensity() {
        return context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT;
    }
}
