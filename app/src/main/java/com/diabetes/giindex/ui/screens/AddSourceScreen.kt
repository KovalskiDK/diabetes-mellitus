package com.diabetes.giindex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diabetes.giindex.data.local.entity.DataSource
import com.diabetes.giindex.data.local.entity.SourceType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSourceScreen(
    onBackClick: () -> Unit,
    onSaveClick: (DataSource) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(SourceType.JSON) }
    var showError by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить источник") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (name.isNotBlank() && url.isNotBlank()) {
                                val source = DataSource(
                                    name = name.trim(),
                                    url = url.trim(),
                                    type = selectedType,
                                    version = "1.0",
                                    description = description.trim().takeIf { it.isNotBlank() }
                                )
                                onSaveClick(source)
                            } else {
                                showError = true
                            }
                        }
                    ) {
                        Text("Сохранить")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { 
                    name = it
                    showError = false
                },
                label = { Text("Название источника *") },
                placeholder = { Text("Sydney University GI Database") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && name.isBlank(),
                supportingText = if (showError && name.isBlank()) {
                    { Text("Обязательное поле") }
                } else null
            )
            
            OutlinedTextField(
                value = url,
                onValueChange = { 
                    url = it
                    showError = false
                },
                label = { Text("URL источника *") },
                placeholder = { Text("https://example.com/data.json") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && url.isBlank(),
                supportingText = if (showError && url.isBlank()) {
                    { Text("Обязательное поле") }
                } else null
            )
            
            Text(
                text = "Тип источника",
                style = MaterialTheme.typography.bodyLarge
            )
            
            SourceType.values().forEach { type ->
                val (title, subtitle) = when (type) {
                    SourceType.JSON -> "JSON файл" to "Структурированные данные в формате JSON"
                    SourceType.CSV -> "CSV файл" to "Таблица с разделителями"
                    SourceType.XML -> "XML файл" to "XML структура данных"
                    SourceType.API -> "API endpoint" to "REST API для получения данных"
                    SourceType.MANUAL -> "Ручной ввод" to "Добавление продуктов вручную"
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RadioButton(
                        selected = selectedType == type,
                        onClick = { selectedType = type }
                    )
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
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание (необязательно)") },
                placeholder = { Text("Официальная база данных гликемических индексов") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "💡 Подсказка",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "После добавления источника вы сможете загрузить данные из него на экране 'Источники данных'.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
