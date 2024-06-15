package me.intuit.cat.data.utils.generic

sealed class Result<out T> {
    data class Success<out T>(val data : T) : Result<T>()
    data class Error<out T>(val message: String? = "Unknown error", val data: T? = null): Result<T>()
}