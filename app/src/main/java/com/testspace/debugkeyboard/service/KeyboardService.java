package com.testspace.debugkeyboard.service;

import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.testspace.debugkeyboard.KeyEventsTranslator;
import com.testspace.debugkeyboard.KeyboardController;
import com.testspace.debugkeyboard.dagger.Dagger;
import com.testspace.debugkeyboard.viewholders.KeyboardViewHolder;

import javax.inject.Inject;

public class KeyboardService extends InputMethodService {
    public static final String ACTION_RESET = "action:reset";
    public static final String ACTION_INCREASE = "action:inc";
    public static final String ACTION_DECREASE = "action:dec";
    public static final String EXTRA_ACTION = "extra:action";

    @Inject Bootstrapper bootstrapper;
    @Inject KeyboardController keyboardController;
    @Inject KeyboardViewHolder keyboardViewHolder;
    @Inject KeyEventsTranslator keyEventsTranslator;
    @Inject NotificationController notificationController;
    @Inject IntentHandler intentHandler;

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        notificationController.showNotificationIfSmall();
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);
        notificationController.remove();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getSource() == InputDevice.SOURCE_KEYBOARD) {
            keyEventsTranslator.sendKeyPressed(keyCode);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intentHandler.handle(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationController.remove();
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