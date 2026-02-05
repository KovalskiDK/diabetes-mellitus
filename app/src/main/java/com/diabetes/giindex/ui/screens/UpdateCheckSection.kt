package com.diabetes.giindex.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.diabetes.giindex.data.update.UpdateService
import com.diabetes.giindex.data.update.UpdateManager
import com.diabetes.giindex.data.update.DownloadStatus
import com.diabetes.giindex.ui.components.*
import kotlinx.coroutines.launch

@Composable
fun UpdateCheckSection() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    var isChecking by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    var showNoUpdateDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showProgressDialog by remember { mutableStateOf(false) }
    var downloadProgress by remember { mutableStateOf(0) }
    var errorMessage by remember { mutableStateOf("") }
    var currentVersion by remember { mutableStateOf("") }
    var updateInfo by remember { mutableStateOf<com.diabetes.giindex.data.update.UpdateInfo?>(null) }
    
    val updateService = remember { UpdateService(context) }
    val updateManager = remember { UpdateManager(context) }
    
    SettingsItem(
        title = "Проверить обновления",
        subtitle = if (isChecking) "Проверка..." else "Проверить наличие новой версии",
        onClick = {
            if (!isChecking) {
                isChecking = true
                scope.launch {
                    val result = updateService.checkForUpdates()
                    isChecking = false
                    
                    when {
                        result.error != null -> {
                            errorMessage = result.error
                            showErrorDialog = true
                        }
                        result.hasUpdate && result.updateInfo != null -> {
                            updateInfo = result.updateInfo
                            showUpdateDialog = true
                        }
                        else -> {
                            currentVersion = result.currentVersion
                            showNoUpdateDialog = true
                        }
                    }
                }
            }
        }
    )
    
    if (showUpdateDialog && updateInfo != null) {
        UpdateDialog(
            updateInfo = updateInfo!!,
            onDismiss = { showUpdateDialog = false },
            onUpdate = {
                showUpdateDialog = false
                showProgressDialog = true
                
                scope.launch {
                    updateManager.downloadAndInstallUpdate(updateInfo!!).collect { status ->
                        when (status) {
                            is DownloadStatus.Downloading -> {
                                downloadProgress = status.progress
                            }
                            is DownloadStatus.Completed -> {
                                showProgressDialog = false
                            }
                            is DownloadStatus.Failed -> {
                                showProgressDialog = false
                                errorMessage = status.error
                                showErrorDialog = true
                            }
                        }
                    }
                }
            }
        )
    }
    
    if (showNoUpdateDialog) {
        NoUpdateDialog(
            currentVersion = currentVersion,
            onDismiss = { showNoUpdateDialog = false }
        )
    }
    
    if (showErrorDialog) {
        UpdateErrorDialog(
            error = errorMessage,
            onDismiss = { showErrorDialog = false }
        )
    }
    
    if (showProgressDialog) {
        UpdateProgressDialog(
            progress = downloadProgress,
            onDismiss = { showProgressDialog = false }
        )
    }
}
