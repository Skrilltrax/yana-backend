package dev.skrilltrax.di

import dev.skrilltrax.db.database
import io.github.cdimascio.dotenv.dotenv
import org.koin.dsl.module

val applicationModule = module {
    single { dotenv() }
    single { database(get()) }
}