package com.testspace.debugkeyboard.service;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.testspace.debugkeyboard.ActionsDispatcher;
import com.testspace.debugkeyboard.KeyboardController;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntentHandler {
    private final ActionsDispatcher actionDispatcher;
    private final KeyboardController keyboardController;

    @Inject
    public IntentHandler(ActionsDispatcher actionsDispatcher, KeyboardController keyboardController) {
        this.actionDispatcher = actionsDispatcher;
        this.keyboardController = keyboardController;
    }

    public void handle(@Nullable Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return;
        }

        Bundle extras = intent.getExtras();
        String action = extras.getString(KeyboardService.EXTRA_ACTION, "");

        if (TextUtils.isEmpty(action)) {
            return;
        }


        switch (action) {
            case KeyboardService.ACTION_RESET:
                actionDispatcher.dispatchSizeChanged(keyboardController.getDefaultKeyboardSize());
                break;
            case KeyboardService.ACTION_INCREASE:
                actionDispatcher.dispatchSizeIncPressed();
                break;
            case KeyboardService.ACTION_DECREASE:
                actionDispatcher.dispatchSizeDecPressed();
                break;
        }

    }
}
