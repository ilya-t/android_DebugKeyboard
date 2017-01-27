package com.testspace.debugkeyboard;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyboardController {
    private final KeyboardViewHolder keyboardViewHolder;
    private int keyboardSize;

    @Inject
    KeyboardController(KeyboardActionDispatcher keyboardActionDispatcher,
                       KeyboardViewHolder keyboardViewHolder,
                       DisplayInfo displayInfo) {
        keyboardActionDispatcher.setActionListener(new ActionListenerImpl());
        this.keyboardViewHolder = keyboardViewHolder;
        keyboardSize = displayInfo.getHeight() / 3;
    }

    private void setKeyboardSize(int keyboardSize) {
        this.keyboardSize = keyboardSize;
        keyboardViewHolder.updateKeyboard(keyboardSize);
    }

    public int getKeyboardSize() {
        return keyboardSize;
    }

    private class ActionListenerImpl implements ActionListener {
        @Override
        public void onSizeIncPressed() {
            setKeyboardSize(keyboardSize*2);
        }

        @Override
        public void onSizeDecPressed() {
            setKeyboardSize(keyboardSize/2);
        }
    }
}
