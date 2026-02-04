package com.diabetes.giindex.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Информация для диабетиков") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            ExpandableInfoCard(
                title = "Что такое гликемический индекс (ГИ)?",
                content = """
                    Гликемический индекс показывает, как быстро продукт повышает уровень сахара в крови после его употребления.
                    
                    • Низкий ГИ (1-55): сахар поднимается медленно и плавно
                    • Средний ГИ (56-69): умеренная скорость повышения
                    • Высокий ГИ (70+): резкий скачок сахара в крови
                    
                    Продукты с низким ГИ лучше для контроля уровня сахара.
                """.trimIndent()
            )
            
            ExpandableInfoCard(
                title = "Что такое гликемическая нагрузка (ГН)?",
                content = """
                    Гликемическая нагрузка учитывает не только скорость повышения сахара, но и количество углеводов в порции продукта.
                    
                    • Низкая ГН (≤10): минимальное влияние на сахар
                    • Средняя ГН (11-19): умеренное влияние
                    • Высокая ГН (≥20): сильное влияние на уровень сахара
                    
                    ГН = (ГИ × углеводы в порции) / 100
                """.trimIndent()
            )
            
            ExpandableInfoCard(
                title = "В чем разница между ГИ и ГН?",
                content = """
                    Простыми словами:
                    
                    🔸 ГИ - это СКОРОСТЬ: как быстро сахар попадет в кровь
                    🔸 ГН - это КОЛИЧЕСТВО: сколько всего сахара попадет в кровь
                    
                    Пример с арбузом:
                    • ГИ арбуза = 76 (высокий!) - сахар поднимется быстро
                    • ГН арбуза = 4 (низкая!) - но углеводов в порции мало
                    
                    Вывод: арбуз можно есть, несмотря на высокий ГИ, потому что ГН низкая.
                """.trimIndent()
            )
            
            ExpandableInfoCard(
                title = "Что важнее - ГИ или ГН?",
                content = """
                    Для людей с диабетом важны ОБА показателя:
                    
                    ✅ Идеально: низкий ГИ + низкая ГН
                    ⚠️ Осторожно: высокий ГИ + низкая ГН (можно в малых количествах)
                    ⚠️ Осторожно: низкий ГИ + высокая ГН (следить за размером порции)
                    ❌ Избегать: высокий ГИ + высокая ГН
                    
                    Гликемическая нагрузка более точно показывает реальное влияние продукта на организм.
                """.trimIndent()
            )
            
            ExpandableInfoCard(
                title = "Практические советы",
                content = """
                    1. Смотрите на размер порции - он влияет на ГН
                    2. Комбинируйте продукты: белки и жиры снижают общий ГИ
                    3. Цельные продукты лучше переработанных
                    4. Готовка влияет на ГИ: al dente паста лучше разваренной
                    5. Холодная картошка имеет ниже ГИ, чем горячая
                """.trimIndent()
            )
            
            ExpandableInfoCard(
                title = "💡 Примеры для понимания",
                content = """
                    Белый хлеб (30г):
                    ГИ = 75 (высокий), ГН = 11 (средняя)
                    
                    Яблоко (120г):
                    ГИ = 38 (низкий), ГН = 6 (низкая)
                    
                    Арбуз (120г):
                    ГИ = 76 (высокий), ГН = 4 (низкая)
                """.trimIndent()
            )
        }
    }
}

@Composable
fun ExpandableInfoCard(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Свернуть" else "Развернуть"
                )
            }
            
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Divider(modifier = Modifier.padding(bottom = 12.dp))
                    Text(
                        text = content,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = MaterialTheme.typography.bodyMedium.fontSize * 1.5
                    )
                }
            }
        }
    }
}
