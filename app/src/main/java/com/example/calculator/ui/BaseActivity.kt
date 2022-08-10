package com.example.calculator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getSelectedTheme())
    }

    private fun getSelectedTheme(): Int {
        val sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        return sharedPreferences.getInt(THEME, R.style.Theme_Calculator_Light)
    }

    companion object {
        const val SHARED_PREF = "SETTINGS"
        const val THEME = "THEME"
    }
}