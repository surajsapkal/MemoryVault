package com.example.memoryvault.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoryvault.domain.model.Memory
import com.example.memoryvault.domain.repository.MemoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoryViewModel @Inject constructor(
    private val repository: MemoryRepository
) : ViewModel() {

    private val _selectedMemory = MutableStateFlow<Memory?>(null)
    val selectedMemory: StateFlow<Memory?> = _selectedMemory


    val memoriesState = repository.getAllMemories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val unsyncedMemories = repository.getUnsyncedMemories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertMemory(memory: Memory) {
        viewModelScope.launch {
            repository.insertMemory(memory)
        }
    }

    fun getMemoryById(id: Long) {
        viewModelScope.launch {
            _selectedMemory.value = repository.getMemoryById(id)
        }
    }

    fun updateMemory(memory: Memory) {
        viewModelScope.launch {
            repository.updateMemory(memory)
        }
    }

    fun deleteMemory(memory: Memory) {
        viewModelScope.launch {
            repository.deleteMemory(memory)
        }
    }
}