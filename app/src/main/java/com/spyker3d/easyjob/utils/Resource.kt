package com.spyker3d.easyjob.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String) : Resource<T>(message = message)
    class InternetConnectionError<T>(message: String) : Resource<T>(message = message)
    class NotFoundError<T>(message: String) : Resource<T>(message = message)
}
