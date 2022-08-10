package com.example.calculator.utils

class Calculator {
    fun result(num1: Double, operation: String, num2: Double): Double {
        return when(operation) {
            "+" -> sum(num1, num2)
            "-" -> sub(num1, num2)
            "x" -> multiple(num1, num2)
            else -> divide(num1, num2)
        }
    }

    fun percent(num: Double) = num / 100

    private fun sum(num1: Double, num2: Double) = num1 + num2
    private fun sub(num1: Double, num2: Double) = num1 - num2
    private fun multiple(num1: Double, num2: Double) = num1 * num2
    private fun divide(num1: Double, num2: Double) = num1 / num2
}