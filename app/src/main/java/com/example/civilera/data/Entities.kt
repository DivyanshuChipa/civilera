package com.example.civilera.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sites")
data class Site(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val location: String
)

@Entity(tableName = "materials")
data class Material(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val unit: String // e.g., kg, bags, etc.
)

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val materialId: Int,
    val fromSiteId: Int?, // null means initial purchase/stock entry
    val toSiteId: Int,
    val quantity: Double,
    val timestamp: Long = System.currentTimeMillis()
)
