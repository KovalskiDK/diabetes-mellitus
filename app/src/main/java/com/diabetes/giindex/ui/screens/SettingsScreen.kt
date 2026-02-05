package com.diabetes.giindex.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.diabetes.giindex.data.update.UpdateService
import com.diabetes.giindex.data.update.UpdateManager
import com.diabetes.giindex.data.update.DownloadStatus
import com.diabetes.giindex.ui.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: com.diabetes.giindex.ui.viewmodel.SettingsViewModel,
    onBackClick: () -> Unit,
    onSourcesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SettingsSection(title = "Данные") {
                SettingsItem(
                    title = "Источники данных ГИ",
                    subtitle = "Управление источниками ГИ",
                    onClick = onSourcesClick
                )
                SettingsItem(
                    title = "Очистить кэш",
                    subtitle = "Удалить все кэшированные данные (изображения, AI-анализы)",
                    onClick = {
                        try {
                            context.cacheDir.deleteRecursively()
                            android.widget.Toast.makeText(
                                context,
                                "Кэш успешно очищен",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            android.widget.Toast.makeText(
                                context,
                                "Ошибка при очистке кэша: ${e.message}",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }
            
            Divider()
            
            SettingsSection(title = "О приложении") {
                SettingsItem(
                    title = "Версия",
                    subtitle = "1.9.0",
                    onClick = { }
                )
                
                UpdateCheckSection()
                
                SettingsItem(
                    title = "GitHub",
                    subtitle = "github.com/KovalskiDK/diabetes-mellitus",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/KovalskiDK/diabetes-mellitus"))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        content()
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
