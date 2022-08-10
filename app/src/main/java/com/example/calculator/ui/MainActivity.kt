package com.example.calculator.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.R
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.liveData.observe(this) { resource ->
            showText(resource)
        }

        initViews()
    }

    private fun initViews() {
        initNumberButtons()
        initOperatorButtons()
        initSymbolButtons()
        initSettingsButton()
    }

    private fun initNumberButtons() {
        with(binding) {
            btn0.setOnClickListener { viewModel.onClickNumber("0") }
            btn1.setOnClickListener { viewModel.onClickNumber("1") }
            btn2.setOnClickListener { viewModel.onClickNumber("2") }
            btn3.setOnClickListener { viewModel.onClickNumber("3") }
            btn4.setOnClickListener { viewModel.onClickNumber("4") }
            btn5.setOnClickListener { viewModel.onClickNumber("5") }
            btn6.setOnClickListener { viewModel.onClickNumber("6") }
            btn7.setOnClickListener { viewModel.onClickNumber("7") }
            btn8.setOnClickListener { viewModel.onClickNumber("8") }
            btn9.setOnClickListener { viewModel.onClickNumber("9") }
        }
    }

    private fun initOperatorButtons() {
        with(binding) {
            btnPlus.setOnClickListener { viewModel.onClickOperator(getString(R.string.button_plus)) }
            btnMinus.setOnClickListener { viewModel.onClickOperator(getString(R.string.button_minus)) }
            btnMulti.setOnClickListener { viewModel.onClickOperator(getString(R.string.button_multi)) }
            btnDivide.setOnClickListener { viewModel.onClickOperator(getString(R.string.button_divide)) }
        }
    }

    private fun initSymbolButtons() {
        with(binding) {
            btnC.setOnClickListener { viewModel.onClear() }
            btnDot.setOnClickListener { viewModel.onClickDot() }
            btnPercent.setOnClickListener { viewModel.onClickPercent() }
            btnBackspace.setOnClickListener { viewModel.onClickBackspace() }
            btnResult.setOnClickListener { viewModel.onClickResult() }
        }
    }

    private fun initSettingsButton() {
        binding.btnSettings.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }
    }

    private fun showText(resource: Resource<String>) {
        when(resource) {
            is Resource.Success -> {
                binding.tvWorkField.text = resource.data.trim()
            }

            is Resource.Error -> {
                Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}