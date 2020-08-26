package com.shai.syncsms.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shai.syncsms.data.repository.SmsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

class SyncSmsWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params), KoinComponent {

    val smsRepository: SmsRepository by inject()

    companion object{
        const val UNIQUE_NAME = "SYNC_SMS_WORKER"
        val REPEAT_TIME_INTERVAL_UNITS = TimeUnit.MINUTES
        const val REPEAT_TIME_INTERVAL_IN_MINUTES = 15L
    }
    override fun doWork(): Result {
        try {
            smsRepository.syncSms()
            return Result.success()
        } catch (throwable: Throwable) {
            return Result.failure()
        }
    }
}