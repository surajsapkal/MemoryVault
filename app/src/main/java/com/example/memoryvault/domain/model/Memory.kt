package com.example.memoryvault.domain.model


data class Memory(
    val id: String,
    val title: String,
    val content: String,
    val category: String,
    val imageUri: String? = null,
    val type: String,
    val timestamp: Long,
    val reminderTime: Long?,
    val createdAt: Long,
    val updatedAt: Long,
    val isSynced: Boolean = false,
    val isDeleted: Boolean = false
)
