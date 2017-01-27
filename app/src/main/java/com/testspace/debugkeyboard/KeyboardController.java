package com.testspace.debugkeyboard;

import android.util.Log;
import android.view.LayoutInflater;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyboardController {
    private int keyboardSize;

    @Inject
    KeyboardController(KeyboardActionDispatcher keyboardActionDispatcher) {
        keyboardActionDispatcher.setActionListener(new ActionListenerImpl());
    }

    private void setKeyboardSize(int keyboardSize) {
        this.keyboardSize = keyboardSize;
        invalidateKeyboard();
    }

    private void invalidateKeyboard() {
        Log.d("_debug_", String.format(Locale.US, "Keyboard should change!"));
    }

    private class ActionListenerImpl implements ActionListener {
        @Override
        public void onSizeIncPressed() {
            setKeyboardSize(keyboardSize+1);
        }

        @Override
        public void onSizeDecPressed() {
            setKeyboardSize(keyboardSize-1);
        }
    }
}
