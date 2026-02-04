package com.diabetes.giindex.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.diabetes.giindex.data.local.GIIndexDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

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
    
    private suspend fun initializeDatabase(database: GIIndexDatabase) {
        val productDao = database.productDao()
        
        SampleData.sampleProducts.forEach { product ->
            productDao.insertProduct(product)
        }
    }
}
