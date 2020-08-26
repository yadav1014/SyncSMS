package com.shai.syncsms.di

import androidx.work.WorkManager
import com.shai.syncsms.data.repository.SmsRepository
import org.koin.dsl.module

val appModule = module {

    factory {
        SmsRepository(get(), get())
    }

    single {
        WorkManager.getInstance(get())
    }
}