package com.testspace.debugkeyboard;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.testspace.debugkeyboard.util.DisplayInfo;
import com.testspace.debugkeyboard.util.InfoRepresenter;
import com.testspace.debugkeyboard.viewholders.RootViewHolder;
import com.testspace.debugkeyboard.viewholders.ViewCreatedCallback;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RootViewController {
    private final ActionsDispatcher actionsDispatcher;
    private final DisplayInfo displayInfo;
    private final KeyboardController keyboardController;
    private final InfoRepresenter infoRepresenter;

    @Nullable private View btnIncrease;
    @Nullable private View btnDecrease;
    @Nullable private TextView textViewInfo;
    @Nullable private SeekBar seekBar;

    @Inject
    public RootViewController(RootViewHolder rootViewHolder,
                              InfoRepresenter infoRepresenter,
                              DisplayInfo displayInfo,
                              ActionsDispatcher actionsDispatcher,
                              KeyboardController keyboardController) {
        this.infoRepresenter = infoRepresenter;
        this.displayInfo = displayInfo;
        this.keyboardController = keyboardController;
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
        btnIncrease = rootView.findViewById(R.id.increase);
        btnDecrease = rootView.findViewById(R.id.decrease);
        textViewInfo = (TextView) rootView.findViewById(R.id.textViewInfo);
        seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);

        if (textViewInfo != null) {
            textViewInfo.setText(infoRepresenter.getInfo());

            if (btnIncrease == null && seekBar == null) {
                textViewInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionsDispatcher.setSize(keyboardController.getDefaultKeyboardSize());
                    }
                });
            }
        }


        if (btnIncrease != null) {
            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionsDispatcher.onSizeIncPressed();
                }
            });
        }

        if (btnDecrease != null) {
            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionsDispatcher.onSizeDecPressed();
                }
            });
        }

        if (seekBar != null) {
            seekBar.setMax(displayInfo.getHeight());
            seekBar.setProgress(keyboardController.getKeyboardSize());


            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    textViewInfo.setText(
                            InfoRepresenter.represent(seekBar.getProgress(), seekBar.getMax()));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    actionsDispatcher.setSize(seekBar.getProgress());
                }
            });
        }
    }
}
