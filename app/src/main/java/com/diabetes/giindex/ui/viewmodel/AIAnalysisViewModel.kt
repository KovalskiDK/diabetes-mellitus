package com.diabetes.giindex.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.diabetes.giindex.data.ai.GeminiConfig
import com.diabetes.giindex.data.ai.GeminiService
import com.diabetes.giindex.data.ai.ProductAnalysis
import com.diabetes.giindex.data.local.GIIndexDatabase
import com.diabetes.giindex.data.local.entity.AIAnalysisCache
import com.diabetes.giindex.data.local.entity.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AIAnalysisViewModel(application: Application) : AndroidViewModel(application) {
    
    private val _analysis = MutableStateFlow<ProductAnalysis?>(null)
    val analysis: StateFlow<ProductAnalysis?> = _analysis.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val database = GIIndexDatabase.getDatabase(application)
    private val cacheDao = database.aiAnalysisCacheDao()
    private val gson = Gson()
    
    // Кэш действителен 7 дней
    private val CACHE_VALIDITY_DAYS = 7L
    private val CACHE_VALIDITY_MS = CACHE_VALIDITY_DAYS * 24 * 60 * 60 * 1000
    
    // Автоматически инициализируем Gemini API с прямыми HTTP запросами
    private var geminiService: GeminiService? = try {
        if (GeminiConfig.API_KEY.isNotBlank() && !GeminiConfig.API_KEY.contains("Demo")) {
            GeminiService(GeminiConfig.API_KEY)
        } else {
            null // Используем офлайн-режим если ключ не настроен
        }
    } catch (e: Exception) {
        null
    }
    
    fun setApiKey(apiKey: String) {
        geminiService = if (apiKey.isNotBlank()) {
            GeminiService(apiKey)
        } else {
            null
        }
    }
    
    fun analyzeProduct(product: Product) {
        currentProduct = product // Сохраняем для повторного анализа
        
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                // Проверяем кэш
                val cached = cacheDao.getAnalysis(product.id)
                val now = System.currentTimeMillis()
                
                if (cached != null && 
                    (now - cached.timestamp) < CACHE_VALIDITY_MS &&
                    cached.productGi == product.gi &&
                    cached.productCarbs == (product.carbsPer100g ?: 0f)) {
                    
                    // Используем кэшированный результат
                    _analysis.value = ProductAnalysis(
                        recommendation = cached.recommendation,
                        explanation = cached.explanation,
                        tips = gson.fromJson(cached.tips, object : TypeToken<List<String>>() {}.type),
                        isAiGenerated = cached.isAiGenerated
                    )
                    _isLoading.value = false
                    return@launch
                }
                
                // Получаем новый анализ
                val service = geminiService
                val result = if (service == null) {
                    getOfflineAnalysis(product)
                } else {
                    service.analyzeProduct(product)
                }
                
                // Сохраняем в кэш
                cacheDao.insertAnalysis(
                    AIAnalysisCache(
                        productId = product.id,
                        recommendation = result.recommendation,
                        explanation = result.explanation,
                        tips = gson.toJson(result.tips),
                        isAiGenerated = result.isAiGenerated,
                        timestamp = now,
                        productGi = product.gi,
                        productCarbs = product.carbsPer100g ?: 0f
                    )
                )
                
                _analysis.value = result
                
                // Очищаем устаревший кэш
                cacheDao.deleteExpiredAnalyses(now - CACHE_VALIDITY_MS)
                
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
    
    private var currentProduct: Product? = null
    
    fun clearAnalysis() {
        viewModelScope.launch {
            _analysis.value = null
            _error.value = null
            
            // Удаляем из кэша, чтобы получить свежий анализ
            currentProduct?.let { product ->
                cacheDao.getAnalysis(product.id)?.let {
                    cacheDao.deleteExpiredAnalyses(System.currentTimeMillis() + 1000) // Удаляем все
                }
                // Сразу запрашиваем новый анализ
                analyzeProduct(product)
            }
        }
    }
}
