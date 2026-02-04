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
    object Info : Screen("info")
    object GeneralInfo : Screen("general_info")
    object Education : Screen("education")
    object Fiber : Screen("fiber")
    object Alcohol : Screen("alcohol")
    object Settings : Screen("settings")
}
