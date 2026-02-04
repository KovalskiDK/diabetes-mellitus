package com.diabetes.giindex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diabetes.giindex.data.local.entity.SourceType
import com.diabetes.giindex.ui.viewmodel.DataSourceViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourcesScreen(
    viewModel: DataSourceViewModel,
    onBackClick: () -> Unit,
    onAddSourceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sources by viewModel.sources.collectAsState()
    var sourceToEdit by remember { mutableStateOf<com.diabetes.giindex.data.local.entity.DataSource?>(null) }
    
    sourceToEdit?.let { source ->
        UpdateSourceDialog(
            sourceName = source.name,
            currentUrl = source.url,
            currentType = source.type,
            onDismiss = { sourceToEdit = null },
            onConfirm = { newUrl, newType ->
                viewModel.updateSourceUrlAndType(source.id, newUrl, newType)
                sourceToEdit = null
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Источники данных") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = onAddSourceClick) {
                        Icon(Icons.Filled.Add, contentDescription = "Добавить источник")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (sources.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Нет источников данных",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Добавьте источник для загрузки продуктов",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sources, key = { it.id }) { source ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = source.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = { sourceToEdit = source }) {
                                        Icon(Icons.Filled.Edit, contentDescription = "Редактировать")
                                    }
                                    Switch(
                                        checked = source.isActive,
                                        onCheckedChange = { 
                                            viewModel.toggleSourceActive(source.id, source.isActive)
                                        }
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Версия: ${source.version}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            Text(
                                text = "Записей: ${source.recordsCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            val lastUpdatedText = if (source.lastUpdated > 0) {
                                val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                                "Обновлено: ${dateFormat.format(Date(source.lastUpdated))}"
                            } else {
                                "Обновлено: Никогда"
                            }
                            
                            Text(
                                text = lastUpdatedText,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            if (!source.description.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = source.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            
                            if (source.url.isNotBlank() && source.type != SourceType.MANUAL) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { viewModel.refreshSource(source.id) },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Обновить данные")
                                    }
                                    
                                    if (source.recordsCount > 0) {
                                        OutlinedButton(
                                            onClick = { viewModel.clearSourceData(source.id) },
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("Удалить данные")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
