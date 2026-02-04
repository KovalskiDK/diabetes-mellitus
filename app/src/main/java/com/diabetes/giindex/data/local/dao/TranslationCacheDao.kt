package com.diabetes.giindex.data.local.dao

import androidx.room.*
import com.diabetes.giindex.data.local.entity.TranslationCache

@Dao
interface TranslationCacheDao {
    
    @Query("SELECT * FROM translation_cache WHERE original = :original AND targetLanguage = :targetLanguage")
    suspend fun getTranslation(original: String, targetLanguage: String = "ru"): TranslationCache?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translation: TranslationCache)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslations(translations: List<TranslationCache>)
    
    @Query("DELETE FROM translation_cache")
    suspend fun clearCache()
    
    @Query("DELETE FROM translation_cache WHERE createdAt < :timestamp")
    suspend fun deleteOldTranslations(timestamp: Long)
    
    @Query("SELECT COUNT(*) FROM translation_cache")
    suspend fun getCacheSize(): Int
}
