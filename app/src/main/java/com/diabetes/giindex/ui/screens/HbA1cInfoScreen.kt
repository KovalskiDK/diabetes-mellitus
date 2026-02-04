package com.diabetes.giindex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HbA1cInfoScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Анализ HbA1c") },
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
            // Что такое HbA1c
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "🔬 Что такое HbA1c?",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "HbA1c (гликированный гемоглобин) — это анализ крови, который показывает средний уровень сахара в крови за последние 2-3 месяца.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "Простыми словами: это «память» вашей крови о том, сколько сахара в ней было в течение нескольких месяцев.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            // Как это работает
            Text(
                text = "Как это работает?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "🩸 Глюкоза в крови \"прилипает\" к гемоглобину (белку в красных кровяных клетках)",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Divider()
                    
                    Text(
                        text = "⏱️ Чем выше уровень сахара и чем дольше он повышен, тем больше глюкозы прилипает",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Divider()
                    
                    Text(
                        text = "🔄 Красные кровяные клетки живут около 120 дней (3 месяца)",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Divider()
                    
                    Text(
                        text = "📊 Анализ показывает процент гемоглобина, к которому прилипла глюкоза",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            
            // Нормы HbA1c
            Text(
                text = "Нормы HbA1c",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NormRow(
                        range = "< 5.7%",
                        status = "✅ Норма",
                        description = "Нормальный уровень сахара"
                    )
                    
                    Divider()
                    
                    NormRow(
                        range = "5.7% - 6.4%",
                        status = "⚠️ Преддиабет",
                        description = "Повышенный риск развития диабета"
                    )
                    
                    Divider()
                    
                    NormRow(
                        range = "≥ 6.5%",
                        status = "❌ Диабет",
                        description = "Диагноз сахарный диабет"
                    )
                    
                    Divider()
                    
                    Text(
                        text = "Целевой уровень при диабете:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    NormRow(
                        range = "< 7%",
                        status = "🎯 Хороший контроль",
                        description = "Рекомендуемый уровень для большинства людей с диабетом"
                    )
                    
                    NormRow(
                        range = "< 6.5%",
                        status = "⭐ Отличный контроль",
                        description = "Для молодых людей без осложнений"
                    )
                }
            }
            
            // Формула пересчета
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "📐 Формула пересчета HbA1c в средний уровень глюкозы",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Средняя глюкоза (ммоль/л) = (HbA1c × 1.59) - 2.59",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Text(
                                text = "или",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            Text(
                                text = "Средняя глюкоза (мг/дл) = (HbA1c × 28.7) - 46.7",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Text(
                        text = "Примеры:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    
                    ExampleRow("HbA1c 5%", "≈ 5.4 ммоль/л (97 мг/дл)")
                    ExampleRow("HbA1c 6%", "≈ 7.0 ммоль/л (126 мг/дл)")
                    ExampleRow("HbA1c 7%", "≈ 8.6 ммоль/л (154 мг/дл)")
                    ExampleRow("HbA1c 8%", "≈ 10.2 ммоль/л (183 мг/дл)")
                    ExampleRow("HbA1c 9%", "≈ 11.8 ммоль/л (212 мг/дл)")
                }
            }
            
            // Преимущества анализа
            Text(
                text = "Преимущества HbA1c",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BulletPoint("✅ Не нужно голодать перед анализом")
                    BulletPoint("✅ Можно сдавать в любое время дня")
                    BulletPoint("✅ Показывает долгосрочный контроль сахара")
                    BulletPoint("✅ Не зависит от стресса или физической активности в день анализа")
                    BulletPoint("✅ Помогает оценить эффективность лечения")
                }
            }
            
            // Ограничения
            Text(
                text = "Ограничения анализа",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "⚠️ Результат может быть неточным при:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    
                    BulletPoint("Анемии (низкий гемоглобин)")
                    BulletPoint("Недавнем кровотечении или переливании крови")
                    BulletPoint("Беременности")
                    BulletPoint("Заболеваниях почек или печени")
                    BulletPoint("Приеме некоторых лекарств")
                }
            }
            
            // Как часто сдавать
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "📅 Как часто сдавать HbA1c?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    BulletPoint("Без диабета (профилактика): 1 раз в год")
                    BulletPoint("Преддиабет: каждые 3-6 месяцев")
                    BulletPoint("Диабет с хорошим контролем: каждые 6 месяцев")
                    BulletPoint("Диабет с плохим контролем: каждые 3 месяца")
                    BulletPoint("При изменении лечения: через 3 месяца")
                }
            }
            
            // Важное примечание
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = "⚕️ Важно: HbA1c — это один из ключевых анализов для контроля диабета, но он не заменяет регулярное измерение глюкозы глюкометром. Обязательно консультируйтесь с врачом для интерпретации результатов и корректировки лечения.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun NormRow(
    range: String,
    status: String,
    description: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = range,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = status,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ExampleRow(hba1c: String, glucose: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = hba1c,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = glucose,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun BulletPoint(text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}
