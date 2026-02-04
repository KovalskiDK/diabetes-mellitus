package com.diabetes.giindex.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object SourceDataLoader {
    
    suspend fun loadFromUrl(url: String): LoadResult {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection()
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                
                val content = connection.getInputStream().bufferedReader().use { it.readText() }
                LoadResult.Success(content)
            } catch (e: Exception) {
                LoadResult.Error(e.message ?: "Ошибка загрузки данных")
            }
        }
    }
}

sealed class LoadResult {
    data class Success(val content: String) : LoadResult()
    data class Error(val message: String) : LoadResult()
}
