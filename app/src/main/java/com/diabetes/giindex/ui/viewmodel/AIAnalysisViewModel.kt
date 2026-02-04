package com.diabetes.giindex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diabetes.giindex.data.ai.GeminiService
import com.diabetes.giindex.data.ai.ProductAnalysis
import com.diabetes.giindex.data.local.entity.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AIAnalysisViewModel : ViewModel() {
    
    private val _analysis = MutableStateFlow<ProductAnalysis?>(null)
    val analysis: StateFlow<ProductAnalysis?> = _analysis.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private var geminiService: GeminiService? = null
    
    fun setApiKey(apiKey: String) {
        geminiService = if (apiKey.isNotBlank()) {
            GeminiService(apiKey)
        } else {
            null
        }
    }
    
    fun analyzeProduct(product: Product) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val service = geminiService
                if (service == null) {
                    _analysis.value = getOfflineAnalysis(product)
                } else {
                    _analysis.value = service.analyzeProduct(product)
                }
            } catch (e: Exception) {
                _error.value = "Ошибка анализа: ${e.message}"
                _analysis.value = getOfflineAnalysis(product)
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private fun getOfflineAnalysis(product: Product): ProductAnalysis {
        val recommendation = when {
            product.gi < 55 -> "✅ МОЖНО употреблять регулярно"
            product.gi < 70 -> "⚠️ ОСТОРОЖНО - контролируйте порции"
            else -> "❌ ИЗБЕГАЙТЕ или употребляйте редко"
        }
        
        val explanation = buildString {
            append("Гликемический индекс: ")
            append(when {
                product.gi < 55 -> "низкий (${product.gi})"
                product.gi < 70 -> "средний (${product.gi})"
                else -> "высокий (${product.gi})"
            })
            append(". ")
            
            product.carbsPer100g?.let { carbs ->
                append("Содержание углеводов: ${String.format("%.1f", carbs)}г на 100г. ")
            }
            
            append(when {
                product.gi < 55 -> "Продукт медленно повышает уровень сахара в крови, что хорошо для контроля диабета."
                product.gi < 70 -> "Продукт умеренно влияет на уровень сахара. Употребляйте с осторожностью."
                else -> "Продукт быстро повышает уровень сахара в крови. Рекомендуется избегать."
            })
        }
        
        val tips = mutableListOf<String>()
        
        when {
            product.gi < 55 -> {
                tips.add("Хороший выбор для ежедневного рациона")
                tips.add("Можно употреблять в умеренных количествах")
                tips.add("Сочетайте с белками для лучшего эффекта")
            }
            product.gi < 70 -> {
                tips.add("Употребляйте небольшими порциями")
                tips.add("Сочетайте с белками и клетчаткой")
                tips.add("Контролируйте уровень сахара после употребления")
                tips.add("Лучше употреблять в первой половине дня")
            }
            else -> {
                tips.add("Ограничьте употребление до минимума")
                tips.add("Употребляйте только в особых случаях")
                tips.add("Обязательно контролируйте уровень глюкозы")
                tips.add("Сочетайте с продуктами с низким ГИ")
            }
        }
        
        product.gl?.let { gl ->
            if (gl > 20) {
                tips.add("Высокая гликемическая нагрузка - уменьшите порцию")
            }
        }
        
        return ProductAnalysis(
            recommendation = recommendation,
            explanation = explanation,
            tips = tips,
            isAiGenerated = false
        )
    }
    
    fun clearAnalysis() {
        _analysis.value = null
        _error.value = null
    }
}
