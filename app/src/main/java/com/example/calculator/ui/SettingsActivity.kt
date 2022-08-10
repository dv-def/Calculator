package com.example.calculator.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.content.Intent
import com.example.calculator.R
import com.example.calculator.databinding.ActivitySettingsBinding

class SettingsActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        val currentTheme = sharedPreferences.getInt(THEME, R.style.Theme_Calculator_Light)

        with(binding) {
            rbLight.isChecked = currentTheme == R.style.Theme_Calculator_Light
            rbLight.setOnClickListener {
                onRadioButtonClick(it.id)
            }

            rbDark.isChecked = currentTheme == R.style.Theme_Calculator_Dark
            rbDark.setOnClickListener {
                onRadioButtonClick(it.id)
            }

            btnSaveSettings.setOnClickListener {
                startActivity(
                    Intent(
                        this@SettingsActivity,
                        MainActivity::class.java
                    )
                )
            }
        }
    }

    private fun onRadioButtonClick(viewId: Int) {
        val editor = sharedPreferences.edit()
        when (viewId) {
            R.id.rb_light -> editor.putInt(THEME, R.style.Theme_Calculator_Light)
            R.id.rb_dark -> editor.putInt(THEME, R.style.Theme_Calculator_Dark)
        }
        editor.apply()
        recreate()
    }
}