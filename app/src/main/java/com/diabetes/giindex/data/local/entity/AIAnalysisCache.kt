package com.diabetes.giindex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_analysis_cache")
data class AIAnalysisCache(
    @PrimaryKey
    val productId: Long,
    val recommendation: String,
    val explanation: String,
    val tips: String, // JSON array of tips
    val isAiGenerated: Boolean,
    val timestamp: Long,
    val productGi: Int, // Для проверки актуальности кэша
    val productCarbs: Float // Для проверки актуальности кэша
)
