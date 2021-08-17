package dev.skrilltrax.db.model

import kotlinx.serialization.Serializable

@Serializable
data class NoteModel(var id: Int? = null, val title: String, val body: String)
