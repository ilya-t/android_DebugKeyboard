package com.testspace.debugkeyboard;

import android.inputmethodservice.InputMethodService;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;

import com.testspace.debugkeyboard.dagger.Dagger;

import javax.inject.Inject;

public class KeyboardService extends InputMethodService {
    @Inject
    KeyboardController keyboardController;
    @Inject
    KeyboardViewHolder keyboardViewHolder;
    @Inject
    KeyboardActionDispatcher keyboardActionDispatcher;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getSource() == InputDevice.SOURCE_KEYBOARD) {
            keyboardActionDispatcher.onKey(keyCode, new int[]{keyCode});
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Dagger.init(this);
        Dagger.component().inject(this);
        keyboardController.toString();
    }

    @Override
    public View onCreateInputView() {
        return keyboardViewHolder.createView();
    }


}