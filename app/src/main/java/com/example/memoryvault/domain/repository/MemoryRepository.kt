package com.example.memoryvault.domain.repository

import com.example.memoryvault.domain.model.Memory
import kotlinx.coroutines.flow.Flow

interface MemoryRepository {

    suspend fun insertMemory(memory: Memory)

    fun getAllMemories(): Flow<List<Memory>>

    suspend fun getMemoryById(id: Long): Memory?

    suspend fun updateMemory(memory: Memory)

    suspend fun deleteMemory(memory: Memory)

    fun getUnsyncedMemories(): Flow<List<Memory>>

}