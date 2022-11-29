package com.krs.news.data.utils

/**
 * generic class that holds a value with its loading status
 */
sealed class Resource<T> (
    val data:T? = null,
    val message:String? = null
){

    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message:String? = null, data:T? = null) : Resource<T>(data, message)
}
