package com.example.memoryvault.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoryvault.domain.model.Memory
import com.example.memoryvault.domain.repository.MemoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoryViewModel @Inject constructor(
    private val repository: MemoryRepository
) : ViewModel() {

    private val _selectedMemory = MutableStateFlow<Memory?>(null)
    val selectedMemory: StateFlow<Memory?> = _selectedMemory

    private val _uiEvent = MutableStateFlow<String?>(null)
    val uiEvent: StateFlow<String?> = _uiEvent

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    var memoriesState = repository.getAllMemories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val filteredMemories = _searchText.combine(memoriesState){q,list ->
        if (q.isBlank()){
            list
        }else{
            list.filter {
                it.title.contains(q,ignoreCase = true)
                it.content.contains(q,ignoreCase = true)
                it.category.contains(q,ignoreCase = true)
            }
        }
    }.stateIn(
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
            _uiEvent.value = "Memory Inserted"
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
            _uiEvent.value = "Memory Updated"
        }
    }

    fun deleteMemory(memory: Memory) {
        viewModelScope.launch {
            repository.deleteMemory(memory)
            _uiEvent.value = "Memory Deleted"
        }
    }

    fun clearEvent() {
        _uiEvent.value = null
    }

    fun onSearchQueryChanged(query: String){
        _searchText.value = query
    }
}