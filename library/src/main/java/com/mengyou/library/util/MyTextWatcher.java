package com.mengyou.library.util;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HLHT-006 on 2015-12-09.
 */
public class MyTextWatcher implements TextWatcher {

    private TextInputLayout targetInputLayout;
    private String errorMessage;
    private String regular;
    private String emptyMessage;
    private View view;
    private boolean onlyEmpty=false;

    public MyTextWatcher(TextInputLayout targetInputLayout,String emptyMessage)
    {
        this.targetInputLayout = targetInputLayout;
        this.emptyMessage = emptyMessage;
        this.onlyEmpty=true;
    }

    public MyTextWatcher(TextInputLayout targetInputLayout, String errorMessage, String regular, String emptyMessage) {
        this.targetInputLayout = targetInputLayout;
        this.errorMessage = errorMessage;
        this.regular = regular;
        this.emptyMessage = emptyMessage;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d("info","beforeTextChanged");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String value=s.toString();
        if (value.equals(""))
        {
            targetInputLayout.setError(emptyMessage);
            view.setEnabled(false);
        }
        else
        {
            if (!onlyEmpty) {
                Pattern pattern = Pattern.compile(regular);
                Matcher matcher = pattern.matcher(value);
                if (!matcher.matches()) {
                    targetInputLayout.setError(errorMessage);
                    view.setEnabled(false);
                } else {
                    targetInputLayout.setErrorEnabled(false);
                    view.setEnabled(true);
                }
            }
            else
            {
                targetInputLayout.setErrorEnabled(false);
                view.setEnabled(true);
            }
        }
    }
}
