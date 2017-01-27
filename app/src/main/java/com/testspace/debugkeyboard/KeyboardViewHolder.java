package com.testspace.debugkeyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testspace.debugkeyboard.dagger.Dagger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyboardViewHolder {
    private final LayoutInflater inflater;
    private final DisplayInfo displayInfo;
    private final Context context;
    private final InputMethodService service;

    @Nullable private KeyboardView keyboardView;
    private ViewGroup rootView;

    @Inject
    KeyboardViewHolder(Context context,
                       LayoutInflater inflater,
                       DisplayInfo displayInfo,
                       InputMethodService service) {
        this.context = context;
        this.service = service;
        this.displayInfo = displayInfo;
        this.inflater = inflater;
    }

    @Nullable
    public KeyboardView getKeyboardView() {
        return keyboardView;
    }

    public View createView(int height) {
        keyboardView = createRootView();
        keyboardView.setKeyboard(createKeyboard(height));
        keyboardView.setOnKeyboardActionListener(Dagger.component().getKeyboardActionListener());
        return rootView;
    }

    private KeyboardView createRootView() {
        rootView = (ViewGroup) inflater.inflate(R.layout.keyboard_root_layout, null);
        rootView.findViewById(R.id.increase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dagger.component().getKeyboardActionListener().onKey(KeyEvent.KEYCODE_DPAD_UP, new int[]{KeyEvent.KEYCODE_DPAD_UP});
            }
        });
        rootView.findViewById(R.id.decrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dagger.component().getKeyboardActionListener().onKey(KeyEvent.KEYCODE_DPAD_DOWN, new int[]{KeyEvent.KEYCODE_DPAD_DOWN});
            }
        });
        return (KeyboardView) rootView.findViewById(R.id.keyboardView);
        //inflater.inflate(R.layout.keyboard, null)
    }
/*
        int width = getResources().getDisplayMetrics().widthPixels;

        int anomaly_keyboard_top = 980;
        int anomaly_keyboard_bottom = 1690;
        int anomaly_screen_bottom = 1825;
        int anomalyKeyboardHeight = anomaly_keyboard_bottom - anomaly_keyboard_top;

        int height = getResou1rces().getDisplayMetrics().heightPixels / 2;

        // mi5 Anomaly: ScreenSize 1688 KeyboardHeight: 700
        height = 700;
*/

    private Keyboard createKeyboard(int height) {
        return new FixedSizeKeyboard(context, R.xml.gerty, 0,
                displayInfo.getWidth(), height);
    }

    public void updateKeyboard(int height) {
        service.setInputView(createView(height));
    }
}
