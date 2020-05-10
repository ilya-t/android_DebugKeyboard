package com.testspace.debugkeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.testspace.debugkeyboard.util.DisplayInfo;
import com.testspace.debugkeyboard.util.InfoRepresenter;
import com.testspace.debugkeyboard.viewholders.RootViewHolder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RootViewController {
    private final ActionsDispatcher actionsDispatcher;
    private final DisplayInfo displayInfo;
    private final KeyboardController keyboardController;
    private final InfoRepresenter infoRepresenter;
    private final Resources resources;

    @Nullable private View btnIncrease;
    @Nullable private View btnDecrease;
    @Nullable private TextView textViewInfo;
    @Nullable private SeekBar seekBar;
    @Nullable private View btnRandom;

    @Inject
    public RootViewController(Context context,
                              RootViewHolder rootViewHolder,
                              InfoRepresenter infoRepresenter,
                              DisplayInfo displayInfo,
                              ActionsDispatcher actionsDispatcher,
                              KeyboardController keyboardController) {
        this.resources = context.getResources();
        this.infoRepresenter = infoRepresenter;
        this.displayInfo = displayInfo;
        this.keyboardController = keyboardController;
        this.actionsDispatcher = actionsDispatcher;
        if (rootViewHolder.getView() != null) {
            onRootCreated(rootViewHolder.getView());
        }

        rootViewHolder.addCallbackListener(this::onRootCreated);
    }

    private void onRootCreated(@NonNull ViewGroup rootView) {
        btnIncrease = rootView.findViewById(R.id.increase);
        btnDecrease = rootView.findViewById(R.id.decrease);
        textViewInfo = rootView.findViewById(R.id.textViewInfo);
        seekBar = rootView.findViewById(R.id.seekBar);
        btnRandom = rootView.findViewById(R.id.random_word);

        if (seekBar != null) {
            seekBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            seekBar.getThumb().setColorFilter(resources.getColor(R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
        }

        if (textViewInfo != null) {
            textViewInfo.setText(infoRepresenter.getInfo());

            if (btnIncrease == null && seekBar == null) {
                textViewInfo.setOnClickListener(v ->
                        actionsDispatcher.dispatchSizeChanged(
                                keyboardController.getDefaultKeyboardSize()));
            }
        }

        if (btnRandom != null) {
            btnRandom.setOnClickListener(v -> actionsDispatcher.dispatchRandomWord());
        }

        if (btnIncrease != null) {
            btnIncrease.setOnClickListener(v -> actionsDispatcher.dispatchSizeIncPressed());
        }

        if (btnDecrease != null) {
            btnDecrease.setOnClickListener(v -> actionsDispatcher.dispatchSizeDecPressed());
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
                public void onStopTrackingTouch(SeekBar seekBar) {
                    actionsDispatcher.dispatchSizeChanged(seekBar.getProgress());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
            });
        }
    }
}
