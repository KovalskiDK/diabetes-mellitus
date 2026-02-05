package com.diabetes.giindex.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.diabetes.giindex.data.local.GIIndexDatabase
import com.diabetes.giindex.data.local.dao.DataSourceDao
import com.diabetes.giindex.data.local.entity.DataSource
import com.diabetes.giindex.data.local.entity.SourceType
import com.diabetes.giindex.data.local.entity.SyncLog
import com.diabetes.giindex.data.local.entity.SyncStatus
import com.diabetes.giindex.data.network.LoadResult
import com.diabetes.giindex.data.network.SourceDataLoader
import com.diabetes.giindex.data.parser.ParseResult
import com.diabetes.giindex.data.parser.SourceDataParser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import android.util.Log

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object DatabaseInitializer {
    
    private val IS_INITIALIZED_KEY = booleanPreferencesKey("is_database_initialized")
    
    suspend fun initializeIfNeeded(context: Context, database: GIIndexDatabase) {
        val isInitialized = context.dataStore.data
            .map { preferences ->
                preferences[IS_INITIALIZED_KEY] ?: false
            }
            .first()
        
        if (!isInitialized) {
            initializeDatabase(database)
            context.dataStore.edit { preferences ->
                preferences[IS_INITIALIZED_KEY] = true
            }
        }
    }
    
    suspend fun initializeDatabase(database: GIIndexDatabase) {
        try {
            val productDao = database.productDao()
            val dataSourceDao = database.dataSourceDao()
            
            Log.d("DatabaseInitializer", "Starting database initialization")
            
            // Инициализируем источники данных
            Log.d("DatabaseInitializer", "Initializing data sources")
            initializeDataSources(database, dataSourceDao)
            Log.d("DatabaseInitializer", "Database initialization completed")
        } catch (e: Exception) {
            Log.e("DatabaseInitializer", "Error during database initialization", e)
            throw e
        }
    }
    
    private suspend fun initializeDataSources(database: GIIndexDatabase, dataSourceDao: DataSourceDao) {
        try {
            // Проверяем, есть ли уже источники
            val existingSources = dataSourceDao.getAllSources().first()
            Log.d("DatabaseInitializer", "Existing sources count: ${existingSources.size}")
            
            if (existingSources.isEmpty()) {
                Log.d("DatabaseInitializer", "Creating default data sources")
            val defaultSources = listOf(
                DataSource(
                    name = "Популярные продукты",
                    url = "https://raw.githubusercontent.com/KovalskiDK/diabetes-mellitus/main/gi-popular-products.json",
                    type = SourceType.JSON,
                    version = "1.0",
                    description = "База популярных продуктов с гликемическим индексом",
                    isActive = true,
                    priority = 100
                )
            )
            
            Log.d("DatabaseInitializer", "Inserting ${defaultSources.size} default sources")
            defaultSources.forEach { source ->
                val id = dataSourceDao.insertSource(source)
                Log.d("DatabaseInitializer", "Inserted source: ${source.name} with id: $id")
                
                // Автоматически загружаем данные для активного источника
                if (source.isActive) {
                    Log.d("DatabaseInitializer", "Auto-loading data for source: ${source.name}")
                    loadSourceData(database, id, source)
                }
            }
            Log.d("DatabaseInitializer", "All default sources inserted successfully")
        } else {
            Log.d("DatabaseInitializer", "Data sources already exist, skipping initialization")
        }
        } catch (e: Exception) {
            Log.e("DatabaseInitializer", "Error initializing data sources", e)
            throw e
        }
    }
    
    private suspend fun loadSourceData(database: GIIndexDatabase, sourceId: Long, source: DataSource) {
        try {
            Log.d("DatabaseInitializer", "Loading data from: ${source.url}")
            
            // Получаем DAO
            val syncLogDao = database.syncLogDao()
            val productDao = database.productDao()
            val dataSourceDao = database.dataSourceDao()
            
            val logId = syncLogDao.insertLog(
                SyncLog(
                    sourceId = sourceId,
                    sourceName = source.name,
                    sourceVersion = source.version,
                    status = SyncStatus.IN_PROGRESS,
                    startedAt = System.currentTimeMillis()
                )
            )
            
            // Загружаем данные из URL
            when (val loadResult = SourceDataLoader.loadFromUrl(source.url)) {
                is LoadResult.Success -> {
                    Log.d("DatabaseInitializer", "Data loaded successfully, parsing...")
                    
                    // Парсим данные
                    val parseResult = when (source.type) {
                        SourceType.JSON -> SourceDataParser.parseJson(loadResult.content, sourceId)
                        SourceType.CSV -> SourceDataParser.parseCsv(loadResult.content, sourceId)
                        else -> ParseResult.Error("Неподдерживаемый тип источника")
                    }
                    
                    when (parseResult) {
                        is ParseResult.Success -> {
                            Log.d("DatabaseInitializer", "Parsed ${parseResult.products.size} products, saving...")
                            
                            // Сохраняем продукты
                            productDao.insertProducts(parseResult.products)
                            
                            // Обновляем версию источника
                            dataSourceDao.updateSource(
                                source.copy(
                                    lastUpdated = System.currentTimeMillis(),
                                    recordsCount = parseResult.products.size
                                )
                            )
                            
                            // Обновляем лог
                            syncLogDao.updateLog(
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
                            
                            Log.d("DatabaseInitializer", "Successfully loaded ${parseResult.products.size} products from ${source.name}")
                        }
                        is ParseResult.Error -> {
                            Log.e("DatabaseInitializer", "Parse error: ${parseResult.message}")
                            syncLogDao.updateLog(
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
                    Log.e("DatabaseInitializer", "Load error: ${loadResult.message}")
                    syncLogDao.updateLog(
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
            Log.e("DatabaseInitializer", "Error loading source data: ${e.message}", e)
        }
    }
}
