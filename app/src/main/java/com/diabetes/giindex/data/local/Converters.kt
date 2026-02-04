package com.diabetes.giindex.data.local

import androidx.room.TypeConverter
import com.diabetes.giindex.data.local.entity.SourceType
import com.diabetes.giindex.data.local.entity.SyncStatus
import com.diabetes.giindex.data.local.entity.TranslationSource

class Converters {
    
    @TypeConverter
    fun fromTranslationSource(value: TranslationSource): String {
        return value.name
    }
    
    @TypeConverter
    fun toTranslationSource(value: String): TranslationSource {
        return try {
            TranslationSource.valueOf(value)
        } catch (e: IllegalArgumentException) {
            TranslationSource.NONE
        }
    }
    
    @TypeConverter
    fun fromSourceType(value: SourceType): String {
        return value.name
    }
    
    @TypeConverter
    fun toSourceType(value: String): SourceType {
        return try {
            SourceType.valueOf(value)
        } catch (e: IllegalArgumentException) {
            SourceType.MANUAL
        }
    }
    
    @TypeConverter
    fun fromSyncStatus(value: SyncStatus): String {
        return value.name
    }
    
    @TypeConverter
    fun toSyncStatus(value: String): SyncStatus {
        return try {
            SyncStatus.valueOf(value)
        } catch (e: IllegalArgumentException) {
            SyncStatus.PENDING
        }
    }
}
