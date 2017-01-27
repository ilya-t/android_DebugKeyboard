package com.testspace.debugkeyboard.dagger;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.LayoutInflater;

import com.testspace.debugkeyboard.util.DisplayInfo;

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
}
