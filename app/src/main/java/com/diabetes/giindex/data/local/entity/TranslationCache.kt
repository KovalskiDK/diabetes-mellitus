package com.diabetes.giindex.data.local.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "translation_cache",
    primaryKeys = ["original", "targetLanguage"],
    indices = [Index(value = ["original"])]
)
data class TranslationCache(
    val original: String,
    val translated: String,
    val targetLanguage: String = "ru",
    val source: TranslationSource,
    val verified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
