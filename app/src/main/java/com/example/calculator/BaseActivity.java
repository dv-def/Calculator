package com.example.calculator;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public static final String SHARED_PREF = "SETTINGS";
    public static final String THEME = "THEME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getThemeSetting());
    }

    private int getThemeSetting() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        return sharedPreferences.getInt(THEME, R.style.Theme_Calculator_Light);
    }

}
