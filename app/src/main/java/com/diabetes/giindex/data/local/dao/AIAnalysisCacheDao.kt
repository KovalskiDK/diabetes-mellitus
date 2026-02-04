package com.diabetes.giindex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diabetes.giindex.data.local.entity.AIAnalysisCache

@Dao
interface AIAnalysisCacheDao {
    
    @Query("SELECT * FROM ai_analysis_cache WHERE productId = :productId LIMIT 1")
    suspend fun getAnalysis(productId: Long): AIAnalysisCache?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: AIAnalysisCache)
    
    @Query("DELETE FROM ai_analysis_cache WHERE timestamp < :expirationTime")
    suspend fun deleteExpiredAnalyses(expirationTime: Long)
    
    @Query("DELETE FROM ai_analysis_cache")
    suspend fun clearAll()
}
