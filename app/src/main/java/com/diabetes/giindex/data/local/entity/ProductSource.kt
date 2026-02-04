package com.diabetes.giindex.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "product_sources",
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
data class ProductSource(
    val productId: Long,
    val sourceId: Long,
    val sourceVersion: String,
    val importedAt: Long = System.currentTimeMillis()
)
