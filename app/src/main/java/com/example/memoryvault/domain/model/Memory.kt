package com.example.memoryvault.domain.model


data class Memory(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val timestamp: Long,
    val reminderTime: Long?,
    val isSynced: Boolean?
)
