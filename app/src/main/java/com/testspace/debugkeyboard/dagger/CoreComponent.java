package com.testspace.debugkeyboard.dagger;

import com.testspace.debugkeyboard.service.KeyboardService;

public interface CoreComponent {
    void inject(KeyboardService keyboardService);
}
