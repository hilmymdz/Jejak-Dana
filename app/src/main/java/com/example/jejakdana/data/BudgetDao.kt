package com.example.jejakdana.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBudget(budget: Budget)

    @Delete
    suspend fun deleteBudget(budget: Budget)

    @Query("SELECT * FROM budgets ORDER BY id DESC")
    fun getAllBudgets(): LiveData<List<Budget>>

    @Query("DELETE FROM budgets")
    suspend fun deleteAllBudgets()

    @Query("SELECT * FROM budgets")
    fun getBudgets(): List<Budget>
}