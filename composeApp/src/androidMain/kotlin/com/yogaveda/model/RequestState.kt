package com.yogaveda.model

sealed class RequestState<out T> {
    object Idle: RequestState<Nothing>()
    object Loading: RequestState<Nothing>()
    class Success<T>(val data: T): RequestState<T>()
    class Error(val exception: Throwable): RequestState<Nothing>()
}