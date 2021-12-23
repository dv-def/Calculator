package com.example.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends BaseActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(BaseActivity.SHARED_PREF, MODE_PRIVATE);
        int currentTheme = sharedPreferences.getInt(BaseActivity.THEME, R.style.Theme_Calculator_Light);

        RadioButton rbLight = findViewById(R.id.rb_light);
        rbLight.setOnClickListener(onRadioButtonClick());
        rbLight.setChecked(currentTheme == R.style.Theme_Calculator_Light);

        RadioButton rbDark = findViewById(R.id.rb_dark);
        rbDark.setOnClickListener(onRadioButtonClick());
        rbDark.setChecked(currentTheme == R.style.Theme_Calculator_Dark);

        MaterialButton save = findViewById(R.id.btn_save_settings);
        save.setOnClickListener(onClickSave());
    }

    private View.OnClickListener onRadioButtonClick() {
        return view -> {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            switch (view.getId()) {
                case R.id.rb_light:
                    editor.putInt(BaseActivity.THEME, R.style.Theme_Calculator_Light);
                    break;
                case R.id.rb_dark:
                    editor.putInt(BaseActivity.THEME, R.style.Theme_Calculator_Dark);
            }

            editor.apply();
            recreate();
        };
    }

    private View.OnClickListener onClickSave() {
        return view -> startActivity(new Intent(this, MainActivity.class));
    }
}