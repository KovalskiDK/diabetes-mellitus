package com.diabetes.giindex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.diabetes.giindex.data.repository.DataSourceRepository
import com.diabetes.giindex.data.repository.ProductRepository
import com.diabetes.giindex.ui.navigation.Screen
import com.diabetes.giindex.ui.screens.ProductDetailScreen
import com.diabetes.giindex.ui.screens.ProductListScreen
import com.diabetes.giindex.ui.theme.GIIndexTheme
import com.diabetes.giindex.ui.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = (application as GIIndexApplication).database
        
        setContent {
            GIIndexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    val productRepository = ProductRepository(
                        database.productDao(),
                        database.productSourceDao()
                    )
                    
                    val dataSourceRepository = DataSourceRepository(
                        database.dataSourceDao(),
                        database.syncLogDao()
                    )
                    
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
                        }
                        
                        composable(Screen.Sources.route) {
                        }
                        
                        composable(Screen.Settings.route) {
                        }
                    }
                }
            }
        }
    }
}
