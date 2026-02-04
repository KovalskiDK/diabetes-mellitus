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
fun InsulinInfoScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Инсулин и инсулинорезистентность") },
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
            // Что такое инсулин
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
                        text = "🔬 Что такое инсулин?",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "Инсулин — это гормон, который вырабатывается поджелудочной железой. Его можно представить как «ключ», который открывает двери клеток для глюкозы (сахара).",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            // Как работает инсулин
            Text(
                text = "Как работает инсулин?",
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
                    InfoStep(
                        number = "1",
                        title = "Вы едите",
                        description = "Пища переваривается и превращается в глюкозу, которая попадает в кровь"
                    )
                    
                    Divider()
                    
                    InfoStep(
                        number = "2",
                        title = "Поджелудочная железа реагирует",
                        description = "Она чувствует повышение сахара в крови и выделяет инсулин"
                    )
                    
                    Divider()
                    
                    InfoStep(
                        number = "3",
                        title = "Инсулин работает как ключ",
                        description = "Он «открывает» клетки, позволяя глюкозе войти внутрь"
                    )
                    
                    Divider()
                    
                    InfoStep(
                        number = "4",
                        title = "Клетки получают энергию",
                        description = "Глюкоза используется для энергии, а уровень сахара в крови снижается"
                    )
                }
            }
            
            // Зачем нужен инсулин
            Text(
                text = "Зачем нужен инсулин?",
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BulletPoint("Регулирует уровень сахара в крови")
                    BulletPoint("Помогает клеткам получать энергию")
                    BulletPoint("Способствует накоплению энергии в печени и мышцах")
                    BulletPoint("Участвует в обмене белков и жиров")
                }
            }
            
            // Инсулинорезистентность
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "⚠️ Что такое инсулинорезистентность?",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    
                    Text(
                        text = "Инсулинорезистентность — это состояние, когда клетки перестают нормально реагировать на инсулин. Представьте, что «замок» на двери клетки заржавел, и ключ (инсулин) не может его открыть.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            
            // Что происходит при инсулинорезистентности
            Text(
                text = "Что происходит?",
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
                        text = "1️⃣ Клетки не пускают глюкозу внутрь",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Даже если инсулина достаточно, клетки его «не слышат»",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Divider()
                    
                    Text(
                        text = "2️⃣ Сахар остается в крови",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Уровень глюкозы в крови повышается",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Divider()
                    
                    Text(
                        text = "3️⃣ Поджелудочная вырабатывает еще больше инсулина",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Пытается «достучаться» до клеток, производя больше гормона",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Divider()
                    
                    Text(
                        text = "4️⃣ Развивается диабет 2 типа",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Со временем поджелудочная железа истощается и не может производить достаточно инсулина",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Причины инсулинорезистентности
            Text(
                text = "Основные причины",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BulletPoint("🍔 Избыточный вес и ожирение (особенно в области живота)")
                    BulletPoint("🛋️ Малоподвижный образ жизни")
                    BulletPoint("🍰 Избыток простых углеводов в рационе")
                    BulletPoint("🧬 Генетическая предрасположенность")
                    BulletPoint("😴 Хронический стресс и недосып")
                    BulletPoint("💊 Некоторые лекарства (стероиды)")
                }
            }
            
            // Как улучшить чувствительность к инсулину
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
                        text = "✅ Как улучшить чувствительность к инсулину",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    BulletPoint("🏃 Регулярная физическая активность (особенно силовые тренировки)")
                    BulletPoint("⚖️ Снижение веса (даже 5-10% помогает)")
                    BulletPoint("🥗 Питание с низким ГИ и высоким содержанием клетчатки")
                    BulletPoint("😴 Качественный сон 7-9 часов")
                    BulletPoint("🧘 Управление стрессом")
                    BulletPoint("💊 Прием метформина (по назначению врача)")
                }
            }
            
            // Важное примечание
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    
                )
            ) {
                Text(
                    text = "⚕️ Важно: Инсулинорезистентность можно обратить вспять на ранних стадиях с помощью изменения образа жизни. Обязательно консультируйтесь с врачом для правильной диагностики и лечения.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun InfoStep(
    number: String,
    title: String,
    description: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = number,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
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
