package com.testspace.debugkeyboard.viewholders;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.testspace.debugkeyboard.R;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RootViewHolder {
    private final LayoutInflater inflater;
    @Nullable private ViewGroup rootView;
    private List<ViewCreatedCallback<ViewGroup>> callbackList = new CopyOnWriteArrayList<>();

    @Nullable public ViewGroup getView() {
        return rootView;
    }

    @Inject
    public RootViewHolder(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addCallbackListener(ViewCreatedCallback<ViewGroup> callback) {
        if (!callbackList.contains(callback)) {
            callbackList.add(callback);
        }
    }

    ViewGroup createRoot(int height) {
        rootView = (ViewGroup) inflater.inflate(R.layout.keyboard_root_layout, null);
        ViewStub contentStub = (ViewStub) rootView.findViewById(R.id.contentStub);
        if (contentStub != null) {
            if (height >= rootView.getResources().getDimensionPixelSize(R.dimen.default_content_height)) {
                contentStub.setLayoutResource(R.layout.content);
            } else if (height >= rootView.getResources().getDimensionPixelSize(R.dimen.small_content_height)) {
                contentStub.setLayoutResource(R.layout.content_small);
            }

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
