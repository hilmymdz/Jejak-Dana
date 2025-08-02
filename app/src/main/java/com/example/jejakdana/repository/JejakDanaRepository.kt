package com.example.jejakdana.repository

import androidx.lifecycle.LiveData
import com.example.jejakdana.data.Budget
import com.example.jejakdana.data.BudgetDao
import com.example.jejakdana.data.Transaction
import com.example.jejakdana.data.TransactionDao
import java.util.Calendar
import java.util.Date
import com.example.jejakdana.data.BudgetWithSpending

class JejakDanaRepository (private val transactionDao : TransactionDao, private val budgetDao: BudgetDao){
    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()
    val totalIncome: LiveData<Double> = transactionDao.getTotalIncome()
    val totalExpense: LiveData<Double> = transactionDao.getTotalExpense()

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    val allBudgets: LiveData<List<Budget>> = budgetDao.getAllBudgets()

    suspend fun insertBudget(budget: Budget) {
        budgetDao.insertBudget(budget)
    }

    suspend fun deleteBudget(budget: Budget) {
        budgetDao.deleteBudget(budget)
    }

    suspend fun deleteAllTransactions() {
        transactionDao.deleteAllTransactions()
    }

    suspend fun deleteAllBudgets() {
        budgetDao.deleteAllBudgets()
    }
    suspend fun getBudgetsWithSpending(): List<BudgetWithSpending> {
        val budgets = budgetDao.getBudgets()
        val result = mutableListOf<BudgetWithSpending>()
        val calendar = Calendar.getInstance()

        for (budget in budgets) {
            // Tentukan rentang tanggal berdasarkan periode budget
            val (startDate, endDate) = when (budget.period) {
                "Weekly" -> {
                    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                    val start = calendar.time
                    calendar.add(Calendar.WEEK_OF_YEAR, 1)
                    calendar.add(Calendar.DAY_OF_YEAR, -1)
                    val end = calendar.time
                    start to end
                }
                "Monthly" -> {
                    calendar.set(Calendar.DAY_OF_MONTH, 1)
                    val start = calendar.time
                    calendar.add(Calendar.MONTH, 1)
                    calendar.add(Calendar.DAY_OF_MONTH, -1)
                    val end = calendar.time
                    start to end
                }
                "Yearly" -> {
                    calendar.set(Calendar.DAY_OF_YEAR, 1)
                    val start = calendar.time
                    calendar.add(Calendar.YEAR, 1)
                    calendar.add(Calendar.DAY_OF_YEAR, -1)
                    val end = calendar.time
                    start to end
                }
                else -> Date(0) to Date() // Untuk "One Time" atau lainnya
            }

            val spending = transactionDao.getSpendingForCategory(budget.category, startDate, endDate) ?: 0.0
            result.add(BudgetWithSpending(budget, spending))
        }
        return result
    }
}