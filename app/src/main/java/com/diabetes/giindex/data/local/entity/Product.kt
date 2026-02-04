package com.diabetes.giindex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "products",
    indices = [
        Index(value = ["nameOriginal"]),
        Index(value = ["nameRu"]),
        Index(value = ["category"]),
        Index(value = ["gi"])
    ]
)
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val nameOriginal: String,
    val nameRu: String? = null,
    val descriptionOriginal: String? = null,
    val descriptionRu: String? = null,
    
    val gi: Int,
    val gl: Float? = null,
    val carbsPer100g: Float? = null,
    val portionSize: Int? = null,
    val portionUnit: String? = null,
    
    val category: String,
    val language: String = "en",
    
    val translationSource: TranslationSource = TranslationSource.NONE,
    val translationVerified: Boolean = false,
    
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class TranslationSource {
    NONE,
    MANUAL,
    DICTIONARY,
    ML_KIT,
    API
}
