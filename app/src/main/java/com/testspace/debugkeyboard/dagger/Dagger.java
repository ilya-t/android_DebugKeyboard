package com.testspace.debugkeyboard.dagger;

import android.content.Context;

public class Dagger {
    private static CoreComponent core;

    public static CoreComponent component() {
        return core;
    }

    public static void init(Context context) {
        core = ComponentFactory.createProdComponent(context);
    }
}
