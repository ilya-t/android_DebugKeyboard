package com.testspace.debugkeyboard;

import com.testspace.debugkeyboard.util.DisplayInfo;
import com.testspace.debugkeyboard.viewholders.KeyboardViewHolder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyboardController {
    private final KeyboardViewHolder keyboardViewHolder;
    private final DisplayInfo displayInfo;
    private int keyboardSize;

    @Inject
    KeyboardController(ActionsDispatcher actionsDispatcher,
                       KeyboardViewHolder keyboardViewHolder,
                       DisplayInfo displayInfo) {
        actionsDispatcher.addActionListener(new ActionListenerImpl());
        this.keyboardViewHolder = keyboardViewHolder;
        this.displayInfo = displayInfo;
        keyboardSize = getDefaultKeyboardSize();
    }

    private void setKeyboardSize(int keyboardSize) {
        if (keyboardSize < 0 || keyboardSize > displayInfo.getHeight()) {
            return;
        }
        this.keyboardSize = keyboardSize;
        keyboardViewHolder.updateKeyboard(keyboardSize);
    }

    public int getDefaultKeyboardSize() {
        return displayInfo.getHeight() / 3;
    }

    public int getKeyboardSize() {
        return keyboardSize;
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

        @Override
        public void setSize(int size) {
            setKeyboardSize(size);
        }
    }
}
