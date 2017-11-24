package com.testspace.debugkeyboard.viewholders;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.testspace.debugkeyboard.FixedSizeKeyboard;
import com.testspace.debugkeyboard.R;
import com.testspace.debugkeyboard.util.DisplayInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyboardViewHolder {
    private final DisplayInfo displayInfo;
    private final Context context;
    private final InputMethodService service;
    private final RootViewHolder rootViewHolder;

    @Nullable private KeyboardView keyboardView;
    private List<ViewCreatedCallback<KeyboardView>> callbackList = new CopyOnWriteArrayList<>();

    @Inject
    KeyboardViewHolder(Context context,
                       RootViewHolder rootViewHolder,
                       DisplayInfo displayInfo,
                       InputMethodService service) {
        this.context = context;
        this.service = service;
        this.rootViewHolder = rootViewHolder;
        this.displayInfo = displayInfo;
    }

    @Nullable
    public KeyboardView getKeyboardView() {
        return keyboardView;
    }

    public View createView(int height) {
        ViewGroup rootView = rootViewHolder.createRoot(height);
        keyboardView = rootView.findViewById(R.id.keyboardView);
        keyboardView.setKeyboard(createKeyboard(height));
        for (ViewCreatedCallback<KeyboardView> callback : callbackList) {
            callback.onViewCreated(keyboardView);
        }
        return rootView;
    }

    public void updateKeyboard(int height) {
        service.setInputView(createView(height));
    }

    private Keyboard createKeyboard(int height) {
        return new FixedSizeKeyboard(context, R.xml.gerty, 0,
                displayInfo.getWidth(), height);
    }

    public void addCallbackListener(ViewCreatedCallback<KeyboardView> callback) {
        if (!callbackList.contains(callback)) {
            callbackList.add(callback);
        }
    }
}
