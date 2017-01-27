package com.testspace.debugkeyboard.viewholders;

import android.view.View;

/**
 * Created by oneday on 28/01/2017.
 */
public interface ViewCreatedCallback<T extends View> {
    void onViewCreated(T view);
}
