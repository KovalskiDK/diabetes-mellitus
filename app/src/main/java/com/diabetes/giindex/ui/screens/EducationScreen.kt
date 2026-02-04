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
fun EducationScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Об углеводах") },
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
                text = "Типы углеводов и их усвоение",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            // Глюкоза
            EducationCard(
                title = "Глюкоза (декстроза)",
                description = "Простой сахар, основной источник энергии для клеток организма.",
                details = listOf(
                    "• Усваивается напрямую, без расщепления",
                    "• Быстро повышает уровень сахара в крови",
                    "• ГИ: 100 (эталонное значение)",
                    "• Содержится в: меде, фруктах, ягодах",
                    "• Время усвоения: 5-15 минут"
                )
            )
            
            // Фруктоза
            EducationCard(
                title = "Фруктоза",
                description = "Фруктовый сахар, самый сладкий из природных сахаров.",
                details = listOf(
                    "• Усваивается медленнее глюкозы",
                    "• Метаболизируется в печени",
                    "• ГИ: 19 (низкий)",
                    "• Содержится в: фруктах, меде, агаве",
                    "• Не требует инсулина для усвоения",
                    "• Избыток может привести к жировым отложениям"
                )
            )
            
            // Сахароза
            EducationCard(
                title = "Сахароза (столовый сахар)",
                description = "Дисахарид, состоящий из глюкозы и фруктозы.",
                details = listOf(
                    "• Расщепляется на глюкозу и фруктозу",
                    "• Быстро повышает сахар в крови",
                    "• ГИ: 65 (средний)",
                    "• Содержится в: сахаре, сладостях, выпечке",
                    "• Время усвоения: 15-30 минут"
                )
            )
            
            // Лактоза
            EducationCard(
                title = "Лактоза (молочный сахар)",
                description = "Дисахарид, состоящий из глюкозы и галактозы.",
                details = listOf(
                    "• Расщепляется ферментом лактазой",
                    "• Медленнее повышает сахар в крови",
                    "• ГИ: 46 (низкий)",
                    "• Содержится в: молоке, йогурте, сыре",
                    "• Некоторые люди не переносят лактозу"
                )
            )
            
            // Мальтоза
            EducationCard(
                title = "Мальтоза (солодовый сахар)",
                description = "Дисахарид, состоящий из двух молекул глюкозы.",
                details = listOf(
                    "• Быстро расщепляется до глюкозы",
                    "• Очень быстро повышает сахар в крови",
                    "• ГИ: 105 (очень высокий)",
                    "• Содержится в: пиве, солоде, патоке",
                    "• Образуется при расщеплении крахмала"
                )
            )
            
            // Крахмал
            EducationCard(
                title = "Крахмал",
                description = "Сложный углевод (полисахарид), состоящий из множества молекул глюкозы.",
                details = listOf(
                    "• Медленно расщепляется до глюкозы",
                    "• Постепенно повышает сахар в крови",
                    "• ГИ: зависит от типа (40-90)",
                    "• Содержится в: картофеле, крупах, хлебе",
                    "• Время усвоения: 1-3 часа",
                    "• Резистентный крахмал не переваривается"
                )
            )
            
            // Клетчатка
            EducationCard(
                title = "Клетчатка (пищевые волокна)",
                description = "Неперевариваемые углеводы, важные для здоровья.",
                details = listOf(
                    "• Не повышает уровень сахара в крови",
                    "• ГИ: 0",
                    "• Замедляет усвоение других углеводов",
                    "• Содержится в: овощах, фруктах, цельнозерновых",
                    "• Улучшает пищеварение",
                    "• Растворимая клетчатка снижает холестерин"
                )
            )
            
            Divider()
            
            // Процесс усвоения
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
                        text = "Процесс усвоения углеводов",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "1. Ротовая полость",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Фермент амилаза начинает расщеплять крахмал",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "2. Желудок",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Кислота останавливает действие амилазы, углеводы проходят дальше",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "3. Тонкий кишечник",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Ферменты поджелудочной железы расщепляют углеводы до моносахаридов (глюкоза, фруктоза, галактоза)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "4. Всасывание",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Моносахариды всасываются через стенки кишечника в кровь",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "5. Печень",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Фруктоза и галактоза преобразуются в глюкозу или запасаются как гликоген",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "6. Кровоток",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Глюкоза поступает в кровь, поджелудочная железа выделяет инсулин для доставки глюкозы в клетки",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Divider()
            
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
                        text = "Рекомендации для людей с диабетом",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    
                    Text(
                        text = "• Выбирайте продукты с низким ГИ (до 55)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "• Комбинируйте углеводы с белками и жирами",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "• Употребляйте больше клетчатки",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "• Избегайте простых сахаров (сахароза, мальтоза)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "• Контролируйте размер порций",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "• Учитывайте гликемическую нагрузку (ГН)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun EducationCard(
    title: String,
    description: String,
    details: List<String>,
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
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            details.forEach { detail ->
                Text(
                    text = detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
