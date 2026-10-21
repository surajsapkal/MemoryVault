package com.example.memoryvault.utils

import android.content.Context
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.memoryvault.data.sync.MemorySyncWorker
import com.example.memoryvault.ui.viewmodels.MemoryViewModel
import java.util.concurrent.TimeUnit

object WorkManagerHelper {

    private const val SYNC_WORK_NAME = "memory_sync"

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

        val request = OneTimeWorkRequestBuilder<MemorySyncWorker>()
            .setConstraints(constraints).build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                request
            )

    }

}