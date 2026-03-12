package com.example.memoryvault.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memoryvault.utils.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MemoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val timestamp: Long,
    val reminderTime: Long?,
    val isSynced: Boolean?
)