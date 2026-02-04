package com.diabetes.giindex.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

object CategoryIcons {
    fun getIconForCategory(category: String): ImageVector {
        return when (category.lowercase()) {
            "фрукты" -> Icons.Filled.LocalFlorist
            "овощи" -> Icons.Filled.Eco
            "хлеб" -> Icons.Filled.Grain
            "крупы" -> Icons.Filled.RiceBowl
            "молочные продукты" -> Icons.Filled.Icecream
            "мясо и рыба" -> Icons.Filled.Restaurant
            "сладости" -> Icons.Filled.Cake
            "напитки" -> Icons.Filled.LocalCafe
            "бобовые" -> Icons.Filled.Spa
            "орехи" -> Icons.Filled.Nature
            "снеки" -> Icons.Filled.Fastfood
            "готовые блюда" -> Icons.Filled.DinnerDining
            "завтраки" -> Icons.Filled.BreakfastDining
            else -> Icons.Filled.Category  // Дефолтная иконка "Другое"
        }
    }
}
