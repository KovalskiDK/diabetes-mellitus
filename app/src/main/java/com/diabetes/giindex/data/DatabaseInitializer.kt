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
            initializeDataSources(dataSourceDao)
            Log.d("DatabaseInitializer", "Database initialization completed")
        } catch (e: Exception) {
            Log.e("DatabaseInitializer", "Error during database initialization", e)
            throw e
        }
    }
    
    private suspend fun initializeDataSources(dataSourceDao: DataSourceDao) {
        try {
            // Проверяем, есть ли уже источники
            val existingSources = dataSourceDao.getAllSources().first()
            Log.d("DatabaseInitializer", "Existing sources count: ${existingSources.size}")
            
            if (existingSources.isEmpty()) {
                Log.d("DatabaseInitializer", "Creating default data sources")
            val defaultSources = listOf(
                DataSource(
                    name = "GI Foundation (Sydney)",
                    url = "https://raw.githubusercontent.com/KovalskiDK/diabetes-mellitus/main/gi-database.json",
                    type = SourceType.JSON,
                    version = "1.0",
                    description = "Официальная база данных гликемических индексов Сиднейского университета",
                    isActive = true,
                    priority = 100
                )
            )
            
            Log.d("DatabaseInitializer", "Inserting ${defaultSources.size} default sources")
            defaultSources.forEach { source ->
                val id = dataSourceDao.insertSource(source)
                Log.d("DatabaseInitializer", "Inserted source: ${source.name} with id: $id")
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
}
