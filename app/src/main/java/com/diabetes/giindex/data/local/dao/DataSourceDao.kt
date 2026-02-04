package com.diabetes.giindex.data.local.dao

import androidx.room.*
import com.diabetes.giindex.data.local.entity.DataSource
import kotlinx.coroutines.flow.Flow

@Dao
interface DataSourceDao {
    
    @Query("SELECT * FROM data_sources ORDER BY priority DESC, name ASC")
    fun getAllSources(): Flow<List<DataSource>>
    
    @Query("SELECT * FROM data_sources WHERE isActive = 1 ORDER BY priority DESC")
    fun getActiveSources(): Flow<List<DataSource>>
    
    @Query("SELECT * FROM data_sources WHERE id = :id")
    suspend fun getSourceById(id: Long): DataSource?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(source: DataSource): Long
    
    @Update
    suspend fun updateSource(source: DataSource)
    
    @Delete
    suspend fun deleteSource(source: DataSource)
    
    @Query("UPDATE data_sources SET isActive = :isActive WHERE id = :sourceId")
    suspend fun updateActiveStatus(sourceId: Long, isActive: Boolean)
    
    @Query("UPDATE data_sources SET version = :version, lastUpdated = :timestamp, recordsCount = :count WHERE id = :sourceId")
    suspend fun updateSourceVersion(sourceId: Long, version: String, timestamp: Long, count: Int)
}
