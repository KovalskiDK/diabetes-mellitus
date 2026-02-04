package com.diabetes.giindex.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.diabetes.giindex.data.preferences.ProductLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: com.diabetes.giindex.ui.viewmodel.SettingsViewModel,
    onBackClick: () -> Unit,
    onSourcesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val productLanguage by viewModel.productLanguage.collectAsState()
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
                    title = "Источники данных",
                    subtitle = "Управление источниками ГИ",
                    onClick = onSourcesClick
                )
                SettingsItem(
                    title = "Очистить кэш переводов",
                    subtitle = "Удалить сохраненные переводы",
                    onClick = { /* TODO */ }
                )
            }
            
            Divider()
            
            SettingsSection(title = "Отображение") {
                LanguageSelectionItem(
                    selectedLanguage = productLanguage,
                    onLanguageSelected = { viewModel.setProductLanguage(it) }
                )
            }
            
            Divider()
            
            SettingsSection(title = "О приложении") {
                SettingsItem(
                    title = "Версия",
                    subtitle = "1.0.0",
                    onClick = { }
                )
                SettingsItem(
                    title = "GitHub",
                    subtitle = "github.com/KovalskiDK/diabetes-mellitus",
                    onClick = { }
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

@Composable
fun LanguageSelectionItem(
    selectedLanguage: ProductLanguage,
    onLanguageSelected: (ProductLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .selectableGroup()
    ) {
        Text(
            text = "Язык названий продуктов",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        ProductLanguage.values().forEach { language ->
            val (title, subtitle) = when (language) {
                ProductLanguage.RUSSIAN -> "Русский" to "Показывать только русские названия"
                ProductLanguage.ENGLISH -> "Английский" to "Показывать только английские названия"
                ProductLanguage.BOTH -> "Оба языка" to "Русский + английский мелким шрифтом"
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (language == selectedLanguage),
                        onClick = { onLanguageSelected(language) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (language == selectedLanguage),
                    onClick = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
