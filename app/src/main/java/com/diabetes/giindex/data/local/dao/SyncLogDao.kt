package com.diabetes.giindex.data.local.dao

import androidx.room.*
import com.diabetes.giindex.data.local.entity.SyncLog
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncLogDao {
    
    @Query("SELECT * FROM sync_logs ORDER BY startedAt DESC LIMIT 50")
    fun getRecentLogs(): Flow<List<SyncLog>>
    
    @Query("SELECT * FROM sync_logs WHERE sourceId = :sourceId ORDER BY startedAt DESC LIMIT 10")
    fun getLogsForSource(sourceId: Long): Flow<List<SyncLog>>
    
    @Insert
    suspend fun insertLog(log: SyncLog): Long
    
    @Update
    suspend fun updateLog(log: SyncLog)
    
    @Query("DELETE FROM sync_logs WHERE startedAt < :timestamp")
    suspend fun deleteOldLogs(timestamp: Long)
    
    @Query("SELECT * FROM sync_logs WHERE id = :id")
    suspend fun getLogById(id: Long): SyncLog?
}
