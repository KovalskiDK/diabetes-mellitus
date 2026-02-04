package com.diabetes.giindex.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

object CategoryIcons {
    fun getIconForCategory(category: String): ImageVector {
        return when (category) {
            "Бобовые" -> Icons.Filled.Spa
            "Выпечка" -> Icons.Filled.Cake
            "Готовые блюда" -> Icons.Filled.DinnerDining
            "Завтраки" -> Icons.Filled.BreakfastDining
            "Крупы" -> Icons.Filled.RiceBowl
            "Молочные продукты" -> Icons.Filled.Icecream
            "Мясо и рыба" -> Icons.Filled.Restaurant
            "Напитки" -> Icons.Filled.LocalCafe
            "Овощи" -> Icons.Filled.Eco
            "Орехи" -> Icons.Filled.Nature
            "Сладости" -> Icons.Filled.Cake
            "Снеки" -> Icons.Filled.Fastfood
            "Соусы" -> Icons.Filled.WaterDrop
            "Фрукты" -> Icons.Filled.LocalFlorist
            "Хлеб" -> Icons.Filled.Grain
            else -> Icons.Filled.Category  // Дефолтная иконка для остальных
        }
    }
}
