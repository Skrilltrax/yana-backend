package dev.skrilltrax.di

import dev.skrilltrax.db.database
import dev.skrilltrax.services.NotesService
import dev.skrilltrax.services.UserService
import io.github.cdimascio.dotenv.dotenv
import org.koin.dsl.module

val applicationModule = module {
    single { dotenv() }
    single { database(get()) }
    single { UserService(get(), get()) }
    single { NotesService(get()) }
}