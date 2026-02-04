package com.diabetes.giindex.data.repository

import com.diabetes.giindex.data.local.dao.ProductDao
import com.diabetes.giindex.data.local.dao.ProductSourceDao
import com.diabetes.giindex.data.local.entity.Product
import com.diabetes.giindex.data.local.entity.ProductSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class ProductRepository(
    private val productDao: ProductDao,
    private val productSourceDao: ProductSourceDao
) {
    
    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()
    
    fun getFavoriteProducts(): Flow<List<Product>> = productDao.getFavoriteProducts()
    
    fun searchProducts(query: String): Flow<List<Product>> = productDao.searchProducts(query)
    
    fun getProductsByCategory(category: String): Flow<List<Product>> = 
        productDao.getProductsByCategory(category)
    
    fun getProductsByGiRange(minGi: Int, maxGi: Int): Flow<List<Product>> = 
        productDao.getProductsByGiRange(minGi, maxGi)
    
    fun getAllCategories(): Flow<List<String>> = productDao.getAllCategories()
    
    suspend fun getProductById(id: Long): Product? = productDao.getProductById(id)
    
    suspend fun insertProduct(product: Product): Long = productDao.insertProduct(product)
    
    suspend fun insertProducts(products: List<Product>) = productDao.insertProducts(products)
    
    suspend fun updateProduct(product: Product) = productDao.updateProduct(product)
    
    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)
    
    suspend fun toggleFavorite(productId: Long, isFavorite: Boolean) = 
        productDao.updateFavoriteStatus(productId, isFavorite)
    
    suspend fun updateTranslation(
        productId: Long, 
        nameRu: String, 
        source: String, 
        verified: Boolean
    ) = productDao.updateTranslation(productId, nameRu, source, verified)
    
    suspend fun getProductCount(): Int = productDao.getProductCount()
    
    fun getSourcesForProduct(productId: Long): Flow<List<ProductSource>> = 
        productSourceDao.getSourcesForProduct(productId)
    
    suspend fun linkProductToSource(productSource: ProductSource) = 
        productSourceDao.insertProductSource(productSource)
    
    suspend fun deleteProductsBySource(sourceId: Long): Int = 
        productDao.deleteProductsBySource(sourceId)
    
    suspend fun getProductCountBySource(sourceId: Long): Int = 
        productDao.getProductCountBySource(sourceId)
}
