package com.example.calculator

import com.example.calculator.utils.Calculator
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatorTest {
    private lateinit var calculator: Calculator

    @Before
    fun init() {
        calculator = Calculator()
    }

    @Test
    fun testPercent() {
        val expected = 1.0
        val actual = calculator.percent(100.0)
        assertEquals(expected, actual)
    }

    @Test
    fun testSum() {
        val expected = 28.0
        val actual = calculator.result(10.0, "+", 18.0)
        assertEquals(expected, actual)
    }

    @Test
    fun testSub() {
        val expected = 25.0
        val actual = calculator.result(50.0, "-", 25.0)
        assertEquals(expected, actual)
    }

    @Test
    fun testMultiple() {
        val expected = 42.0
        val actual = calculator.result(14.0, "x",3.0)
        assertEquals(expected, actual)
    }

    @Test
    fun testDivide() {
        val expected = 25.0
        val actual = calculator.result(100.0, "/", 4.0)
        assertEquals(expected, actual)
    }

    @Test
    fun testDivideByZero() {
        val expected = Double.POSITIVE_INFINITY
        val actual = calculator.result(1.0, "/", 0.0)
        assertEquals(expected, actual)
    }
}