package com.testspace.debugkeyboard;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActionsDispatcher implements ActionListener{
    private List<ActionListener> listenerList = new CopyOnWriteArrayList<>();

    @Inject
    public ActionsDispatcher() {
    }

    public void addActionListener(ActionListener listener) {
        if (!listenerList.contains(listener)) {
            listenerList.add(listener);
        }
    }

    @Override
    public void onSizeIncPressed() {
        for (ActionListener listener : listenerList) {
            listener.onSizeIncPressed();
        }
    }

    @Override
    public void onSizeDecPressed() {
        for (ActionListener listener : listenerList) {
            listener.onSizeDecPressed();
        }
    }

    @Override
    public void setSize(int size) {
        for (ActionListener listener : listenerList) {
            listener.setSize(size);
        }

    }
}
