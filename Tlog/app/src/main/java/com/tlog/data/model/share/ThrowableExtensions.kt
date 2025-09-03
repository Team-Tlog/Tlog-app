package com.tlog.data.model.share

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toErrorMessage(): String {
    return when (this) {
        is UnknownHostException -> ErrorType.NO_INTERNET.message
        is SocketTimeoutException -> ErrorType.TIMEOUT.message
        is IOException -> ErrorType.NETWORK_ERROR.message
        is HttpException -> {
            when (code()) {
                400 -> ErrorType.BAD_REQUEST.message
                401 -> ErrorType.UNAUTHORIZED.message
                403 -> ErrorType.FORBIDDEN.message
                404 -> ErrorType.NOT_FOUND.message
                409 -> ErrorType.CONFLICT.message
                in 500..599 -> ErrorType.SERVER_ERROR.message
                else -> "서버 오류 (${code()})"
            }
        }
        else -> ErrorType.UNKNOWN_ERROR.message
    }
}