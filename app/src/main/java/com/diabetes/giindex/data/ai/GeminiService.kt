package com.diabetes.giindex.data.ai

import com.diabetes.giindex.data.local.entity.Product
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiService(private val apiKey: String) {
    
    private val model = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = apiKey,
        generationConfig = generationConfig {
            temperature = 0.7f
            topK = 40
            topP = 0.95f
            maxOutputTokens = 1024
        }
    )
    
    suspend fun analyzeProduct(product: Product): ProductAnalysis {
        return withContext(Dispatchers.IO) {
            try {
                val prompt = buildPrompt(product)
                val response = model.generateContent(prompt)
                val text = response.text ?: "Не удалось получить анализ"
                
                parseAnalysis(text, product)
            } catch (e: Exception) {
                ProductAnalysis(
                    recommendation = getBasicRecommendation(product),
                    explanation = "Ошибка при получении AI-анализа: ${e.message}",
                    tips = emptyList(),
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
