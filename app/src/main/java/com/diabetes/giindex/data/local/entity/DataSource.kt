package com.diabetes.giindex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_sources")
data class DataSource(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val name: String,
    val url: String,
    val type: SourceType,
    val version: String,
    val lastUpdated: Long = System.currentTimeMillis(),
    val recordsCount: Int = 0,
    val isActive: Boolean = true,
    val priority: Int = 0,
    
    val description: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class SourceType {
    JSON,
    CSV,
    XML,
    API,
    MANUAL
}
