package com.diabetes.giindex.data.local.dao

import androidx.room.*
import com.diabetes.giindex.data.local.entity.ProductSource
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductSourceDao {
    
    @Query("SELECT * FROM product_sources WHERE productId = :productId")
    fun getSourcesForProduct(productId: Long): Flow<List<ProductSource>>
    
    @Query("SELECT * FROM product_sources WHERE sourceId = :sourceId")
    fun getProductsFromSource(sourceId: Long): Flow<List<ProductSource>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductSource(productSource: ProductSource)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductSources(productSources: List<ProductSource>)
    
    @Query("DELETE FROM product_sources WHERE sourceId = :sourceId")
    suspend fun deleteProductsFromSource(sourceId: Long)
    
    @Query("DELETE FROM product_sources WHERE productId = :productId")
    suspend fun deleteSourcesForProduct(productId: Long)
}
