package com.example.memoryvault.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.memoryvault.data.local.entity.MemoryEntity
import com.example.memoryvault.utils.Constants.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY timestamp DESC")
    fun getAllMemories(): Flow<List<MemoryEntity>> // Room + Flow = auto UI updates - No manual refresh required.


    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun getMemoryById(id: Long): MemoryEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMemory(memory: MemoryEntity)

    @Delete
    suspend fun deleteMemory(memory: MemoryEntity)

    @Query("SELECT * FROM $TABLE_NAME WHERE isSynced = 0")
    fun getUnsyncedMemories(): Flow<List<MemoryEntity>>

}