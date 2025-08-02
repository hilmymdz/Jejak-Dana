package com.example.jejakdana.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.jejakdana.data.AppDatabase
import com.example.jejakdana.data.Budget
import com.example.jejakdana.data.BudgetWithSpending
import com.example.jejakdana.data.Transaction
import com.example.jejakdana.repository.JejakDanaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JejakDanaRepository

    // Transaction LiveData
    val allTransactions: LiveData<List<Transaction>>
    val totalIncome: LiveData<Double>
    val totalExpense: LiveData<Double>

    // Budget LiveData yang baru
    private val _budgetsWithSpending = MutableLiveData<List<BudgetWithSpending>>()
    val budgetsWithSpending: LiveData<List<BudgetWithSpending>> = _budgetsWithSpending

    init {
        val transactionDao = AppDatabase.getDatabase(application).transactionDao()
        val budgetDao = AppDatabase.getDatabase(application).budgetDao()
        repository = JejakDanaRepository(transactionDao, budgetDao)

        allTransactions = repository.allTransactions
        totalIncome = repository.totalIncome
        totalExpense = repository.totalExpense

        // Panggil refresh saat ViewModel dibuat
        refreshBudgetsWithSpending()
    }

    // --- Fungsi yang Diperbaiki ---
    fun refreshBudgetsWithSpending() {
        // 1. Jalankan di background thread menggunakan Dispatchers.IO
        viewModelScope.launch(Dispatchers.IO) {
            // 2. Ambil data dari repository
            val result = repository.getBudgetsWithSpending()
            // 3. Gunakan postValue untuk mengirim data kembali ke main thread
            _budgetsWithSpending.postValue(result)
        }
    }

    // --- Fungsi Lainnya ---

    fun insertTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.insertTransaction(transaction)
    }

    fun insertBudget(budget: Budget) = viewModelScope.launch {
        repository.insertBudget(budget)
        refreshBudgetsWithSpending()
    }

    fun deleteBudget(budget: Budget) = viewModelScope.launch {
        repository.deleteBudget(budget)
        refreshBudgetsWithSpending()
    }

    fun deleteAllTransactions() = viewModelScope.launch {
        repository.deleteAllTransactions()
    }

    fun deleteAllBudgets() = viewModelScope.launch {
        repository.deleteAllBudgets()
        refreshBudgetsWithSpending()
    }
}
