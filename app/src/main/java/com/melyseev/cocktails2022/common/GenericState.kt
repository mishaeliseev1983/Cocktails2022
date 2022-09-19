package com.melyseev.cocktails2022.common

sealed class GenericState<T>(var data: T? = null, var error: String ="", var loading: Boolean = false) {
    class Success<T>(data: T)     : GenericState<T>(data = data)
    class Loading<T>                : GenericState<T>(loading = true)
    class Error<T>(error: String)   : GenericState<T>(error = error)
}