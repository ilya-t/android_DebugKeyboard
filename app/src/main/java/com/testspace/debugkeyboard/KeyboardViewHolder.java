package com.testspace.debugkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.testspace.debugkeyboard.dagger.Dagger;

import javax.inject.Inject;

/**
 * Created by inflater-ts on 27.01.17.
 */
public class KeyboardViewHolder {
    private final LayoutInflater inflater;
    private final Context context;
    private Keyboard keyboard;

    @Nullable private KeyboardView keyboardView;

    @Inject
    KeyboardViewHolder(Context context, LayoutInflater inflater,
                       Keyboard keyboard) {
        this.context = context;
        this.inflater = inflater;
        this.keyboard = keyboard;
    }

    @NonNull
    public KeyboardView getView() {
        return keyboardView == null ? createView() : keyboardView;
    }

    public KeyboardView createView() {
        keyboardView = (KeyboardView) inflater.inflate(R.layout.keyboard, null);
/*
        int width = getResources().getDisplayMetrics().widthPixels;

        int anomaly_keyboard_top = 980;
        int anomaly_keyboard_bottom = 1690;
        int anomaly_screen_bottom = 1825;
        int anomalyKeyboardHeight = anomaly_keyboard_bottom - anomaly_keyboard_top;

        int height = getResources().getDisplayMetrics().heightPixels / 2;

        // mi5 Anomaly: ScreenSize 1688 KeyboardHeight: 700
        height = 700;
*/
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(Dagger.component().getKeyboardActionListener());
        return keyboardView;
    }
}
