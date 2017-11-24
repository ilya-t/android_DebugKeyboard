package com.testspace.debugkeyboard.service;

import android.inputmethodservice.InputMethodService;
import android.os.Handler;

import com.testspace.debugkeyboard.ActionListener;
import com.testspace.debugkeyboard.ActionsDispatcher;
import com.testspace.debugkeyboard.KeyboardController;
import com.testspace.debugkeyboard.util.InfoRepresenter;
import com.testspace.debugkeyboard.util.KeyboardSizeResolver;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.testspace.debugkeyboard.util.KeyboardSizeResolver.SizeType.NORMAL;
import static com.testspace.debugkeyboard.util.KeyboardSizeResolver.SizeType.TINY;

@Singleton
public class NotificationController {
    private static final int NOTIFICATION_ID = 18;
    private final Handler handler;
    private final InfoRepresenter infoRepresenter;
    private final KeyboardSizeResolver keyboardSizeResolver;
    private final KeyboardController keyboardController;
    private SizeNotification sizeNotification;
    @Inject
    public NotificationController(ActionsDispatcher actionsDispatcher,
                                  InfoRepresenter infoRepresenter,
                                  InputMethodService service,
                                  KeyboardSizeResolver keyboardSizeResolver,
                                  KeyboardController keyboardController) {
        this.keyboardSizeResolver = keyboardSizeResolver;
        this.keyboardController = keyboardController;
        this.infoRepresenter = infoRepresenter;
        sizeNotification = new SizeNotification(NOTIFICATION_ID, service);
        handler = new Handler();
        actionsDispatcher.addActionListener(new ActionListenerImpl());
    }

    public void remove() {
        sizeNotification.remove();
    }

    public void update() {
        sizeNotification.setInfo(infoRepresenter.getInfo()).update();
    }

    private class ActionListenerImpl implements ActionListener {
        @Override
        public void onSizeIncPressed() {
            showNotificationIfSmall();
        }

        @Override
        public void onSizeDecPressed() {
            showNotificationIfSmall();
        }

        @Override
        public void onRandomWord() { }

        @Override
        public void setSize(int size) {
            showNotificationIfSmall();
        }
    }

    public void showNotificationIfSmall() {
        if (keyboardSizeResolver.getSizeType(keyboardController.getKeyboardSize()) == NORMAL) {
            handler.post(this::remove);
        } else {
            handler.post(this::update);
        }
    }
}
