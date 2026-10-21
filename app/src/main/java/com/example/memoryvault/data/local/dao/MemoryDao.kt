package com.example.memoryvault.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.memoryvault.data.local.entity.MemoryEntity
import com.example.memoryvault.domain.model.Memory
import com.example.memoryvault.utils.Constants.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity)


    @Update
    suspend fun updateMemory(memory: MemoryEntity)


    @Query("""
        SELECT *
        FROM $TABLE_NAME
        WHERE isDeleted = 0
        ORDER BY timestamp DESC
    """)
    fun getAllMemories(): Flow<List<MemoryEntity>>


    @Query("""
        SELECT *
        FROM $TABLE_NAME
        WHERE id = :id
        LIMIT 1
    """)
    suspend fun getMemoryById(id: String): MemoryEntity?


    @Query("""
        SELECT *
        FROM $TABLE_NAME
        WHERE isSynced = 0
        ORDER BY updatedAt ASC
    """)
    suspend fun getPendingMemories(): List<MemoryEntity>


    @Query("""
        SELECT *
        FROM $TABLE_NAME
    """)
    suspend fun getAllMemoriesForSync(): List<MemoryEntity>


    @Query("""
        UPDATE $TABLE_NAME
        SET isSynced = 1
        WHERE id IN (:ids)
    """)
    suspend fun markSynced(ids: List<String>)


    @Query("""
        DELETE FROM $TABLE_NAME
        WHERE id IN (:ids)
        AND isDeleted = 1
    """)
    suspend fun permanentlyDeleteTombstones(ids: List<String>)


    @Query("""
        DELETE FROM $TABLE_NAME
        WHERE id = :id
    """)
    suspend fun permanentlyDelete(id: String)


    @Query("""
        UPDATE $TABLE_NAME
        SET title = :title,
            content = :content,
            category = :category,
            imageUri = :imageUri,
            type = :type,
            timestamp = :timestamp,
            reminderTime = :reminderTime,
            updatedAt = :updatedAt,
            isSynced = 0
        WHERE id = :id
    """)
    suspend fun updateMemoryLocally(
        id: String,
        title: String,
        content: String,
        category: String,
        imageUri: String?,
        type: String,
        timestamp: Long,
        reminderTime: Long?,
        updatedAt: Long
    )


    @Query("""
        UPDATE $TABLE_NAME
        SET isDeleted = 1,
            isSynced = 0,
            updatedAt = :updatedAt
        WHERE id = :id
    """)
    suspend fun markDeleted(
        id: String,
        updatedAt: Long
    )

}