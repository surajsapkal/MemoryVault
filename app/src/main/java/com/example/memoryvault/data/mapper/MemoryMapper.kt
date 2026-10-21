package com.example.memoryvault.data.mapper

import com.example.memoryvault.data.local.entity.MemoryEntity
import com.example.memoryvault.domain.model.Memory
import com.example.memoryvault.data.remote.model.FirestoreMemory

object MemoryMapper {

    fun entityToDomain(entity: MemoryEntity): Memory{
        return Memory(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            category = entity.category,
            imageUri = entity.imageUri,
            type = entity.type,
            timestamp = entity.timestamp,
            reminderTime = entity.reminderTime,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            isSynced = entity.isSynced,
            isDeleted = entity.isDeleted
        )
    }

    fun domainToEntity(memory: Memory): MemoryEntity{
        return MemoryEntity(
            id = memory.id,
            title = memory.title,
            content = memory.content,
            category = memory.category,
            imageUri = memory.imageUri,
            type = memory.type,
            timestamp = memory.timestamp,
            reminderTime = memory.reminderTime,
            createdAt = memory.createdAt,
            updatedAt = memory.updatedAt,
            isSynced = memory.isSynced,
            isDeleted = memory.isDeleted
        )
    }

    fun firestoreToDomain(firestoreMemory: FirestoreMemory): Memory{
        return Memory(
            id = firestoreMemory.id,
            title = firestoreMemory.title,
            content = firestoreMemory.content,
            category = firestoreMemory.category,
            imageUri = firestoreMemory.imageUri,
            type = firestoreMemory.type,
            timestamp = firestoreMemory.timestamp,
            reminderTime = firestoreMemory.reminderTime,
            createdAt = firestoreMemory.createdAt,
            updatedAt = firestoreMemory.updatedAt,
            isSynced = true,
            isDeleted = false
        )
    }

    fun domainToFirestore(memory: Memory): FirestoreMemory{
        return FirestoreMemory(
            id = memory.id,
            title = memory.title,
            content = memory.content,
            category = memory.category,
            imageUri = memory.imageUri,
            type = memory.type,
            timestamp = memory.timestamp,
            reminderTime = memory.reminderTime,
            createdAt = memory.createdAt,
            updatedAt = memory.updatedAt
        )
    }

}