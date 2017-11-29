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
        MICRO(R.dimen.micro_content_height, R.layout.content_micro),
        TINY(R.dimen.tiny_content_height, R.layout.content_small),
        SMALL(R.dimen.small_content_height, R.layout.content_small),
        NORMAL(R.dimen.default_content_height, R.layout.content);

        @DimenRes private final int minSupportedSize;
        @LayoutRes private final int layoutId;

        SizeType(@DimenRes int minSupportedSize, @LayoutRes int layoutId) {
            this.minSupportedSize = minSupportedSize;
            this.layoutId = layoutId;
        }

        public int size(Resources resources) {
            return resources.getDimensionPixelSize(minSupportedSize);
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
        } else if (height >= SizeType.TINY.size(resources)) {
            return SizeType.TINY;
        } else {
            return SizeType.MICRO;
        }
    }
}
