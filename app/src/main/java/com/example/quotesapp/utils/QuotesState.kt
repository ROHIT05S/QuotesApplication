package com.example.quotesapp.utils

sealed class QuotesState<out T> {
    data class Success<T>(val data: T) : QuotesState<T>()
    data class Error(val message: String) : QuotesState<Nothing>()
    object Loading : QuotesState<Nothing>()
}