package com.testspace.debugkeyboard.service;

import android.inputmethodservice.InputMethodService;
import android.os.Handler;

import com.testspace.debugkeyboard.ActionListener;
import com.testspace.debugkeyboard.ActionsDispatcher;
import com.testspace.debugkeyboard.util.InfoRepresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationController {
    private static final int NOTIFICATION_ID = 18;
    private final Handler handler;
    private final InfoRepresenter infoRepresenter;
    private SizeNotification sizeNotification;
    @Inject
    public NotificationController(ActionsDispatcher actionsDispatcher,
                                  InfoRepresenter infoRepresenter,
                                  InputMethodService service) {
        actionsDispatcher.addActionListener(new ActionListenerImpl());
        this.infoRepresenter = infoRepresenter;
        sizeNotification = new SizeNotification(NOTIFICATION_ID, service);
        handler = new Handler();
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
            postUpdate();
        }

        @Override
        public void onSizeDecPressed() {
            postUpdate();
        }

        @Override
        public void setSize(int size) {
            postUpdate();

        }
    }

    private void postUpdate() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                update();
            }
        });
    }
}
