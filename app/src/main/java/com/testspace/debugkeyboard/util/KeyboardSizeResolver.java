package com.testspace.debugkeyboard.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;

import com.testspace.debugkeyboard.R;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyboardSizeResolver {
    private final Resources resources;

    public enum SizeType {
        TINY(R.dimen.tiny_content_height, R.layout.content_small),
        SMALL(R.dimen.small_content_height, R.layout.content_small),
        NORMAL(R.dimen.default_content_height, R.layout.content);

        @DimenRes private final int dimenId;
        @LayoutRes private final int layoutId;

        SizeType(@DimenRes int dimenId, @LayoutRes int layoutId) {
            this.dimenId = dimenId;
            this.layoutId = layoutId;
        }

        public int size(Resources resources) {
            return resources.getDimensionPixelSize(dimenId);
        }
    }

    @Inject
    public KeyboardSizeResolver(Context context) {
        this.resources = context.getResources();
    }

    @LayoutRes
    public int getSizeLayout(int height) {
        return getSizeType(height).layoutId;
    }

    public SizeType getSizeType(int height) {
        if (height >= SizeType.NORMAL.size(resources)) {
            return SizeType.NORMAL;
        } else if (height >= SizeType.SMALL.size(resources)) {
            return SizeType.SMALL;
        } else {
            return SizeType.TINY;
        }
    }
}
