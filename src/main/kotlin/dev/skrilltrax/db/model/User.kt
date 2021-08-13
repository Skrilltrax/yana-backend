package dev.skrilltrax.db.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(val username: String, val password: String)
