package com.testspace.debugkeyboard.dagger;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.LayoutInflater;

import com.testspace.debugkeyboard.DisplayInfo;
import com.testspace.debugkeyboard.FixedSizeKeyboard;
import com.testspace.debugkeyboard.KeyboardActionDispatcher;
import com.testspace.debugkeyboard.KeyboardViewHolder;
import com.testspace.debugkeyboard.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CoreModule {
    private final Context context;
    private final InputMethodService inputMethodService;

    public CoreModule(Context context) {
        this.context = context;
        inputMethodService = (InputMethodService) context;
    }

    @Provides
    InputMethodService providerService() {
        return inputMethodService;
    }

    @Provides
    LayoutInflater provideInflater() {
        return inputMethodService.getLayoutInflater();
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    DisplayInfo provideDisplayInfo(Context context) {
        return new DisplayInfo(context);
    }

    @Provides
    @Singleton
    Keyboard provideKeyboard(Context context, DisplayInfo displayInfo) {
        return new FixedSizeKeyboard(context, R.xml.gerty, Keyboard.KEYCODE_MODE_CHANGE,
                displayInfo.getWidth(),
                displayInfo.getHeight() / 3);
    }
}
