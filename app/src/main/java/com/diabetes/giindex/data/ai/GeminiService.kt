package com.diabetes.giindex.data.ai

import com.diabetes.giindex.data.local.entity.Product
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GeminiService(private val apiKey: String) {
    
    companion object {
        private const val TAG = "GeminiService"
    }
    
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d(TAG, message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val api = retrofit.create(GeminiApiService::class.java)
    
    suspend fun analyzeProduct(product: Product): ProductAnalysis {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "=== Starting Gemini API request ===")
                Log.d(TAG, "Product: ${product.nameRu ?: product.nameOriginal}")
                Log.d(TAG, "API Key (first 10 chars): ${apiKey.take(10)}...")
                
                val prompt = buildPrompt(product)
                Log.d(TAG, "Prompt: $prompt")
                
                val request = GeminiRequest(
                    contents = listOf(
                        Content(
                            parts = listOf(Part(text = prompt))
                        )
                    )
                )
                
                Log.d(TAG, "Sending request to Gemini API...")
                val response = api.generateContent(apiKey, request)
                
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d(TAG, "Response body: $body")
                    
                    val text = body?.candidates?.firstOrNull()
                        ?.content?.parts?.firstOrNull()?.text
                        ?: "Не удалось получить анализ"
                    
                    Log.d(TAG, "AI Response text: $text")
                    
                    val result = parseAnalysis(text, product)
                    Log.d(TAG, "=== Gemini API request SUCCESS ===")
                    result
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "API Error: ${response.code()} - ${response.message()}")
                    Log.e(TAG, "Error body: $errorBody")
                    
                    ProductAnalysis(
                        recommendation = getBasicRecommendation(product),
                        explanation = "Ошибка API: ${response.code()} - ${response.message()}\n$errorBody",
                        tips = getBasicTips(product),
                        isAiGenerated = false
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "=== Gemini API request FAILED ===", e)
                Log.e(TAG, "Exception: ${e.javaClass.simpleName}")
                Log.e(TAG, "Message: ${e.message}")
                e.printStackTrace()
                
                ProductAnalysis(
                    recommendation = getBasicRecommendation(product),
                    explanation = "Ошибка: ${e.javaClass.simpleName}\n${e.message}",
                    tips = getBasicTips(product),
                    isAiGenerated = false
                )
            }
        }
    }
    
    private fun buildPrompt(product: Product): String {
        val name = product.nameRu ?: product.nameOriginal
        val gi = product.gi
        val carbs = product.carbsPer100g ?: 0f
        val gl = product.gl ?: 0f
        
        return """
Ты - эксперт по питанию при сахарном диабете. Проанализируй продукт для человека с диабетом.

Продукт: $name
Гликемический индекс (ГИ): $gi
Углеводы на 100г: ${String.format("%.1f", carbs)}г
Гликемическая нагрузка (ГН): ${String.format("%.1f", gl)}

Дай краткий анализ в следующем формате:

РЕКОМЕНДАЦИЯ: (одна строка - можно/осторожно/избегать)
ОБЪЯСНЕНИЕ: (2-3 предложения почему)
СОВЕТЫ: (3-4 конкретных совета как употреблять)

Пиши простым языком, без медицинских терминов. Будь конкретным и практичным.
        """.trimIndent()
    }
    
    private fun parseAnalysis(text: String, product: Product): ProductAnalysis {
        val lines = text.lines().filter { it.isNotBlank() }
        
        var recommendation = ""
        var explanation = ""
        val tips = mutableListOf<String>()
        
        var currentSection = ""
        
        for (line in lines) {
            when {
                line.startsWith("РЕКОМЕНДАЦИЯ:", ignoreCase = true) -> {
                    currentSection = "recommendation"
                    recommendation = line.substringAfter(":").trim()
                }
                line.startsWith("ОБЪЯСНЕНИЕ:", ignoreCase = true) -> {
                    currentSection = "explanation"
                    explanation = line.substringAfter(":").trim()
                }
                line.startsWith("СОВЕТЫ:", ignoreCase = true) -> {
                    currentSection = "tips"
                }
                line.startsWith("-") || line.startsWith("•") || line.matches(Regex("^\\d+\\..*")) -> {
                    if (currentSection == "tips") {
                        tips.add(line.removePrefix("-").removePrefix("•").replaceFirst(Regex("^\\d+\\."), "").trim())
                    }
                }
                else -> {
                    when (currentSection) {
                        "explanation" -> explanation += " " + line.trim()
                        "tips" -> if (line.trim().isNotEmpty()) tips.add(line.trim())
                    }
                }
            }
        }
        
        return ProductAnalysis(
            recommendation = recommendation.ifBlank { getBasicRecommendation(product) },
            explanation = explanation.ifBlank { "Анализ продукта на основе гликемического индекса и содержания углеводов." },
            tips = tips.ifEmpty { getBasicTips(product) },
            isAiGenerated = true
        )
    }
    
    private fun getBasicRecommendation(product: Product): String {
        return when {
            product.gi < 55 -> "✅ МОЖНО употреблять регулярно"
            product.gi < 70 -> "⚠️ ОСТОРОЖНО - контролируйте порции"
            else -> "❌ ИЗБЕГАЙТЕ или употребляйте редко"
        }
    }
    
    private fun getBasicTips(product: Product): List<String> {
        val tips = mutableListOf<String>()
        
        when {
            product.gi < 55 -> {
                tips.add("Хороший выбор для ежедневного рациона")
                tips.add("Можно употреблять в умеренных количествах")
            }
            product.gi < 70 -> {
                tips.add("Употребляйте небольшими порциями")
                tips.add("Сочетайте с белками и клетчаткой")
                tips.add("Контролируйте уровень сахара после употребления")
            }
            else -> {
                tips.add("Ограничьте употребление")
                tips.add("Употребляйте только в особых случаях")
                tips.add("Обязательно контролируйте уровень глюкозы")
            }
        }
        
        return tips
    }
}

data class ProductAnalysis(
    val recommendation: String,
    val explanation: String,
    val tips: List<String>,
    val isAiGenerated: Boolean
)
