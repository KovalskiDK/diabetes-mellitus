package com.diabetes.giindex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diabetes.giindex.data.local.entity.SourceType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateSourceDialog(
    sourceName: String,
    currentUrl: String,
    currentType: SourceType,
    onDismiss: () -> Unit,
    onConfirm: (String, SourceType) -> Unit
) {
    var url by remember { mutableStateOf(currentUrl) }
    var selectedType by remember { mutableStateOf(currentType) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Обновить источник: $sourceName") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("URL") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Text("Тип источника:", style = MaterialTheme.typography.labelMedium)
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedType == SourceType.JSON,
                        onClick = { selectedType = SourceType.JSON },
                        label = { Text("JSON") }
                    )
                    FilterChip(
                        selected = selectedType == SourceType.CSV,
                        onClick = { selectedType = SourceType.CSV },
                        label = { Text("CSV") }
                    )
                    FilterChip(
                        selected = selectedType == SourceType.API,
                        onClick = { selectedType = SourceType.API },
                        label = { Text("API") }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(url, selectedType) }
            ) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}
