package com.example.jejakdana.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val type: String, // "Income" or "Expense"
    val date: Date,
    val amount: Double,
    val category: String,
    val description: String?
)