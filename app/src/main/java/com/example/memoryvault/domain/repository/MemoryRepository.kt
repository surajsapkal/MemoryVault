package com.example.memoryvault.domain.repository

import com.example.memoryvault.domain.model.Memory
import kotlinx.coroutines.flow.Flow

interface MemoryRepository {

    fun getAllMemories(): Flow<List<Memory>>

    suspend fun getMemoryById(id: String): Memory?

    suspend fun insertMemory(memory: Memory)

    suspend fun updateMemory(memory: Memory)

    suspend fun deleteMemory(memory: Memory)

    suspend fun syncMemories()

}