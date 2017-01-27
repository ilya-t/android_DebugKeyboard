package com.testspace.debugkeyboard.dagger;

import android.inputmethodservice.KeyboardView;
import android.view.ViewGroup;

import com.testspace.debugkeyboard.KeyboardActionDispatcher;
import com.testspace.debugkeyboard.KeyboardController;
import com.testspace.debugkeyboard.KeyboardService;

public interface CoreComponent {
    void inject(KeyboardService keyboardService);

    KeyboardActionDispatcher getKeyboardActionListener();//FIXME
}
