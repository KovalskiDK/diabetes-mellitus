package com.diabetes.giindex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diabetes.giindex.data.DatabaseInitializer
import com.diabetes.giindex.data.local.GIIndexDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugScreen(
    database: GIIndexDatabase,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var message by remember { mutableStateOf("Нажмите кнопку для инициализации") }
    var isLoading by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Отладка") },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Инициализация базы данных",
                style = MaterialTheme.typography.titleLarge
            )
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
            
            if (isLoading) {
                CircularProgressIndicator()
            }
            
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        message = "Инициализация..."
                        try {
                            DatabaseInitializer.initializeDatabase(database)
                            
                            val productCount = database.productDao().getProductCount()
                            val sourceCount = database.dataSourceDao().getAllSources().first().size
                            
                            message = "Готово!\nПродуктов: $productCount\nИсточников: $sourceCount"
                        } catch (e: Exception) {
                            message = "Ошибка: ${e.message}"
                            android.util.Log.e("DebugScreen", "Error", e)
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading
            ) {
                Text("Запустить инициализацию")
            }
            
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        message = "Проверка..."
                        try {
                            val productCount = database.productDao().getProductCount()
                            val sourceCount = database.dataSourceDao().getAllSources().first().size
                            
                            message = "Текущее состояние:\nПродуктов: $productCount\nИсточников: $sourceCount"
                        } catch (e: Exception) {
                            message = "Ошибка: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading
            ) {
                Text("Проверить состояние")
            }
        }
    }
}
