package com.example.memoryvault.data.repository

import com.example.memoryvault.data.local.dao.MemoryDao
import com.example.memoryvault.data.mapper.MemoryMapper
import com.example.memoryvault.data.remote.FirestoreDataSource
import com.example.memoryvault.domain.model.Memory
import com.example.memoryvault.data.remote.model.FirestoreMemory
import com.example.memoryvault.domain.repository.MemoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class MemoryRepositoryImpl @Inject constructor(
    private val memoryDao: MemoryDao,
    private val firestoreDataSource: FirestoreDataSource
) : MemoryRepository {
    override fun getAllMemories(): Flow<List<Memory>> {
        return memoryDao.getAllMemories()
            .map { entities ->
                entities.map(MemoryMapper::entityToDomain)
            }
    }

    override suspend fun getMemoryById(id: String): Memory? {
        return memoryDao.getMemoryById(id)
            ?.let ( MemoryMapper::entityToDomain )
    }

    override suspend fun insertMemory(memory: Memory) {
        val localMemory = memory.copy(
            isSynced = false,
            isDeleted = false
        )

        memoryDao.insertMemory(
            MemoryMapper.domainToEntity(localMemory)
        )
    }

    override suspend fun updateMemory(memory: Memory) {
        val now = System.currentTimeMillis()

        val updatedMemory = memory.copy(
            updatedAt = now,
            isSynced = false,
            isDeleted = false
        )

        memoryDao.updateMemory(
            MemoryMapper.domainToEntity(updatedMemory)
        )
    }

    override suspend fun deleteMemory(memory: Memory) {
        return memoryDao.markDeleted(
            id = memory.id,
            updatedAt = System.currentTimeMillis()
        )
    }

    override suspend fun syncMemories() {
        uploadPendingChanges()
        mergeRemoteChanges()
    }

    private suspend fun uploadPendingChanges() {

        val pending = memoryDao.getPendingMemories()

        if (pending.isEmpty()) {
            return
        }

        val pendingDomain = pending.map(
            MemoryMapper::entityToDomain
        )

        val upserts = pendingDomain
            .filter { !it.isDeleted }

        val deletes = pendingDomain
            .filter { it.isDeleted }

        firestoreDataSource.upsertMemories(
            upserts.map(MemoryMapper::domainToFirestore)
        )

        firestoreDataSource.deleteMemories(
            deletes.map { it.id }
        )

        val uploadedIds = upserts.map { it.id }

        if (uploadedIds.isNotEmpty()) {
            memoryDao.markSynced(uploadedIds)
        }

        val deletedIds = deletes.map { it.id }

        if (deletedIds.isNotEmpty()) {
            memoryDao.permanentlyDeleteTombstones(deletedIds)
        }
    }

    private suspend fun mergeRemoteChanges() {

        val remoteMemories =
            firestoreDataSource.getAllMemories()

        val remoteById =
            remoteMemories.associateBy { it.id }

        val localMemories =
            memoryDao.getAllMemoriesForSync()

        // First merge remote documents.
        for (remote in remoteMemories) {

            val local = localMemories
                .firstOrNull { it.id == remote.id }

            if (local == null) {

                memoryDao.insertMemory(
                    MemoryMapper.domainToEntity(
                        MemoryMapper.firestoreToDomain(remote)
                    )
                )

                continue
            }

            // Never overwrite a local pending change.
            if (!local.isSynced) {
                continue
            }

            // Latest updatedAt
            if (remote.updatedAt >= local.updatedAt) {

                memoryDao.insertMemory(
                    MemoryMapper.domainToEntity(
                        MemoryMapper.firestoreToDomain(remote)
                    )
                )
            }
        }

        // Detect remote deletions.
        // If a local record was synced but is no longer present remotely,
        // remove it locally.
        for (local in localMemories) {

            if (!local.isSynced) {
                continue
            }

            if (local.id !in remoteById) {
                memoryDao.permanentlyDelete(local.id)
            }
        }
    }

}