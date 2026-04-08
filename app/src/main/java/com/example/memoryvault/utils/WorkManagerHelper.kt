package com.example.memoryvault.utils

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkManagerHelper {

    fun scheduleSync(context: Context){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        /*val request = PeriodicWorkRequestBuilder<PostSyncMemory>(
            5, TimeUnit.SECONDS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "post_memory_sync",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )*/

        val request = OneTimeWorkRequestBuilder<PostSyncMemory>()
            .setConstraints(constraints).build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "post_memory_sync",
                ExistingWorkPolicy.KEEP,
                request
            )

    }

}