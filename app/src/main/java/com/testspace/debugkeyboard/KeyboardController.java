package com.testspace.debugkeyboard;

import com.testspace.debugkeyboard.util.DisplayInfo;
import com.testspace.debugkeyboard.viewholders.KeyboardViewHolder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyboardController {
    private final KeyboardViewHolder keyboardViewHolder;
    private int keyboardSize;

    @Inject
    KeyboardController(ActionsDispatcher actionsDispatcher,
                       KeyboardViewHolder keyboardViewHolder,
                       DisplayInfo displayInfo) {
        actionsDispatcher.addActionListener(new ActionListenerImpl());
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
