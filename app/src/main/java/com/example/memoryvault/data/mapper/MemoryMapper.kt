package com.example.memoryvault.data.mapper

import com.example.memoryvault.data.local.entity.MemoryEntity
import com.example.memoryvault.domain.model.Memory

object MemoryMapper {

    fun entityToDomain(entity: MemoryEntity): Memory{
        return Memory(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            category = entity.category,
            timestamp = entity.timestamp,
            reminderTime = entity.reminderTime,
            isSynced = entity.isSynced
        )
    }

    fun domainToEntity(memory: Memory): MemoryEntity{
        return MemoryEntity(
            id = memory.id,
            title = memory.title,
            content = memory.content,
            category = memory.category,
            timestamp = memory.timestamp,
            reminderTime = memory.reminderTime,
            isSynced = memory.isSynced
        )
    }

}