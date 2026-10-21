package com.example.memoryvault.data.remote.model

data class FirestoreMemory(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val category: String = "",
    val imageUri: String? = null,
    val type: String = "",
    val timestamp: Long = 0L,
    val reminderTime: Long? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)