package com.diabetes.giindex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.diabetes.giindex.data.preferences.SettingsPreferences
import com.diabetes.giindex.data.repository.DataSourceRepository
import com.diabetes.giindex.data.repository.ProductRepository
import com.diabetes.giindex.ui.navigation.Screen
import com.diabetes.giindex.ui.screens.*
import com.diabetes.giindex.ui.theme.GIIndexTheme
import com.diabetes.giindex.ui.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = (application as GIIndexApplication).database
        
        setContent {
            GIIndexTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                val productRepository = ProductRepository(
                    database.productDao(),
                    database.productSourceDao()
                )
                
                val dataSourceRepository = DataSourceRepository(
                    database.dataSourceDao(),
                    database.syncLogDao()
                )
                
                val settingsPreferences = SettingsPreferences(this@MainActivity)
                
                val showBottomBar = currentDestination?.route in listOf(
                    Screen.ProductList.route,
                    Screen.Favorites.route,
                    Screen.FAQ.route,
                    Screen.Settings.route
                )
                
                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Home, contentDescription = "Продукты") },
                                    label = { Text("Продукты") },
                                    selected = currentDestination?.hierarchy?.any { it.route == Screen.ProductList.route } == true,
                                    onClick = {
                                        navController.navigate(Screen.ProductList.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Избранное") },
                                    label = { Text("Избранное") },
                                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Favorites.route } == true,
                                    onClick = {
                                        navController.navigate(Screen.Favorites.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Info, contentDescription = "Информация") },
                                    label = { Text("Инфо") },
                                    selected = currentDestination?.hierarchy?.any { it.route == Screen.FAQ.route } == true,
                                    onClick = {
                                        navController.navigate(Screen.FAQ.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Настройки") },
                                    label = { Text("Настройки") },
                                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Settings.route } == true,
                                    onClick = {
                                        navController.navigate(Screen.Settings.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.ProductList.route
                        ) {
                            composable(Screen.ProductList.route) {
                                val viewModel: ProductViewModel = viewModel(
                                    factory = ProductViewModelFactory(productRepository)
                                )
                                ProductListScreen(
                                    viewModel = viewModel,
                                    onProductClick = { productId ->
                                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                                    },
                                    onSettingsClick = {
                                        navController.navigate(Screen.Settings.route)
                                    }
                                )
                            }
                            
                            composable(
                                route = Screen.ProductDetail.route,
                                arguments = listOf(
                                    navArgument("productId") { type = NavType.LongType }
                                )
                            ) { backStackEntry ->
                                val productId = backStackEntry.arguments?.getLong("productId") ?: 0L
                                val viewModel: ProductDetailViewModel = viewModel(
                                    factory = ProductDetailViewModelFactory(productRepository, productId)
                                )
                                ProductDetailScreen(
                                    viewModel = viewModel,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            
                            composable(Screen.Favorites.route) {
                                val viewModel: ProductViewModel = viewModel(
                                    factory = ProductViewModelFactory(productRepository)
                                )
                                FavoritesScreen(
                                    viewModel = viewModel,
                                    onProductClick = { productId ->
                                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                                    },
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            
                            composable(Screen.Sources.route) {
                                val viewModel: DataSourceViewModel = viewModel(
                                    factory = DataSourceViewModelFactory(dataSourceRepository, productRepository)
                                )
                                SourcesScreen(
                                    viewModel = viewModel,
                                    onBackClick = { navController.popBackStack() },
                                    onAddSourceClick = {
                                        navController.navigate(Screen.AddSource.route)
                                    }
                                )
                            }
                            
                            composable(Screen.AddSource.route) {
                                val viewModel: DataSourceViewModel = viewModel(
                                    factory = DataSourceViewModelFactory(dataSourceRepository, productRepository)
                                )
                                AddSourceScreen(
                                    onBackClick = { navController.popBackStack() },
                                    onSaveClick = { source ->
                                        viewModel.addSource(source)
                                        navController.popBackStack()
                                    }
                                )
                            }
                            
                            composable(Screen.Settings.route) {
                                val settingsViewModel: SettingsViewModel = viewModel(
                                    factory = SettingsViewModelFactory(settingsPreferences)
                                )
                                SettingsScreen(
                                    viewModel = settingsViewModel,
                                    onBackClick = { navController.popBackStack() },
                                    onSourcesClick = {
                                        navController.navigate(Screen.Sources.route)
                                    }
                                )
                            }
                            
                            composable(Screen.FAQ.route) {
                                FAQScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
