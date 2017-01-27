package com.testspace.debugkeyboard.dagger;

import com.testspace.debugkeyboard.KeyboardService;

public interface CoreComponent {
    void inject(KeyboardService keyboardService);
}
