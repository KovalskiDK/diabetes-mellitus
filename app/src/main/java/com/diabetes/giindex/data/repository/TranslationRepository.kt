package com.diabetes.giindex.data.repository

import com.diabetes.giindex.data.local.dao.TranslationCacheDao
import com.diabetes.giindex.data.local.entity.TranslationCache
import com.diabetes.giindex.data.local.entity.TranslationSource

class TranslationRepository(
    private val translationCacheDao: TranslationCacheDao
) {
    
    suspend fun getTranslation(original: String, targetLanguage: String = "ru"): TranslationCache? = 
        translationCacheDao.getTranslation(original, targetLanguage)
    
    suspend fun saveTranslation(translation: TranslationCache) = 
        translationCacheDao.insertTranslation(translation)
    
    suspend fun saveTranslations(translations: List<TranslationCache>) = 
        translationCacheDao.insertTranslations(translations)
    
    suspend fun clearCache() = translationCacheDao.clearCache()
    
    suspend fun deleteOldTranslations(daysOld: Int = 90) {
        val timestamp = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L)
        translationCacheDao.deleteOldTranslations(timestamp)
    }
    
    suspend fun getCacheSize(): Int = translationCacheDao.getCacheSize()
}
