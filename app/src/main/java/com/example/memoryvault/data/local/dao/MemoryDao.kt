package com.example.memoryvault.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.memoryvault.data.local.entity.MemoryEntity
import com.example.memoryvault.utils.Constants.TABLE_NAME

@Dao
interface MemoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAllMemories(): List<MemoryEntity>


    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun searchMemories(id: Long): MemoryEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMemory(memory: MemoryEntity)

    @Delete
    suspend fun deleteMemory(memory: MemoryEntity)

    @Query("SELECT * FROM $TABLE_NAME WHERE isSynced = FALSE")
    suspend fun getUnsyncedMemories(): List<MemoryEntity>

}