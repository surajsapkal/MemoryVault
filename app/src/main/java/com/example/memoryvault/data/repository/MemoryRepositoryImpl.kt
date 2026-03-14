package com.example.memoryvault.data.repository

import com.example.memoryvault.data.local.dao.MemoryDao
import com.example.memoryvault.data.local.entity.MemoryEntity
import com.example.memoryvault.data.mapper.MemoryMapper
import com.example.memoryvault.domain.model.Memory
import com.example.memoryvault.domain.repository.MemoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoryRepositoryImpl @Inject constructor(
    private val memoryDao: MemoryDao
) : MemoryRepository {

    override suspend fun insertMemory(memory: Memory) {
        memoryDao.insertMemory(
            MemoryMapper.domainToEntity(memory)
        )
    }

    override fun getAllMemories(): Flow<List<Memory>> {
        return memoryDao.getAllMemories().map { entityList ->
            entityList.map { MemoryMapper.entityToDomain(it) }
        }
    }

    override suspend fun getMemoryById(id: Long): Memory? {
        return memoryDao.getMemoryById(id)?.let {
            MemoryMapper.entityToDomain(it)
        }
    }

    override suspend fun updateMemory(memory: Memory) {
        memoryDao.updateMemory(
            MemoryMapper.domainToEntity(memory)
        )
    }

    override suspend fun deleteMemory(memory: Memory) {
        memoryDao.deleteMemory(
            MemoryMapper.domainToEntity(memory)
        )
    }

    override suspend fun getUnsyncedMemories(): List<Memory> {
        return memoryDao.getUnsyncedMemories().map {
            MemoryMapper.entityToDomain(it)
        }
    }
}