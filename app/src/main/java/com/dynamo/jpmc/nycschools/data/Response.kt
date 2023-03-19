package com.dynamo.jpmc.nycschools.data

/**
 * Created by tanmaythakar on 3/17/23.
 */
sealed class Response<T : Any> {
    class InProgress<T : Any> : Response<T>()
    class Success<T : Any>(val data: T) : Response<T>()
    class Error<T : Any>(val message: String?) : Response<T>()
}