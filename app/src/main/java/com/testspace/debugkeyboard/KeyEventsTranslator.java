package com.testspace.debugkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import androidx.annotation.Nullable;

import com.testspace.debugkeyboard.util.KeyCodeConverter;
import com.testspace.debugkeyboard.viewholders.KeyboardViewHolder;
import com.testspace.debugkeyboard.viewholders.ViewCreatedCallback;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Listens for key events and translates them into actions.
 */
@Singleton
public class KeyEventsTranslator {

    private final Context context;
    private final ActionsDispatcher actionsDispatcher;
    @Nullable
    private KeyboardView keyboardView;

    @Inject
    public KeyEventsTranslator(Context context, KeyboardViewHolder keyboardViewHolder,
                               ActionsDispatcher actionsDispatcher) {
        this.actionsDispatcher = actionsDispatcher;
        this.context = context;
        keyboardView = keyboardViewHolder.getKeyboardView();
        keyboardViewHolder.addCallbackListener(new ViewCreatedCallback<KeyboardView>() {
            @Override
            public void onViewCreated(KeyboardView view) {
                keyboardView = view;
                view.setOnKeyboardActionListener(new KeyboardsActionsListener());
            }
        });
    }


    public void sendKeyPressed(int keycode) {
        if (keyboardView != null) {
            playClick(keycode);
            performAction(keycode);
        }
    }

    private void performAction(int keyCode) {
        if (KeyCodeConverter.isActionDown(keyCode)) {
            actionsDispatcher.dispatchSizeDecPressed();
            return;
        }

        if (KeyCodeConverter.isActionUp(keyCode)) {
            actionsDispatcher.dispatchSizeIncPressed();
            return;
        }
    }

    private void playClick(int keyCode) {
        AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    private class KeyboardsActionsListener implements KeyboardView.OnKeyboardActionListener {
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            sendKeyPressed(primaryCode);
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeUp() {
        }
    }
}
