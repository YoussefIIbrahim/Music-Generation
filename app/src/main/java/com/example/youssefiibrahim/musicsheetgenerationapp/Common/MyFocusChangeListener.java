package com.example.youssefiibrahim.musicsheetgenerationapp.Common;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.youssefiibrahim.musicsheetgenerationapp.R;

public class MyFocusChangeListener implements View.OnFocusChangeListener {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if((v.getId() == R.id.edtNewEmail || v.getId() == R.id.edtNewPassword || v.getId() == R.id.edtNewUsername
         || v.getId() == R.id.edtPassword || v.getId() == R.id.edtUser) && !hasFocus) {

            InputMethodManager imm =  (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        }
    }
}
