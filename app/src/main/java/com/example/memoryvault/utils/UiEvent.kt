package com.example.memoryvault.utils

sealed class UiEvent {

    data object MemoryInserted: UiEvent()
    data object MemoryUpdated: UiEvent()
    data object MemoryDeleted: UiEvent()
    data class Error(val message: String): UiEvent()

}