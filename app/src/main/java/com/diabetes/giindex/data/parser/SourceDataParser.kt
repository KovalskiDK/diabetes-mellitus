package com.diabetes.giindex.data.parser

import com.diabetes.giindex.data.local.entity.Product
import com.diabetes.giindex.data.local.entity.ProductSourceData
import org.json.JSONArray
import org.json.JSONObject

object SourceDataParser {
    
    fun parseJson(jsonString: String, sourceId: Long): ParseResult {
        return try {
            val jsonArray = JSONArray(jsonString)
            val products = mutableListOf<Product>()
            val sourceData = mutableListOf<ProductSourceData>()
            
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                
                val nameEn = item.optString("name", item.optString("nameEn", ""))
                val nameRu = item.optString("nameRu", nameEn)
                val category = item.optString("category", "Прочее")
                val gi = item.optInt("gi", 0)
                val gl = item.optDouble("gl", -1.0).let { if (it >= 0) it.toFloat() else null }
                val carbs = item.optDouble("carbs", -1.0).let { if (it >= 0) it.toFloat() else null }
                
                if (nameEn.isNotBlank() && gi > 0) {
                    val productId = nameEn.hashCode().toLong()
                    
                    products.add(
                        Product(
                            id = productId,
                            nameOriginal = nameEn,
                            nameRu = nameRu.takeIf { it.isNotBlank() },
                            category = category,
                            gi = gi,
                            gl = gl,
                            carbsPer100g = carbs
                        )
                    )
                    
                    sourceData.add(
                        ProductSourceData(
                            productId = productId,
                            sourceId = sourceId,
                            gi = gi,
                            gl = gl,
                            carbsPer100g = carbs
                        )
                    )
                }
            }
            
            ParseResult.Success(products, sourceData)
        } catch (e: Exception) {
            ParseResult.Error(e.message ?: "Ошибка парсинга JSON")
        }
    }
    
    fun parseCsv(csvString: String, sourceId: Long): ParseResult {
        return try {
            val lines = csvString.lines().filter { it.isNotBlank() }
            if (lines.isEmpty()) {
                return ParseResult.Error("CSV файл пустой")
            }
            
            val products = mutableListOf<Product>()
            val sourceData = mutableListOf<ProductSourceData>()
            
            // Пропускаем заголовок
            for (i in 1 until lines.size) {
                val parts = lines[i].split(",").map { it.trim() }
                if (parts.size < 3) continue
                
                val nameEn = parts.getOrNull(0) ?: ""
                val nameRu = parts.getOrNull(1) ?: nameEn
                val category = parts.getOrNull(2) ?: "Прочее"
                val gi = parts.getOrNull(3)?.toIntOrNull() ?: 0
                val gl = parts.getOrNull(4)?.toFloatOrNull()
                val carbs = parts.getOrNull(5)?.toFloatOrNull()
                
                if (nameEn.isNotBlank() && gi > 0) {
                    val productId = nameEn.hashCode().toLong()
                    
                    products.add(
                        Product(
                            id = productId,
                            nameOriginal = nameEn,
                            nameRu = nameRu.takeIf { it.isNotBlank() },
                            category = category,
                            gi = gi,
                            gl = gl,
                            carbsPer100g = carbs
                        )
                    )
                    
                    sourceData.add(
                        ProductSourceData(
                            productId = productId,
                            sourceId = sourceId,
                            gi = gi,
                            gl = gl,
                            carbsPer100g = carbs
                        )
                    )
                }
            }
            
            ParseResult.Success(products, sourceData)
        } catch (e: Exception) {
            ParseResult.Error(e.message ?: "Ошибка парсинга CSV")
        }
    }
}

sealed class ParseResult {
    data class Success(
        val products: List<Product>,
        val sourceData: List<ProductSourceData>
    ) : ParseResult()
    
    data class Error(val message: String) : ParseResult()
}
