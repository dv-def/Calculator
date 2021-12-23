package com.example.calculator;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(getTheme(ThemeMode.LIGHT)); // по-умолчанию
        setTheme(getTheme(ThemeMode.DARK));
        super.onCreate(savedInstanceState);
    }

    private int getTheme(ThemeMode themeMode) {
        if (themeMode == ThemeMode.DARK) {
            return R.style.Theme_Calculator_Dark;
        }

        return R.style.Theme_Calculator_Light;
    }

    enum ThemeMode {
        LIGHT, DARK
    }
}
