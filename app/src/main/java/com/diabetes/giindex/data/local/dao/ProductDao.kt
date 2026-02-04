package com.diabetes.giindex.data.local.dao

import androidx.room.*
import com.diabetes.giindex.data.local.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    
    @Query("SELECT * FROM products ORDER BY nameRu ASC")
    fun getAllProducts(): Flow<List<Product>>
    
    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Long): Product?
    
    @Query("SELECT * FROM products WHERE isFavorite = 1 ORDER BY nameRu ASC")
    fun getFavoriteProducts(): Flow<List<Product>>
    
    @Query("""
        SELECT * FROM products 
        WHERE nameRu LIKE '%' || :query || '%' 
        OR nameOriginal LIKE '%' || :query || '%'
        ORDER BY nameRu ASC
    """)
    fun searchProducts(query: String): Flow<List<Product>>
    
    @Query("SELECT * FROM products WHERE category = :category ORDER BY nameRu ASC")
    fun getProductsByCategory(category: String): Flow<List<Product>>
    
    @Query("SELECT * FROM products WHERE gi BETWEEN :minGi AND :maxGi ORDER BY gi ASC")
    fun getProductsByGiRange(minGi: Int, maxGi: Int): Flow<List<Product>>
    
    @Query("SELECT DISTINCT category FROM products ORDER BY category ASC")
    fun getAllCategories(): Flow<List<String>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)
    
    @Update
    suspend fun updateProduct(product: Product)
    
    @Delete
    suspend fun deleteProduct(product: Product)
    
    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()
    
    @Query("UPDATE products SET isFavorite = :isFavorite WHERE id = :productId")
    suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean)
    
    @Query("UPDATE products SET nameRu = :nameRu, translationSource = :source, translationVerified = :verified WHERE id = :productId")
    suspend fun updateTranslation(productId: Long, nameRu: String, source: String, verified: Boolean)
    
    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int
}
