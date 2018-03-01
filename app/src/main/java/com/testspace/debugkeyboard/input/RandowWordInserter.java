package com.testspace.debugkeyboard.input;

import android.inputmethodservice.InputMethodService;
import android.view.inputmethod.InputConnection;

import com.testspace.debugkeyboard.ActionListener;
import com.testspace.debugkeyboard.ActionsDispatcher;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RandowWordInserter implements ActionListener {
    private static final int PUT_CURSOR_AFTER_TEXT = 1;

    private final RandomWordsProvider randomWordsProvider;
    private final InputMethodService inputMethodService;

    @Inject
    public RandowWordInserter(ActionsDispatcher dispatcher,
                              InputMethodService inputMethodService,
                              RandomWordsProvider randomWordsProvider) {
        this.inputMethodService = inputMethodService;
        this.randomWordsProvider = randomWordsProvider;
        dispatcher.addActionListener(this);
    }

    @Override
    public void onRandomWord() {
        InputConnection ic = inputMethodService.getCurrentInputConnection();
        if (ic != null) {
            ic.commitText(randomWordsProvider.getRandomWord() + " ", PUT_CURSOR_AFTER_TEXT);
        }
    }

    @Override
    public void onSizeIncPressed() { /* Do Nothing */ }

    @Override
    public void onSizeDecPressed() { /* Do Nothing */ }

    @Override
    public void setSize(int size) { /* Do Nothing */ }
}
