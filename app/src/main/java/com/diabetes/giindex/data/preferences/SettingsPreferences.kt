package com.diabetes.giindex.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class SettingsPreferences(private val context: Context) {
    
    companion object {
        private val PRODUCT_LANGUAGE = stringPreferencesKey("product_language")
    }
    
    val productLanguage: Flow<ProductLanguage> = context.settingsDataStore.data
        .map { preferences ->
            val value = preferences[PRODUCT_LANGUAGE] ?: ProductLanguage.RUSSIAN.name
            try {
                ProductLanguage.valueOf(value)
            } catch (e: IllegalArgumentException) {
                ProductLanguage.RUSSIAN
            }
        }
    
    suspend fun setProductLanguage(language: ProductLanguage) {
        context.settingsDataStore.edit { preferences ->
            preferences[PRODUCT_LANGUAGE] = language.name
        }
    }
}

enum class ProductLanguage {
    RUSSIAN,    // Только русские названия
    ENGLISH,    // Только английские названия
    BOTH        // Оба языка (русский + английский мелким)
}
