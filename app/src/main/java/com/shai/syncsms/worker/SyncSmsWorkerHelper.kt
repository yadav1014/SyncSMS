package com.shai.syncsms.worker

import androidx.work.*
import org.koin.core.KoinComponent
import org.koin.core.inject

object SyncSmsWorkerHelper : KoinComponent {
    val workManager: WorkManager by inject()
    fun runPeriodicWorker() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            SyncSmsWorker::class.java,
            SyncSmsWorker.REPEAT_TIME_INTERVAL_IN_MINUTES,
            SyncSmsWorker.REPEAT_TIME_INTERVAL_UNITS
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()
        workManager.enqueueUniquePeriodicWork(
            SyncSmsWorker.UNIQUE_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    fun runSingleWorker() {
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(
            SyncSmsWorker::class.java
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()
        workManager.enqueue(oneTimeWorkRequest)
    }
}