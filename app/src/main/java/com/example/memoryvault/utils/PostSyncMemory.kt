package com.example.memoryvault.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class PostSyncMemory(
    context: Context,
    workParam: WorkerParameters
): CoroutineWorker(context, workParam) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("SyncWorker", "Starting sync...")
            Log.d("SyncWorker", "Syncing memory")
            delay(3000)
            Log.d("SyncWorker", "Sync success")
            Result.success()
        }catch (e: Exception){
            Result.retry()
        }
    }

}