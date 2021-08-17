package dev.skrilltrax.db.dao

interface BaseDao<T> {
    fun mapToModel(): T
}