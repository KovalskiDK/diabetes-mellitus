package com.diabetes.giindex.data.update

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class UpdateService(private val context: Context) {
    
    companion object {
        private const val TAG = "UpdateService"
        private const val VERSION_URL = "https://raw.githubusercontent.com/KovalskiDK/diabetes-mellitus/main/version.json"
    }
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()
    
    suspend fun checkForUpdates(): UpdateCheckResult = withContext(Dispatchers.IO) {
        try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(context.packageName, 0)
            }
            
            val currentVersionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toInt()
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode
            }
            val currentVersionName = packageInfo.versionName ?: "Unknown"
            
            Log.d(TAG, "Checking for updates...")
            Log.d(TAG, "Current version: $currentVersionName ($currentVersionCode)")
            
            val request = Request.Builder()
                .url(VERSION_URL)
                .addHeader("Cache-Control", "no-cache, no-store, must-revalidate")
                .addHeader("Pragma", "no-cache")
                .addHeader("Expires", "0")
                .build()
            
            val response = client.newCall(request).execute()
            
            if (!response.isSuccessful) {
                Log.e(TAG, "Failed to check updates: ${response.code}")
                return@withContext UpdateCheckResult(
                    hasUpdate = false,
                    currentVersion = currentVersionName,
                    error = "Ошибка сервера: ${response.code}"
                )
            }
            
            val jsonString = response.body?.string() ?: ""
            Log.d(TAG, "Response: $jsonString")
            
            val json = JSONObject(jsonString)
            val updateInfo = UpdateInfo(
                versionCode = json.getInt("versionCode"),
                versionName = json.getString("versionName"),
                downloadUrl = json.getString("downloadUrl"),
                releaseNotes = json.getString("releaseNotes"),
                mandatory = json.optBoolean("mandatory", false),
                minSupportedVersion = json.optInt("minSupportedVersion", 0),
                releaseDate = json.optString("releaseDate", "")
            )
            
            val hasUpdate = updateInfo.versionCode > currentVersionCode
            
            Log.d(TAG, "Latest version: ${updateInfo.versionName} (${updateInfo.versionCode})")
            Log.d(TAG, "Has update: $hasUpdate")
            
            UpdateCheckResult(
                hasUpdate = hasUpdate,
                updateInfo = if (hasUpdate) updateInfo else null,
                currentVersion = currentVersionName
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error checking for updates", e)
            UpdateCheckResult(
                hasUpdate = false,
                currentVersion = "Unknown",
                error = "Ошибка: ${e.message}"
            )
        }
    }
}
