package com.diabetes.giindex.ui.navigation

sealed class Screen(val route: String) {
    object ProductList : Screen("product_list")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Long) = "product_detail/$productId"
    }
    object Favorites : Screen("favorites")
    object Sources : Screen("sources")
    object FAQ : Screen("faq")
    object AddSource : Screen("add_source")
    object Debug : Screen("debug")
    object Settings : Screen("settings")
}
