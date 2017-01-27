package com.testspace.debugkeyboard;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.testspace.debugkeyboard.viewholders.RootViewHolder;
import com.testspace.debugkeyboard.viewholders.ViewCreatedCallback;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RootViewController {
    private final ActionsDispatcher actionsDispatcher;

    @Inject
    public RootViewController(RootViewHolder rootViewHolder,
                              ActionsDispatcher actionsDispatcher) {
        this.actionsDispatcher = actionsDispatcher;
        if (rootViewHolder.getView() != null) {
            onRootCreated(rootViewHolder.getView());
        }

        rootViewHolder.addCallbackListener(new ViewCreatedCallback<ViewGroup>() {
            @Override
            public void onViewCreated(ViewGroup rootView) {
                onRootCreated(rootView);
            }
        });
    }

    private void onRootCreated(@NonNull ViewGroup rootView) {
        rootView.findViewById(R.id.increase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsDispatcher.onSizeIncPressed();
            }
        });
        rootView.findViewById(R.id.decrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsDispatcher.onSizeDecPressed();
            }
        });
    }
}
