package com.example.memoryvault.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.memoryvault.domain.repository.MemoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MemorySyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: MemoryRepository
): CoroutineWorker(
    context, workerParameters
) {
    override suspend fun doWork(): Result {
        return try {
            repository.syncMemories()
            Result.success()
        } catch (e: Exception){
            Result.retry()
        }
    }
}