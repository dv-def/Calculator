package com.example.calculator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.utils.Calculator

class MainViewModel(
    private val calculator: Calculator = Calculator()
) : ViewModel() {
    val liveData: LiveData<Resource<String>> = MutableLiveData()

    private var state = MainActivityState("", "", "")

    fun onClickNumber(number: String) {
        state = if (state.operation.isEmpty()) {
            state.copy(
                number1 = state.number1 + number,
                operation = state.operation,
                number2 = state.number2
            )
        } else {
            state.copy(
                number1 = state.number1,
                operation = state.operation,
                number2 = state.number2 + number
            )
        }
        setLiveDataSuccess()
    }

    fun onClickOperator(operator: String) {
        if (state.number1.isEmpty()) {
            state = state.copy(
                number1 = "0.0",
                operation = operator,
                number2 = state.number2
            )

            setLiveDataSuccess()
        } else {
            if (state.operation.isEmpty()) {
                state = state.copy(
                    number1 = state.number1,
                    operation = operator,
                    number2 = state.number2
                )

                setLiveDataSuccess()
            } else {
                if (state.number2.isEmpty()) {
                    state = state.copy(
                        number1 = state.number1,
                        operation = operator,
                        number2 = state.number2
                    )

                    setLiveDataSuccess()
                } else {
                    var result = calculator.result(
                        state.number1.toDouble(),
                        state.operation,
                        state.number2.toDouble()
                    ).toString()

                    if (result.endsWith(".0")) {
                        result = result.dropLast(2)
                    }

                    state = state.copy(
                        number1 = result,
                        operation = operator,
                        number2 = ""
                    )

                    setLiveDataSuccess()
                }
            }
        }
    }

    fun onClickDot() {
        if (state.number1.isEmpty()) {
            state = state.copy(
                number1 = "0.0",
                operation = state.operation,
                number2 = state.number2
            )
            setLiveDataSuccess()
        } else {
            if (state.operation.isEmpty()) {
                if (!state.number1.contains(".")) {
                    state = state.copy(
                        number1 = state.number1 + ".",
                        operation = state.operation,
                        number2 = state.operation
                    )
                    setLiveDataSuccess()
                } else {
                    setLiveDataError("Точка уже есть в первом числе")
                }
            } else {
                if (state.number2.isEmpty()) {
                    state = state.copy(
                        number1 = state.number1,
                        operation = state.operation,
                        number2 = "0.0"
                    )
                    setLiveDataSuccess()
                } else {
                    if (!state.number2.contains(".")) {
                        state = state.copy(
                            number1 = state.number1,
                            operation = state.operation,
                            number2 = state.number2 + "."
                        )
                        setLiveDataSuccess()
                    } else {
                        setLiveDataError("Точка уже есть во втором числе")
                    }
                }
            }
        }
    }

    fun onClickBackspace() {
        if (state.number2.isNotEmpty()) {
            state = state.copy(
                number1 = state.number1,
                operation = state.operation,
                number2 = state.number2.dropLast(1)
            )
        } else {
            if (state.operation.isNotEmpty()) {
                state = state.copy(
                    number1 = state.number1,
                    operation = state.operation.dropLast(1),
                    number2 = state.number2
                )

            } else {
                if (state.number1.isNotEmpty()) {
                    state = state.copy(
                        number1 = state.number1.dropLast(1),
                        operation = state.operation,
                        number2 = state.number2
                    )
                }
            }
        }

        setLiveDataSuccess()
    }

    fun onClickPercent() {
        state = if (state.number1.isEmpty()) {
            state.copy(
                number1 = "0",
                operation = state.operation,
                number2 = state.number2
            )
        } else {
            var result = calculator.percent(state.number1.toDouble()).toString()
            if (result.endsWith(".0")) {
                result = result.dropLast(2)
            }
            state.copy(
                number1 = result,
                operation = state.operation,
                number2 = state.number2
            )
        }

        setLiveDataSuccess()
    }

    fun onClickResult() {
        val hasAllOperators =
            state.number1.isNotEmpty() && state.operation.isNotEmpty() && state.number2.isNotEmpty()

        if (hasAllOperators) {
            if (state.number2.toDouble() == "0".toDouble()) {
                setLiveDataError("Divide by zero")
                return
            }

            var result = calculator.result(
                state.number1.toDouble(),
                state.operation,
                state.number2.toDouble()
            ).toString()

            if (result.endsWith(".0")) {
                result = result.dropLast(2)
            }
            state = state.copy(
                number1 = result,
                operation = "",
                number2 = ""
            )

            setLiveDataSuccess()
        }
    }

    fun onClear() {
        state = state.copy(
            number1 = "",
            operation = "",
            number2 = ""
        )

        setLiveDataSuccess()
    }


    private fun setLiveDataSuccess() {
        liveData.asMutable().postValue(
            Resource.Success("${state.number1}${state.operation}${state.number2}")
        )
    }

    private fun setLiveDataError(message: String) {
        liveData.asMutable().postValue(
            Resource.Error(message)
        )
    }

    private fun <T> LiveData<T>.asMutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("No Live Data")
    }
}