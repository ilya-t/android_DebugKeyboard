package com.testspace.debugkeyboard;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
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
        final TextView textView = (TextView) rootView.findViewById(R.id.textViewInfo);
        SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);

        seekBar.setMax(displayInfo.getHeight());
        seekBar.setProgress(keyboardController.getKeyboardSize());
        represent(textView, seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                represent(textView, seekBar);
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

    private void represent(TextView textView, SeekBar seekBar) {
        float value = seekBar.getProgress();
        float total = seekBar.getMax();
        float percent = (value / total) * 100f;
        String percentage = ((int) percent) + "% (" + (int) value + "/" + (int) total + " px)";
        textView.setText(percentage);

    }
}
