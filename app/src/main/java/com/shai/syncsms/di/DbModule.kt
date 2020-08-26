package com.shai.syncsms.di

import com.shai.syncsms.data.db.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single {
        AppDatabase.getInstance(get())
    }
}
