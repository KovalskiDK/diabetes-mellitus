package com.diabetes.giindex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrafficLightScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Правила питания «Светофор»") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Система «Светофор» помогает быстро определить, какие продукты можно употреблять при диабете, а какие следует ограничить или исключить.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Визуализация светофора
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TrafficLightCircle(color = Color(0xFF4CAF50), label = "Зеленый")
                    TrafficLightCircle(color = Color(0xFFFFC107), label = "Желтый")
                    TrafficLightCircle(color = Color(0xFFF44336), label = "Красный")
                }
            }
            
            // Зеленая зона
            TrafficLightSection(
                color = Color(0xFF4CAF50),
                title = "🟢 Зеленая зона (ГИ < 55)",
                subtitle = "Можно употреблять без ограничений",
                description = "Продукты с низким гликемическим индексом. Медленно повышают уровень сахара в крови.",
                examples = listOf(
                    "Большинство овощей (огурцы, помидоры, капуста, брокколи)",
                    "Зелень и листовые салаты",
                    "Бобовые (чечевица, фасоль, нут)",
                    "Орехи и семена",
                    "Ягоды (черника, клубника, малина)",
                    "Цельнозерновые продукты",
                    "Нежирные молочные продукты"
                )
            )
            
            // Желтая зона
            TrafficLightSection(
                color = Color(0xFFFFC107),
                title = "🟡 Желтая зона (ГИ 55-69)",
                subtitle = "Употреблять умеренно",
                description = "Продукты со средним гликемическим индексом. Умеренно повышают уровень сахара.",
                examples = listOf(
                    "Некоторые фрукты (бананы, виноград, манго)",
                    "Картофель (вареный)",
                    "Коричневый рис",
                    "Овсяная каша",
                    "Цельнозерновой хлеб",
                    "Макароны из твердых сортов пшеницы",
                    "Свекла"
                )
            )
            
            // Красная зона
            TrafficLightSection(
                color = Color(0xFFF44336),
                title = "🔴 Красная зона (ГИ ≥ 70)",
                subtitle = "Ограничить или исключить",
                description = "Продукты с высоким гликемическим индексом. Быстро и значительно повышают уровень сахара.",
                examples = listOf(
                    "Белый хлеб и сдоба",
                    "Белый рис",
                    "Картофель (жареный, пюре)",
                    "Сладости и кондитерские изделия",
                    "Сладкие напитки",
                    "Кукурузные хлопья",
                    "Финики, арбуз"
                )
            )
            
            // Дополнительные рекомендации
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "💡 Важные рекомендации",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    RecommendationItem("Комбинируйте продукты: добавление белков и жиров снижает общий ГИ приема пищи")
                    RecommendationItem("Учитывайте размер порции: даже продукты из зеленой зоны в больших количествах могут повысить сахар")
                    RecommendationItem("Способ приготовления важен: вареные продукты имеют более низкий ГИ, чем жареные")
                    RecommendationItem("Клетчатка помогает: продукты с высоким содержанием клетчатки замедляют усвоение углеводов")
                    RecommendationItem("Учитывайте гликемическую нагрузку (ГН): она показывает реальное влияние порции продукта")
                }
            }
        }
    }
}

@Composable
fun TrafficLightCircle(
    color: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TrafficLightSection(
    color: Color,
    title: String,
    subtitle: String,
    description: String,
    examples: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(color)
                )
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "Примеры продуктов:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                examples.forEach { example ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodyMedium,
                            color = color
                        )
                        Text(
                            text = example,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendationItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
