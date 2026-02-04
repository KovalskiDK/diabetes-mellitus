package com.diabetes.giindex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.diabetes.giindex.data.local.entity.DataSource
import com.diabetes.giindex.data.local.entity.SourceType
import com.diabetes.giindex.data.local.entity.SyncLog
import com.diabetes.giindex.data.local.entity.SyncStatus
import com.diabetes.giindex.data.network.LoadResult
import com.diabetes.giindex.data.network.SourceDataLoader
import com.diabetes.giindex.data.parser.ParseResult
import com.diabetes.giindex.data.parser.SourceDataParser
import com.diabetes.giindex.data.remote.GitHubSourcesApi
import com.diabetes.giindex.data.repository.DataSourceRepository
import com.diabetes.giindex.data.repository.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DataSourceViewModel(
    private val repository: DataSourceRepository,
    private val productRepository: ProductRepository
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
    
    fun toggleSourceActive(sourceId: Long, currentStatus: Boolean) {
        viewModelScope.launch {
            repository.toggleSourceActive(sourceId, !currentStatus)
        }
    }
    
    fun updateSourceUrlAndType(sourceId: Long, url: String, type: com.diabetes.giindex.data.local.entity.SourceType) {
        viewModelScope.launch {
            repository.updateSourceUrlAndType(sourceId, url, type)
        }
    }
    
    fun addSource(source: DataSource) {
        viewModelScope.launch {
            repository.insertSource(source)
        }
    }
    
    fun deleteSource(sourceId: Long) {
        viewModelScope.launch {
            repository.deleteSource(sourceId)
        }
    }
    
    fun clearSourceData(sourceId: Long) {
        viewModelScope.launch {
            val deletedCount = productRepository.deleteProductsBySource(sourceId)
            
            // Обновляем счетчик записей источника
            repository.updateSourceVersion(
                sourceId = sourceId,
                version = "",
                timestamp = System.currentTimeMillis(),
                count = 0
            )
            
            // Создаем лог об удалении
            repository.insertSyncLog(
                SyncLog(
                    sourceId = sourceId,
                    sourceName = repository.getSourceById(sourceId)?.name ?: "",
                    sourceVersion = "",
                    status = SyncStatus.SUCCESS,
                    recordsAdded = -deletedCount,
                    recordsTotal = 0,
                    startedAt = System.currentTimeMillis(),
                    completedAt = System.currentTimeMillis()
                )
            )
        }
    }
    
    fun refreshSource(sourceId: Long) {
        viewModelScope.launch {
            _isRefreshing.value = true
            
            val source = repository.getSourceById(sourceId)
            if (source == null) {
                _isRefreshing.value = false
                return@launch
            }
            
            // Создаем лог синхронизации
            val logId = repository.insertSyncLog(
                SyncLog(
                    sourceId = sourceId,
                    sourceName = source.name,
                    sourceVersion = source.version,
                    status = SyncStatus.IN_PROGRESS,
                    startedAt = System.currentTimeMillis()
                )
            )
            
            try {
                // Загружаем данные из URL
                when (val loadResult = SourceDataLoader.loadFromUrl(source.url)) {
                    is LoadResult.Success -> {
                        // Парсим данные
                        val parseResult = when (source.type) {
                            SourceType.JSON -> SourceDataParser.parseJson(loadResult.content, sourceId)
                            SourceType.CSV -> SourceDataParser.parseCsv(loadResult.content, sourceId)
                            else -> ParseResult.Error("Неподдерживаемый тип источника")
                        }
                        
                        when (parseResult) {
                            is ParseResult.Success -> {
                                // Сохраняем продукты и данные источников
                                productRepository.insertProducts(parseResult.products)
                                
                                // Обновляем версию источника
                                repository.updateSourceVersion(
                                    sourceId = sourceId,
                                    version = source.version,
                                    timestamp = System.currentTimeMillis(),
                                    count = parseResult.products.size
                                )
                                
                                // Обновляем лог
                                repository.updateSyncLog(
                                    SyncLog(
                                        id = logId,
                                        sourceId = sourceId,
                                        sourceName = source.name,
                                        sourceVersion = source.version,
                                        status = SyncStatus.SUCCESS,
                                        recordsAdded = parseResult.products.size,
                                        recordsTotal = parseResult.products.size,
                                        startedAt = System.currentTimeMillis(),
                                        completedAt = System.currentTimeMillis()
                                    )
                                )
                            }
                            is ParseResult.Error -> {
                                repository.updateSyncLog(
                                    SyncLog(
                                        id = logId,
                                        sourceId = sourceId,
                                        sourceName = source.name,
                                        sourceVersion = source.version,
                                        status = SyncStatus.FAILED,
                                        errorMessage = parseResult.message,
                                        startedAt = System.currentTimeMillis(),
                                        completedAt = System.currentTimeMillis()
                                    )
                                )
                            }
                        }
                    }
                    is LoadResult.Error -> {
                        repository.updateSyncLog(
                            SyncLog(
                                id = logId,
                                sourceId = sourceId,
                                sourceName = source.name,
                                sourceVersion = source.version,
                                status = SyncStatus.FAILED,
                                errorMessage = loadResult.message,
                                startedAt = System.currentTimeMillis(),
                                completedAt = System.currentTimeMillis()
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                repository.updateSyncLog(
                    SyncLog(
                        id = logId,
                        sourceId = sourceId,
                        sourceName = source.name,
                        sourceVersion = source.version,
                        status = SyncStatus.FAILED,
                        errorMessage = e.message ?: "Неизвестная ошибка",
                        startedAt = System.currentTimeMillis(),
                        completedAt = System.currentTimeMillis()
                    )
                )
            } finally {
                _isRefreshing.value = false
            }
        }
    }
    
    fun deleteSourceCompletely(sourceId: Long) {
        viewModelScope.launch {
            try {
                // Удаляем все продукты источника
                productRepository.deleteProductsBySource(sourceId)
                // Удаляем сам источник
                val source = repository.getSourceById(sourceId)
                if (source != null) {
                    repository.deleteSource(source)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun discoverGitHubSources() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val githubFiles = GitHubSourcesApi.getAvailableSources()
                val existingSources = sources.value
                
                // Добавляем только новые источники
                githubFiles.forEach { file ->
                    val exists = existingSources.any { it.url == file.downloadUrl }
                    if (!exists) {
                        val newSource = DataSource(
                            name = file.name,
                            description = "",
                            url = file.downloadUrl,
                            type = SourceType.JSON,
                            isActive = false,
                            version = "",
                            lastUpdated = 0,
                            recordsCount = 0
                        )
                        repository.insertSource(newSource)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}

class DataSourceViewModelFactory(
    private val repository: DataSourceRepository,
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataSourceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataSourceViewModel(repository, productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
