package com.example.memoryvault.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memoryvault.utils.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MemoryEntity (
    @PrimaryKey()
    val id: String, // instead of room auto generate id we will use UID
    val title: String,
    val content: String,
    val category: String,
    var imageUri: String? = null,
    var type: String,
    val timestamp: Long,
    val reminderTime: Long?,
    val createdAt: Long,
    val updatedAt: Long,
    val isSynced: Boolean = false, // Boolean should not be nullable - null can create unnecessary cases.
    val isDeleted: Boolean = false,
)