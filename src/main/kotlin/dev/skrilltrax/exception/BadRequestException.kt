package dev.skrilltrax.exception

class BadRequestException(error: String = "Bad Request", errorCode: Int = 400): BaseException(error, errorCode)