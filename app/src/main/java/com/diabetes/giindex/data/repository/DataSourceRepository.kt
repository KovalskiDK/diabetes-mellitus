package com.diabetes.giindex.data.repository

import com.diabetes.giindex.data.local.dao.DataSourceDao
import com.diabetes.giindex.data.local.dao.SyncLogDao
import com.diabetes.giindex.data.local.entity.DataSource
import com.diabetes.giindex.data.local.entity.SyncLog
import kotlinx.coroutines.flow.Flow

class DataSourceRepository(
    private val dataSourceDao: DataSourceDao,
    private val syncLogDao: SyncLogDao
) {
    
    fun getAllSources(): Flow<List<DataSource>> = dataSourceDao.getAllSources()
    
    fun getActiveSources(): Flow<List<DataSource>> = dataSourceDao.getActiveSources()
    
    suspend fun getSourceById(id: Long): DataSource? = dataSourceDao.getSourceById(id)
    
    suspend fun insertSource(source: DataSource): Long = dataSourceDao.insertSource(source)
    
    suspend fun updateSource(source: DataSource) = dataSourceDao.updateSource(source)
    
    suspend fun deleteSource(source: DataSource) = dataSourceDao.deleteSource(source)
    
    suspend fun toggleSourceActive(sourceId: Long, isActive: Boolean) = 
        dataSourceDao.updateActiveStatus(sourceId, isActive)
    
    suspend fun updateSourceVersion(sourceId: Long, version: String, timestamp: Long, count: Int) = 
        dataSourceDao.updateSourceVersion(sourceId, version, timestamp, count)
    
    fun getRecentSyncLogs(): Flow<List<SyncLog>> = syncLogDao.getRecentLogs()
    
    fun getSyncLogsForSource(sourceId: Long): Flow<List<SyncLog>> = 
        syncLogDao.getLogsForSource(sourceId)
    
    suspend fun insertSyncLog(log: SyncLog): Long = syncLogDao.insertLog(log)
    
    suspend fun updateSyncLog(log: SyncLog) = syncLogDao.updateLog(log)
    
    suspend fun getSyncLogById(id: Long): SyncLog? = syncLogDao.getLogById(id)
}
