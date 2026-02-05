package com.diabetes.giindex.data.update

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File

class UpdateManager(private val context: Context) {
    
    companion object {
        private const val TAG = "UpdateManager"
    }
    
    fun downloadAndInstallUpdate(updateInfo: UpdateInfo): Flow<DownloadStatus> = callbackFlow {
        try {
            Log.d(TAG, "Starting download: ${updateInfo.downloadUrl}")
            
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            
            val request = DownloadManager.Request(Uri.parse(updateInfo.downloadUrl))
                .setTitle("Обновление GI Index")
                .setDescription("Загрузка версии ${updateInfo.versionName}")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "GI_Index_${updateInfo.versionName}.apk"
                )
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
            
            val downloadId = downloadManager.enqueue(request)
            Log.d(TAG, "Download started with ID: $downloadId")
            
            trySend(DownloadStatus.Downloading(0))
            
            // Запускаем корутину для отслеживания прогресса
            val progressJob = launch {
                while (isActive) {
                    try {
                        val query = DownloadManager.Query().setFilterById(downloadId)
                        val cursor = downloadManager.query(query)
                        
                        if (cursor.moveToFirst()) {
                            val bytesDownloadedIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                            val bytesTotalIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                            val statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                            
                            val bytesDownloaded = cursor.getLong(bytesDownloadedIndex)
                            val bytesTotal = cursor.getLong(bytesTotalIndex)
                            val status = cursor.getInt(statusIndex)
                            
                            if (bytesTotal > 0) {
                                val progress = ((bytesDownloaded * 100) / bytesTotal).toInt()
                                Log.d(TAG, "Download progress: $progress% ($bytesDownloaded / $bytesTotal)")
                                trySend(DownloadStatus.Downloading(progress))
                            }
                            
                            // Если загрузка завершена или провалилась, выходим из цикла
                            if (status == DownloadManager.STATUS_SUCCESSFUL || 
                                status == DownloadManager.STATUS_FAILED) {
                                break
                            }
                        }
                        cursor.close()
                        delay(500) // Проверяем каждые 500мс
                    } catch (e: Exception) {
                        Log.e(TAG, "Error checking download progress", e)
                    }
                }
            }
            
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (id == downloadId) {
                        Log.d(TAG, "Download completed")
                        
                        val query = DownloadManager.Query().setFilterById(downloadId)
                        val cursor = downloadManager.query(query)
                        
                        if (cursor.moveToFirst()) {
                            val statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                            val status = cursor.getInt(statusIndex)
                            
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                val uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                                val uriString = cursor.getString(uriIndex)
                                Log.d(TAG, "Download URI: $uriString")
                                
                                trySend(DownloadStatus.Completed(uriString))
                                installApk(uriString)
                            } else {
                                Log.e(TAG, "Download failed with status: $status")
                                trySend(DownloadStatus.Failed("Ошибка загрузки"))
                            }
                        }
                        cursor.close()
                    }
                }
            }
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(
                    receiver,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                    Context.RECEIVER_NOT_EXPORTED
                )
            } else {
                context.registerReceiver(
                    receiver,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
                )
            }
            
            awaitClose {
                try {
                    progressJob.cancel()
                    context.unregisterReceiver(receiver)
                } catch (e: Exception) {
                    Log.e(TAG, "Error unregistering receiver", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error downloading update", e)
            trySend(DownloadStatus.Failed(e.message ?: "Неизвестная ошибка"))
        }
    }
    
    private fun installApk(uriString: String) {
        try {
            val file = File(Uri.parse(uriString).path ?: return)
            Log.d(TAG, "Installing APK: ${file.absolutePath}")
            
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            
            val apkUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )
            } else {
                Uri.fromFile(file)
            }
            
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error installing APK", e)
        }
    }
}

sealed class DownloadStatus {
    data class Downloading(val progress: Int) : DownloadStatus()
    data class Completed(val uri: String) : DownloadStatus()
    data class Failed(val error: String) : DownloadStatus()
}
