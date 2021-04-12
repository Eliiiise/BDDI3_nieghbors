package com.example.calculator

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.example.calculator.databinding.CalculatorActivityBinding

class CalculatorActivity : AppCompatActivity() {
    lateinit var binding: CalculatorActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.calculator_activity)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        compute()
    }

    private fun compute() {
        with(binding) {
            computeBtn.setOnClickListener {
                sum()
            }

            operatorFirst.doAfterTextChanged {
                sum()
                enableButton()
            }

            operatorSecond.doAfterTextChanged {
                sum()
                enableButton()
            }
        }
    }

    /**
     * Font d'extension permettant de convertir le text d'un edit text en Int
     */
    private fun EditText.toInt() = text.toString().toIntOrNull() ?: 0

    private fun sum() {
        with(binding) {
            val operandFirst = operatorFirst.text.toString().toIntOrNull() ?: 0
            val operandSecond = operatorSecond.text.toString().toIntOrNull() ?: 0

            computeResult.text = "${operandFirst.plus(operandSecond)}"
        }
    }

    private fun enableButton() {
        with(binding) {
            computeBtn.isEnabled = !operatorFirst.text.isNullOrEmpty() && !operatorSecond.text.isNullOrEmpty()
        }
    }
}
