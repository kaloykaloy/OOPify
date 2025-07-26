package com.example.myapplication;

import android.view.KeyEvent;

public interface OnBackPressedListener {
    void onBackPressed();

    boolean dispatchKeyEvent(KeyEvent event);
}
