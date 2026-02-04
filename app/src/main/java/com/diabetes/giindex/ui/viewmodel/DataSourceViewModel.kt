package com.diabetes.giindex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.diabetes.giindex.data.local.entity.DataSource
import com.diabetes.giindex.data.local.entity.SyncLog
import com.diabetes.giindex.data.repository.DataSourceRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DataSourceViewModel(
    private val repository: DataSourceRepository
) : ViewModel() {
    
    val sources: StateFlow<List<DataSource>> = repository.getAllSources()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val recentLogs: StateFlow<List<SyncLog>> = repository.getRecentSyncLogs()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
    
    fun toggleSourceActive(sourceId: Long, isActive: Boolean) {
        viewModelScope.launch {
            repository.toggleSourceActive(sourceId, !isActive)
        }
    }
    
    fun addSource(source: DataSource) {
        viewModelScope.launch {
            repository.insertSource(source)
        }
    }
    
    fun deleteSource(source: DataSource) {
        viewModelScope.launch {
            repository.deleteSource(source)
        }
    }
    
    fun refreshSource(sourceId: Long) {
        viewModelScope.launch {
            _isRefreshing.value = true
            _isRefreshing.value = false
        }
    }
}

class DataSourceViewModelFactory(
    private val repository: DataSourceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataSourceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataSourceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
