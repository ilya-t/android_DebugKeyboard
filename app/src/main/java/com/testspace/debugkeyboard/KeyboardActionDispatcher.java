package com.testspace.debugkeyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.content.Context.AUDIO_SERVICE;

@Singleton
public class KeyboardActionDispatcher implements KeyboardView.OnKeyboardActionListener {
    private boolean caps = false;

    @Nullable
    private ActionListener actionListener;
    private Context context;
    private InputMethodService service;
    private KeyboardViewHolder keyboardViewHolder;

    @Inject
    public KeyboardActionDispatcher(Context context,
                                    KeyboardViewHolder keyboardViewHolder,
                                    InputMethodService service) {
        this.keyboardViewHolder = keyboardViewHolder;
        this.service = service;
        this.context = context;
    }

    public void setActionListener(@Nullable ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Log.d("_debug_", String.format(Locale.US, "Key: %d", primaryCode));
        KeyboardView keyboardView = keyboardViewHolder.getKeyboardView();
        if (keyboardView == null) {
            return;
        }

        InputConnection ic = service.getCurrentInputConnection();
        playClick(primaryCode);
        performAction(primaryCode);
        if (1==1)return;
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboardView.getKeyboard().setShifted(caps);
                keyboardView.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
        }
    }

    private void performAction(int keyCode) {
        if (actionListener == null) {
            return;
        }

        if (KeyCodeConverter.isActionDown(keyCode)) {
            actionListener.onSizeDecPressed();
            Log.d("_debug_", String.format(Locale.US, "DEC SIZE"));
            return;
        }

        if (KeyCodeConverter.isActionUp(keyCode)) {
            actionListener.onSizeIncPressed();
            Log.d("_debug_", String.format(Locale.US, "INC SIZE"));
            return;
        }

        Log.d("_debug_", String.format(Locale.US, "Unknown: %d", keyCode));
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
