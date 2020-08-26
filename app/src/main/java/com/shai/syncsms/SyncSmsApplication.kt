package com.shai.syncsms

import android.app.Application
import com.shai.syncsms.di.appModule
import com.shai.syncsms.di.dbModule
import com.shai.syncsms.di.networkModule
import com.shai.syncsms.worker.SyncSmsWorkerHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SyncSmsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SyncSmsApplication)
            modules(
                appModule,
                networkModule,
                dbModule
            )
        }

        SyncSmsWorkerHelper.runPeriodicWorker()
    }
}