package com.diabetes.giindex.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

object CategoryIcons {
    fun getIconForCategory(category: String): ImageVector {
        return when (category.lowercase()) {
            "фрукты" -> Icons.Filled.Apple
            "овощи" -> Icons.Filled.Grass
            "хлеб", "выпечка" -> Icons.Filled.Bakery
            "крупы", "паста" -> Icons.Filled.RiceBowl
            "молочные продукты", "молочка" -> Icons.Filled.Icecream
            "мясо и рыба", "мясо", "рыба" -> Icons.Filled.Restaurant
            "сладости", "десерты" -> Icons.Filled.Cake
            "напитки" -> Icons.Filled.LocalCafe
            "бобовые" -> Icons.Filled.Egg
            "орехи", "семена" -> Icons.Filled.Nature
            "снеки", "закуски" -> Icons.Filled.Fastfood
            "готовые блюда" -> Icons.Filled.DinnerDining
            "завтраки" -> Icons.Filled.BreakfastDining
            "соусы" -> Icons.Filled.WaterDrop
            "яйца" -> Icons.Filled.Egg
            "соевые продукты" -> Icons.Filled.Spa
            else -> Icons.Filled.FoodBank
        }
    }
}
