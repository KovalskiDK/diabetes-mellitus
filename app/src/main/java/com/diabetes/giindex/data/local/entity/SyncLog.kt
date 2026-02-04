package com.diabetes.giindex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_logs")
data class SyncLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val sourceId: Long,
    val sourceName: String,
    val sourceVersion: String,
    
    val status: SyncStatus,
    val recordsAdded: Int = 0,
    val recordsUpdated: Int = 0,
    val recordsDeleted: Int = 0,
    val recordsTotal: Int = 0,
    
    val errorMessage: String? = null,
    val startedAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)

enum class SyncStatus {
    PENDING,
    IN_PROGRESS,
    SUCCESS,
    FAILED,
    CANCELLED
}
