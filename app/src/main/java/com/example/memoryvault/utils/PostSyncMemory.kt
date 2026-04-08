package com.example.memoryvault.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.memoryvault.data.local.dao.MemoryDao
import com.example.memoryvault.domain.model.Memory
import com.example.memoryvault.domain.repository.MemoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

@HiltWorker
class PostSyncMemory @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workParam: WorkerParameters,
    private val repository: MemoryRepository
): CoroutineWorker(context, workParam) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("SyncWorker", "Starting sync...")
            Log.d("SyncWorker", "Syncing memory")

            val unsyncedList = repository.getUnsyncedMemories().first() // get current list

            for (memory in unsyncedList){
                // pretend API call latency
                delay(5000)

                // pretend API call success
                Log.d("SyncWorker", "Syncing: ${memory.title}")

                // mark as synced
                repository.updateMemory(memory.copy(isSynced = true))
            }

            Log.d("SyncWorker", "Sync success")
            Result.success()
        }catch (e: Exception){
            Result.retry()
        }
    }

}