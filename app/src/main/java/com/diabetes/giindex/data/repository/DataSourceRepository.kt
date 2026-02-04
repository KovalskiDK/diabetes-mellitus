package com.diabetes.giindex.data.repository

import com.diabetes.giindex.data.local.dao.DataSourceDao
import com.diabetes.giindex.data.local.dao.SyncLogDao
import com.diabetes.giindex.data.local.entity.DataSource
import com.diabetes.giindex.data.local.entity.SourceType
import com.diabetes.giindex.data.local.entity.SyncLog
import kotlinx.coroutines.flow.Flow

class DataSourceRepository(
    private val dataSourceDao: DataSourceDao,
    private val syncLogDao: SyncLogDao
) {
    
    fun getAllSources(): Flow<List<DataSource>> = dataSourceDao.getAllSources()
    
    fun getActiveSources(): Flow<List<DataSource>> = dataSourceDao.getActiveSources()
    
    suspend fun getSourceById(id: Long): DataSource? = dataSourceDao.getSourceById(id)
    
    suspend fun insertSource(source: DataSource) {
        dataSourceDao.insertSource(source)
    }
    
    suspend fun updateSource(source: DataSource) = dataSourceDao.updateSource(source)
    
    suspend fun deleteSource(sourceId: Long) {
        val source = dataSourceDao.getSourceById(sourceId)
        source?.let {
            dataSourceDao.deleteSource(it)
        }
    }
    
    suspend fun toggleSourceActive(sourceId: Long, isActive: Boolean) {
        val source = dataSourceDao.getSourceById(sourceId)
        source?.let {
            dataSourceDao.updateSource(it.copy(isActive = isActive))
        }
    }
    
    suspend fun updateSourceVersion(sourceId: Long, version: String, timestamp: Long, count: Int) {
        dataSourceDao.updateSourceVersion(sourceId, version, timestamp, count)
    }
    
    suspend fun updateSourceUrlAndType(sourceId: Long, url: String, type: SourceType) {
        dataSourceDao.updateSourceUrlAndType(sourceId, url, type)
    }
    
    fun getRecentSyncLogs(): Flow<List<SyncLog>> = syncLogDao.getRecentLogs()
    
    fun getSyncLogsForSource(sourceId: Long): Flow<List<SyncLog>> = 
        syncLogDao.getLogsForSource(sourceId)
    
    suspend fun insertSyncLog(log: SyncLog): Long = syncLogDao.insertLog(log)
    
    suspend fun updateSyncLog(log: SyncLog) = syncLogDao.updateLog(log)
    
    suspend fun getSyncLogById(id: Long): SyncLog? = syncLogDao.getLogById(id)
}
