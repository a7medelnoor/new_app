package com.a7medelnoor.new_app.util

sealed class NetworkResult<T>(
    val data : T? = null,
    val message: String? =null
) {

    class SUCCESS<T>(data: T) : NetworkResult<T>(data)
    class ERROR<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
    class LOADING<T> : NetworkResult<T>()
}