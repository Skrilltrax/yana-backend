package dev.skrilltrax.db.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(val id: Int? = null, val username: String, val password: String)
