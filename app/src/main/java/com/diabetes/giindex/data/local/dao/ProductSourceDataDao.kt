package com.diabetes.giindex.data.local.dao

import androidx.room.*
import com.diabetes.giindex.data.local.entity.ProductSourceData
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductSourceDataDao {
    
    @Query("""
        SELECT psd.*, ds.name as sourceName 
        FROM product_source_data psd
        INNER JOIN data_sources ds ON psd.sourceId = ds.id
        WHERE psd.productId = :productId
        ORDER BY ds.priority DESC, ds.name ASC
    """)
    fun getSourceDataForProduct(productId: Long): Flow<List<ProductSourceDataWithName>>
    
    @Query("SELECT * FROM product_source_data WHERE productId = :productId AND sourceId = :sourceId")
    suspend fun getSourceData(productId: Long, sourceId: Long): ProductSourceData?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSourceData(data: ProductSourceData)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSourceDataList(dataList: List<ProductSourceData>)
    
    @Delete
    suspend fun deleteSourceData(data: ProductSourceData)
    
    @Query("DELETE FROM product_source_data WHERE sourceId = :sourceId")
    suspend fun deleteAllDataFromSource(sourceId: Long)
    
    @Query("DELETE FROM product_source_data WHERE productId = :productId")
    suspend fun deleteAllDataForProduct(productId: Long)
}

data class ProductSourceDataWithName(
    val productId: Long,
    val sourceId: Long,
    val gi: Int,
    val gl: Float?,
    val carbsPer100g: Float?,
    val portionSize: Int?,
    val portionUnit: String?,
    val notes: String?,
    val addedAt: Long,
    val sourceName: String
)
