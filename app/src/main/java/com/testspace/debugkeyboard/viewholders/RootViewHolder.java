package com.testspace.debugkeyboard.viewholders;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.testspace.debugkeyboard.R;
import com.testspace.debugkeyboard.util.KeyboardSizeResolver;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RootViewHolder {
    private final LayoutInflater inflater;
    private final KeyboardSizeResolver sizeResolver;
    @Nullable private ViewGroup rootView;
    private List<ViewCreatedCallback<ViewGroup>> callbackList = new CopyOnWriteArrayList<>();

    @Nullable public ViewGroup getView() {
        return rootView;
    }

    @Inject
    public RootViewHolder(LayoutInflater inflater,
                          KeyboardSizeResolver sizeResolver) {
        this.inflater = inflater;
        this.sizeResolver = sizeResolver;
    }

    public void addCallbackListener(ViewCreatedCallback<ViewGroup> callback) {
        if (!callbackList.contains(callback)) {
            callbackList.add(callback);
        }
    }

    ViewGroup createRoot(int height) {
        rootView = (ViewGroup) inflater.inflate(R.layout.keyboard_root_layout, null);
        ViewStub contentStub = rootView.findViewById(R.id.contentStub);
        if (contentStub != null) {
            contentStub.setLayoutResource(sizeResolver.getSizeLayout(height));

            if (contentStub.getLayoutResource() > 0) {
                contentStub.inflate();
            }
        }
        notifyRootCreated();
        return rootView;
    }

    private void notifyRootCreated() {
        for (ViewCreatedCallback<ViewGroup> callback : callbackList) {
            callback.onViewCreated(rootView);
        }
    }
}
