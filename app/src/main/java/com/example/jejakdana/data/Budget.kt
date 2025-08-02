package com.example.jejakdana.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val amount: Double,
    val period: String, // "Week", "Month", "Year"
    val category: String,
    val description: String?
)
