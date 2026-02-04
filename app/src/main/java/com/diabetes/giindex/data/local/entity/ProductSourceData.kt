package com.diabetes.giindex.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "product_source_data",
    primaryKeys = ["productId", "sourceId"],
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DataSource::class,
            parentColumns = ["id"],
            childColumns = ["sourceId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["productId"]),
        Index(value = ["sourceId"])
    ]
)
data class ProductSourceData(
    val productId: Long,
    val sourceId: Long,
    val gi: Int,
    val gl: Float? = null,
    val carbsPer100g: Float? = null,
    val portionSize: Int? = null,
    val portionUnit: String? = null,
    val notes: String? = null,
    val addedAt: Long = System.currentTimeMillis()
)
