package com.testspace.debugkeyboard;

import android.inputmethodservice.InputMethodService;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;

import com.testspace.debugkeyboard.dagger.Dagger;
import com.testspace.debugkeyboard.viewholders.KeyboardViewHolder;

import javax.inject.Inject;

public class KeyboardService extends InputMethodService {
    @Inject KeyboardController keyboardController;
    @Inject
    KeyboardViewHolder keyboardViewHolder;
    @Inject KeyEventsTranslator keyEventsTranslator;
    @Inject RootViewController rootViewController;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getSource() == InputDevice.SOURCE_KEYBOARD) {
            keyEventsTranslator.sendKeyPressed(keyCode);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Dagger.init(this);
        Dagger.component().inject(this);
    }

    @Override
    public View onCreateInputView() {
        return keyboardViewHolder.createView(keyboardController.getKeyboardSize());
    }


}