package com.testspace.debugkeyboard;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.testspace.debugkeyboard.util.DisplayInfo;
import com.testspace.debugkeyboard.viewholders.RootViewHolder;
import com.testspace.debugkeyboard.viewholders.ViewCreatedCallback;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RootViewController {
    private final ActionsDispatcher actionsDispatcher;
    private final DisplayInfo displayInfo;
    private final KeyboardController keyboardController;

    @Nullable private View btnIncrease;
    @Nullable private View btnDecrease;
    @NonNull private TextView textViewInfo;
    @Nullable private SeekBar seekBar;

    @Inject
    public RootViewController(RootViewHolder rootViewHolder,
                              DisplayInfo displayInfo,
                              ActionsDispatcher actionsDispatcher,
                              KeyboardController keyboardController) {
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

        represent(textViewInfo, keyboardController.getKeyboardSize(), displayInfo.getHeight());

        if (btnIncrease == null && seekBar == null) {
            textViewInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionsDispatcher.setSize(keyboardController.getDefaultKeyboardSize());
                }
            });
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
                    represent(textViewInfo, seekBar.getProgress(), seekBar.getMax());
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

    private void represent(TextView textView, float value, float total) {
        float density = 1;//displayInfo.getDensity();
//        float value = seekBar.getProgress();
//        float total = seekBar.getMax();
        int percent = (int) ((value / total) * 100f);

        String percentage = String.format(Locale.US,
                "%d%% (%d / %d px)", percent, (int) (value * density), (int) (total * density));

        textView.setText(percentage);
    }
}
