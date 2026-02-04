package com.diabetes.giindex.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL

data class GitHubFile(
    val name: String,
    val downloadUrl: String
)

object GitHubSourcesApi {
    private const val GITHUB_API_URL = "https://api.github.com/repos/KovalskiDK/diabetes-mellitus/contents/sources"
    private const val RAW_GITHUB_URL = "https://raw.githubusercontent.com/KovalskiDK/diabetes-mellitus/main/sources"
    
    suspend fun getAvailableSources(): List<GitHubFile> = withContext(Dispatchers.IO) {
        try {
            val connection = URL(GITHUB_API_URL).openConnection()
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json")
            
            val response = connection.getInputStream().bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)
            
            val files = mutableListOf<GitHubFile>()
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val name = item.getString("name")
                
                // Только JSON файлы
                if (name.endsWith(".json")) {
                    val fileName = name.removeSuffix(".json")
                    files.add(
                        GitHubFile(
                            name = fileName,
                            downloadUrl = "$RAW_GITHUB_URL/$name"
                        )
                    )
                }
            }
            
            files
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
