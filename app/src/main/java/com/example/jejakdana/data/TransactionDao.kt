package com.example.jejakdana.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.Date

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Income'")
    fun getTotalIncome(): LiveData<Double>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Expense'")
    fun getTotalExpense(): LiveData<Double>

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Expense' AND category = :category AND date BETWEEN :startDate AND :endDate")
    suspend fun getSpendingForCategory(category: String, startDate: Date, endDate: Date): Double?
}