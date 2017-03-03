package com.testspace.debugkeyboard.dagger;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.LayoutInflater;

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
}
