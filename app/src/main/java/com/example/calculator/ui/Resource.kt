package com.example.calculator.ui

sealed class Resource<T> {
    data class Success(val data: String) : Resource<String>()
    data class Error(val message: String) : Resource<String>()
}
