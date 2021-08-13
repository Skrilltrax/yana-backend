package dev.skrilltrax.exception

class UserExistsException(error: String = "User already exists", errorCode: Int = 409): BaseException(error, errorCode)