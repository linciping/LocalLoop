package com.mengyou.library.util;

import android.support.design.widget.TextInputLayout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HLHT-006 on 2015-12-09.
 */
public class ValidateUtil {

    private TextInputLayout targetInputLayout;
    private String errorMessage;
    private String regular;
    private String emptyMessage;
    private EditText targetEditText;
    private MyTextWatcher watcher;
    private View view;
    private boolean onlyEmpty = false;


    public ValidateUtil(TextInputLayout targetInputLayout, String emptyMessage) {
        this.targetInputLayout = targetInputLayout;
        this.emptyMessage = emptyMessage;
        this.targetEditText = targetInputLayout.getEditText();
        onlyEmpty = true;
    }

    public ValidateUtil(TextInputLayout targetInputLayout, String errorMessage, String regular, String emptyMessage) {
        this.targetInputLayout = targetInputLayout;
        this.errorMessage = errorMessage;
        this.regular = regular;
        this.emptyMessage = emptyMessage;
        this.targetEditText = targetInputLayout.getEditText();
    }

    public void setView(View view) {
        this.view = view;
    }

    public void startValidate() {
        targetEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                    @Override
                                                    public void onFocusChange(View v, boolean hasFocus) {
                                                        if (!hasFocus) {
                                                            String value = targetEditText.getText().toString();
                                                            if (value.equals("")) {
                                                                targetInputLayout.setError(emptyMessage);
                                                                view.setEnabled(false);
                                                            } else {
                                                                if (!onlyEmpty) {
                                                                    Pattern pattern = Pattern.compile(regular);
                                                                    Matcher matcher = pattern.matcher(value);
                                                                    if (!matcher.matches()) {
                                                                        targetInputLayout.setError(errorMessage);
                                                                        view.setEnabled(false);
                                                                    } else {
                                                                        view.setEnabled(false);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            String value = targetEditText.getText().toString();
                                                            if (value.equals("")) {
                                                                targetInputLayout.setError(emptyMessage);
                                                                view.setEnabled(false);
                                                            }
                                                        }
                                                    }
                                                }

        );
        if (!onlyEmpty)

        {
            targetEditText.addTextChangedListener(watcher = new MyTextWatcher(targetInputLayout, errorMessage, regular, emptyMessage));
        } else

        {
            targetEditText.addTextChangedListener(watcher = new MyTextWatcher(targetInputLayout, emptyMessage));
        }

        watcher.setView(view);
    }
}
