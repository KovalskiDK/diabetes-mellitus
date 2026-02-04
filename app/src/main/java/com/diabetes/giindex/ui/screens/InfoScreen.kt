package com.diabetes.giindex.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InfoScreen(
    onGeneralInfoClick: () -> Unit,
    onEducationClick: () -> Unit,
    onFiberClick: () -> Unit,
    onAlcoholClick: () -> Unit,
    onTrafficLightClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
            Text(
                text = "Образовательные материалы",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            // Общая информация
            InfoCard(
                icon = Icons.Filled.Info,
                title = "Общая информация",
                description = "Что такое ГИ и ГН, как они влияют на организм",
                onClick = onGeneralInfoClick
            )
            
            // Правила питания Светофор
            InfoCard(
                icon = Icons.Filled.Traffic,
                title = "Правила питания «Светофор»",
                description = "Зеленая, желтая и красная зоны продуктов по ГИ",
                onClick = onTrafficLightClick
            )
            
            // Об углеводах
            InfoCard(
                icon = Icons.Filled.Restaurant,
                title = "Об углеводах",
                description = "Типы углеводов и их усвоение организмом",
                onClick = onEducationClick
            )
            
            // Клетчатка
            InfoCard(
                icon = Icons.Filled.Eco,
                title = "Клетчатка",
                description = "Продукты с высоким содержанием клетчатки",
                onClick = onFiberClick
            )
            
            // Алкоголь
            InfoCard(
                icon = Icons.Filled.Warning,
                title = "Алкоголь и диабет",
                description = "ГИ и ГН алкогольных напитков, рекомендации",
                onClick = onAlcoholClick
            )
        }
    }

@Composable
fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
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
            }
            
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Открыть",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
