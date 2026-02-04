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
fun MedicalDietsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Лечебные столы питания") },
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
                text = "Система лечебных столов по Певзнеру — это классификация диет для различных заболеваний, разработанная советским ученым М.И. Певзнером.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Стол №1
            DietCard(
                number = "1",
                title = "Стол №1",
                indication = "Язвенная болезнь желудка и двенадцатиперстной кишки",
                description = "Щадящая диета с механическим, химическим и термическим щажением слизистой оболочки желудка.",
                allowed = listOf(
                    "Протертые супы на овощном отваре",
                    "Паровые котлеты, фрикадельки",
                    "Отварная рыба нежирных сортов",
                    "Молоко, сливки, творог",
                    "Каши протертые (овсяная, манная, рисовая)",
                    "Яйца всмятку, паровой омлет",
                    "Белый хлеб вчерашней выпечки"
                ),
                forbidden = listOf(
                    "Жирные, жареные блюда",
                    "Острые приправы и специи",
                    "Свежий хлеб, сдоба",
                    "Крепкие бульоны",
                    "Кислые фрукты и ягоды",
                    "Газированные напитки",
                    "Кофе, крепкий чай"
                )
            )
            
            // Стол №2
            DietCard(
                number = "2",
                title = "Стол №2",
                indication = "Хронический гастрит с пониженной кислотностью",
                description = "Стимулирует секреторную функцию желудка, обеспечивает механическое щажение.",
                allowed = listOf(
                    "Супы на мясном, рыбном бульоне",
                    "Нежирное мясо, птица, рыба",
                    "Кисломолочные продукты",
                    "Каши рассыпчатые",
                    "Овощи отварные и тушеные",
                    "Спелые фрукты и ягоды",
                    "Некрепкий чай, кофе с молоком"
                ),
                forbidden = listOf(
                    "Жирные сорта мяса и рыбы",
                    "Острые блюда",
                    "Свежий хлеб",
                    "Бобовые",
                    "Виноград, виноградный сок",
                    "Цельное молоко в больших количествах"
                )
            )
            
            // Стол №5
            DietCard(
                number = "5",
                title = "Стол №5",
                indication = "Заболевания печени, желчного пузыря, желчевыводящих путей",
                description = "Ограничение жиров и холестерина, достаточное количество белков и углеводов.",
                allowed = listOf(
                    "Вегетарианские супы",
                    "Нежирное мясо, птица без кожи",
                    "Отварная, запеченная рыба",
                    "Молочные продукты пониженной жирности",
                    "Каши на воде с молоком",
                    "Овощи отварные, тушеные",
                    "Сладкие фрукты и ягоды",
                    "Мед, варенье, пастила"
                ),
                forbidden = listOf(
                    "Жирные сорта мяса, рыбы",
                    "Жареные блюда",
                    "Острые приправы",
                    "Бобовые",
                    "Грибы",
                    "Кислые фрукты",
                    "Шоколад, мороженое",
                    "Алкоголь"
                )
            )
            
            // Стол №8
            DietCard(
                number = "8",
                title = "Стол №8",
                indication = "Ожирение",
                description = "Снижение калорийности за счет углеводов и частично жиров, ограничение жидкости и соли.",
                allowed = listOf(
                    "Нежирное мясо, птица, рыба",
                    "Морепродукты",
                    "Овощи (кроме картофеля)",
                    "Несладкие фрукты и ягоды",
                    "Кисломолочные продукты низкой жирности",
                    "Яйца (ограниченно)",
                    "Хлеб ржаной, отрубной (ограниченно)"
                ),
                forbidden = listOf(
                    "Сахар, кондитерские изделия",
                    "Хлеб из пшеничной муки",
                    "Макаронные изделия",
                    "Жирные продукты",
                    "Сладкие фрукты (виноград, бананы)",
                    "Картофель (ограничить)",
                    "Алкоголь"
                )
            )
            
            // Стол №9
            DietCard(
                number = "9",
                title = "Стол №9",
                indication = "Сахарный диабет легкой и средней степени тяжести",
                description = "Ограничение углеводов и жиров, исключение сахара, контроль калорийности.",
                allowed = listOf(
                    "Хлеб ржаной, отрубной (200-300г)",
                    "Супы на овощном бульоне",
                    "Нежирное мясо, птица, рыба",
                    "Молочные продукты низкой жирности",
                    "Яйца (1-2 в день)",
                    "Крупы (гречневая, овсяная, перловая)",
                    "Овощи (капуста, огурцы, помидоры, кабачки)",
                    "Несладкие фрукты и ягоды",
                    "Заменители сахара"
                ),
                forbidden = listOf(
                    "Сахар, мед, варенье",
                    "Кондитерские изделия",
                    "Сдобная выпечка",
                    "Жирные бульоны",
                    "Жирное мясо, рыба",
                    "Копчености, консервы",
                    "Сладкие фрукты (виноград, бананы, инжир)",
                    "Манная крупа, рис белый",
                    "Алкоголь"
                )
            )
            
            // Стол №10
            DietCard(
                number = "10",
                title = "Стол №10",
                indication = "Заболевания сердечно-сосудистой системы",
                description = "Ограничение соли и жидкости, исключение продуктов, возбуждающих нервную систему.",
                allowed = listOf(
                    "Хлеб вчерашней выпечки",
                    "Вегетарианские супы",
                    "Нежирное мясо, птица, рыба",
                    "Молочные продукты",
                    "Яйца (ограниченно)",
                    "Каши, макароны",
                    "Овощи отварные, запеченные",
                    "Фрукты, ягоды",
                    "Некрепкий чай"
                ),
                forbidden = listOf(
                    "Соленые продукты",
                    "Жирные сорта мяса, рыбы",
                    "Копчености",
                    "Консервы",
                    "Бобовые",
                    "Грибы",
                    "Шоколад",
                    "Крепкий чай, кофе",
                    "Алкоголь"
                )
            )
            
            // Стол №15
            DietCard(
                number = "15",
                title = "Стол №15",
                indication = "Общий стол (при отсутствии специальных показаний)",
                description = "Физиологически полноценная диета с исключением острых и трудноперевариваемых продуктов.",
                allowed = listOf(
                    "Разнообразные блюда и продукты",
                    "Мясо, птица, рыба",
                    "Молочные продукты",
                    "Яйца",
                    "Крупы, макароны",
                    "Овощи, фрукты",
                    "Хлеб, выпечка (умеренно)"
                ),
                forbidden = listOf(
                    "Очень острые блюда",
                    "Жирные сорта мяса, птицы",
                    "Тугоплавкие жиры",
                    "Горчица, перец (в больших количествах)"
                )
            )
            
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
                        text = "⚠️ Важно",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Лечебные столы назначаются врачом индивидуально с учетом стадии заболевания, сопутствующих патологий и общего состояния пациента. Не занимайтесь самолечением!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun DietCard(
    number: String,
    title: String,
    indication: String,
    description: String,
    allowed: List<String>,
    forbidden: List<String>,
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
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Показания: $indication",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Divider()
            
            Text(
                text = "✅ Разрешено:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                allowed.forEach { item ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            
            Divider()
            
            Text(
                text = "❌ Запрещено:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
            
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                forbidden.forEach { item ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
