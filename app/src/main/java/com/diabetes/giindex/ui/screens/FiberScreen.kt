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
fun FiberScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Клетчатка") },
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
                text = "Пищевые волокна (клетчатка)",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            // Что такое клетчатка
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
                        text = "Что такое клетчатка?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Клетчатка - это неперевариваемые пищевые волокна, которые не усваиваются организмом, но играют важную роль в пищеварении и контроле уровня сахара в крови.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            // Норма потребления
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
                        text = "Суточная норма",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "• Мужчины: 30-38 г/день",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "• Женщины: 21-25 г/день",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "• Дети: 14-31 г/день (зависит от возраста)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            
            Divider()
            
            Text(
                text = "Продукты с высоким содержанием клетчатки",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            // Овощи
            FiberCategoryCard(
                category = "Овощи (на 100г)",
                products = listOf(
                    FiberProduct("Артишок", 8.6, true),
                    FiberProduct("Брокколи", 2.6, true),
                    FiberProduct("Брюссельская капуста", 3.8, true),
                    FiberProduct("Морковь", 2.8, true),
                    FiberProduct("Свекла", 2.8, true),
                    FiberProduct("Капуста белокочанная", 2.5, true),
                    FiberProduct("Шпинат", 2.2, true),
                    FiberProduct("Тыква", 0.5, true),
                    FiberProduct("Баклажан", 3.0, true),
                    FiberProduct("Кабачок", 1.0, true)
                )
            )
            
            // Фрукты и ягоды
            FiberCategoryCard(
                category = "Фрукты и ягоды (на 100г)",
                products = listOf(
                    FiberProduct("Малина", 6.5, true),
                    FiberProduct("Ежевика", 5.3, true),
                    FiberProduct("Груша с кожурой", 3.1, true),
                    FiberProduct("Яблоко с кожурой", 2.4, true),
                    FiberProduct("Клубника", 2.0, true),
                    FiberProduct("Банан", 2.6, false),
                    FiberProduct("Апельсин", 2.4, true),
                    FiberProduct("Черника", 2.4, true),
                    FiberProduct("Авокадо", 6.7, true),
                    FiberProduct("Киви", 3.0, true)
                )
            )
            
            // Бобовые
            FiberCategoryCard(
                category = "Бобовые (вареные, на 100г)",
                products = listOf(
                    FiberProduct("Чечевица", 7.9, true),
                    FiberProduct("Черная фасоль", 8.7, true),
                    FiberProduct("Нут", 7.6, true),
                    FiberProduct("Горох колотый", 8.3, true),
                    FiberProduct("Фасоль красная", 7.4, true),
                    FiberProduct("Соя", 6.0, true),
                    FiberProduct("Эдамаме", 5.2, true)
                )
            )
            
            // Орехи и семена
            FiberCategoryCard(
                category = "Орехи и семена (на 100г)",
                products = listOf(
                    FiberProduct("Семена чиа", 34.4, true),
                    FiberProduct("Семена льна", 27.3, true),
                    FiberProduct("Миндаль", 12.5, true),
                    FiberProduct("Фисташки", 10.6, true),
                    FiberProduct("Семечки подсолнечника", 8.6, true),
                    FiberProduct("Грецкие орехи", 6.7, true),
                    FiberProduct("Арахис", 8.5, true),
                    FiberProduct("Кешью", 3.3, true)
                )
            )
            
            // Крупы и злаки
            FiberCategoryCard(
                category = "Крупы и злаки (на 100г сухого продукта)",
                products = listOf(
                    FiberProduct("Отруби пшеничные", 43.0, true),
                    FiberProduct("Отруби овсяные", 15.4, true),
                    FiberProduct("Киноа", 7.0, true),
                    FiberProduct("Гречка", 10.0, true),
                    FiberProduct("Овсянка", 10.6, true),
                    FiberProduct("Перловка", 15.6, true),
                    FiberProduct("Булгур", 12.5, true),
                    FiberProduct("Коричневый рис", 3.5, true),
                    FiberProduct("Белый рис", 1.3, false)
                )
            )
            
            // Хлеб
            FiberCategoryCard(
                category = "Хлеб (на 100г)",
                products = listOf(
                    FiberProduct("Цельнозерновой хлеб", 6.0, true),
                    FiberProduct("Ржаной хлеб", 5.8, true),
                    FiberProduct("Хлеб с отрубями", 8.5, true),
                    FiberProduct("Белый хлеб", 2.7, false)
                )
            )
            
            Divider()
            
            // Польза клетчатки
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
                    Text(
                        text = "Польза клетчатки при диабете",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "✓ Замедляет усвоение углеводов",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "✓ Снижает скорость повышения сахара в крови",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "✓ Улучшает чувствительность к инсулину",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "✓ Помогает контролировать вес",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "✓ Снижает уровень холестерина",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "✓ Улучшает пищеварение",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
            
            // Рекомендации
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
                    Text(
                        text = "Рекомендации",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "• Увеличивайте потребление клетчатки постепенно",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Пейте больше воды (клетчатка требует жидкости)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Выбирайте цельнозерновые продукты",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Ешьте фрукты с кожурой (где возможно)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Добавляйте овощи в каждый прием пищи",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Замените белый хлеб на цельнозерновой",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
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
                        text = "⚠️ Важно",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        text = "Избыток клетчатки (более 70 г/день) может привести к вздутию, газообразованию и нарушению всасывания минералов. Увеличивайте потребление постепенно.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

data class FiberProduct(
    val name: String,
    val fiberPer100g: Double,
    val recommended: Boolean
)

@Composable
fun FiberCategoryCard(
    category: String,
    products: List<FiberProduct>,
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
            
            products.forEach { product ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${if (product.recommended) "✓" else "○"} ${product.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (product.recommended) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${product.fiberPer100g} г",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = if (product.recommended) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
