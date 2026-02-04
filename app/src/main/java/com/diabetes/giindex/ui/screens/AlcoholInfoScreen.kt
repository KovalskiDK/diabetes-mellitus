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
fun AlcoholInfoScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Алкоголь и диабет") },
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
                text = "Гликемический индекс алкоголя",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            // Предупреждение
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
                        text = "⚠️ Важно!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        text = "Алкоголь опасен для людей с диабетом! Может вызвать гипогликемию (резкое падение сахара в крови) через несколько часов после употребления.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            
            Divider()
            
            Text(
                text = "ГИ и ГН алкогольных напитков",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            // Крепкие напитки
            AlcoholCategoryCard(
                category = "Крепкие напитки (40%)",
                drinks = listOf(
                    AlcoholDrink("Водка", 0, 0.0, 0.0),
                    AlcoholDrink("Виски", 0, 0.0, 0.0),
                    AlcoholDrink("Коньяк", 0, 0.0, 0.0),
                    AlcoholDrink("Текила", 0, 0.0, 0.0),
                    AlcoholDrink("Ром", 0, 0.0, 0.0),
                    AlcoholDrink("Джин", 0, 0.0, 0.0)
                ),
                note = "Не содержат углеводов, но опасны для печени и могут вызвать отсроченную гипогликемию"
            )
            
            // Вино
            AlcoholCategoryCard(
                category = "Вино (12-14%)",
                drinks = listOf(
                    AlcoholDrink("Красное сухое вино", 0, 0.0, 0.3),
                    AlcoholDrink("Белое сухое вино", 0, 0.0, 0.6),
                    AlcoholDrink("Розовое сухое вино", 0, 0.0, 0.5),
                    AlcoholDrink("Красное полусладкое", 30, 3.0, 5.0),
                    AlcoholDrink("Белое полусладкое", 30, 3.5, 6.0),
                    AlcoholDrink("Десертное вино", 45, 8.0, 12.0),
                    AlcoholDrink("Шампанское сухое", 35, 1.5, 1.5),
                    AlcoholDrink("Шампанское полусладкое", 45, 5.0, 9.0)
                ),
                note = "Сухие вина относительно безопасны в малых дозах (100-150 мл)"
            )
            
            // Пиво
            AlcoholCategoryCard(
                category = "Пиво (4-6%)",
                drinks = listOf(
                    AlcoholDrink("Светлое пиво", 66, 6.0, 3.6),
                    AlcoholDrink("Темное пиво", 110, 13.0, 4.5),
                    AlcoholDrink("Безалкогольное пиво", 50, 5.0, 4.0)
                ),
                note = "Содержит много углеводов, быстро повышает сахар"
            )
            
            // Ликеры и коктейли
            AlcoholCategoryCard(
                category = "Ликеры и коктейли",
                drinks = listOf(
                    AlcoholDrink("Ликер сладкий", 60, 20.0, 30.0),
                    AlcoholDrink("Мартини", 40, 8.0, 15.0),
                    AlcoholDrink("Вермут", 35, 7.0, 15.0),
                    AlcoholDrink("Коктейль Мохито", 50, 12.0, 20.0),
                    AlcoholDrink("Коктейль Пина Колада", 55, 15.0, 25.0),
                    AlcoholDrink("Коктейль Маргарита", 45, 10.0, 18.0)
                ),
                note = "Очень опасны! Много сахара + алкоголь"
            )
            
            Divider()
            
            // Как алкоголь влияет на сахар
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
                        text = "Как алкоголь влияет на уровень сахара",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "1. Сразу после употребления:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Сладкие напитки повышают сахар в крови",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "2. Через 2-12 часов:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Печень перестает вырабатывать глюкозу, так как занята переработкой алкоголя. Это может привести к опасной гипогликемии!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            // Рекомендации
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
                        text = "Рекомендации при диабете",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    
                    Text(
                        text = "✓ Максимум 1-2 порции в день",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "✓ Только во время или после еды",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "✓ Измеряйте сахар до, во время и после",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "✓ Имейте при себе быстрые углеводы",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "✓ Предупредите окружающих о диабете",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "✗ Никогда не пейте натощак",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "✗ Избегайте сладких коктейлей и ликеров",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

data class AlcoholDrink(
    val name: String,
    val gi: Int,
    val gl: Double,
    val carbsPer100ml: Double
)

@Composable
fun AlcoholCategoryCard(
    category: String,
    drinks: List<AlcoholDrink>,
    note: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            drinks.forEach { drink ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = drink.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "ГИ: ${drink.gi} | ГН: ${drink.gl} | ${drink.carbsPer100ml}г",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "ℹ️ $note",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
