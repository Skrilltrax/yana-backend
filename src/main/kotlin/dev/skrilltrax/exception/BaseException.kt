package dev.skrilltrax.exception

open class BaseException(error: String, errorCode: Int, cause: Throwable? = null) : Exception(error, cause)