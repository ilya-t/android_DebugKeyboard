package com.testspace.debugkeyboard;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActionsDispatcher {
    private List<ActionListener> listenerList = new CopyOnWriteArrayList<>();

    @Inject
    public ActionsDispatcher() {
    }

    public void addActionListener(ActionListener listener) {
        if (!listenerList.contains(listener)) {
            listenerList.add(listener);
        }
    }

    public void dispatchSizeIncPressed() {
        for (ActionListener listener : listenerList) {
            listener.onSizeIncPressed();
        }
    }

    public void dispatchSizeDecPressed() {
        for (ActionListener listener : listenerList) {
            listener.onSizeDecPressed();
        }
    }

    public void dispatchSizeChanged(int size) {
        for (ActionListener listener : listenerList) {
            listener.setSize(size);
        }
    }

    public void dispatchRandomWord() {
        for (ActionListener listener : listenerList) {
            listener.onRandomWord();
        }
    }
}
