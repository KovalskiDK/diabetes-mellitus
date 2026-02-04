package com.diabetes.giindex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.diabetes.giindex.data.preferences.ProductLanguage
import com.diabetes.giindex.data.preferences.SettingsPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsPreferences: SettingsPreferences
) : ViewModel() {
    
    val productLanguage: StateFlow<ProductLanguage> = settingsPreferences.productLanguage
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductLanguage.RUSSIAN
        )
    
    fun setProductLanguage(language: ProductLanguage) {
        viewModelScope.launch {
            settingsPreferences.setProductLanguage(language)
        }
    }
}

class SettingsViewModelFactory(
    private val settingsPreferences: SettingsPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(settingsPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
