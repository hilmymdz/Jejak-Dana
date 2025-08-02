package com.example.jejakdana.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jejakdana.R
import com.example.jejakdana.databinding.FragmentHomeBinding
import com.example.jejakdana.ui.MainViewModel
import java.text.NumberFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel() // This function now does everything

        binding.fabAddTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addMoneyRecordFragment)
        }
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter(emptyList())
        binding.rvTransactions.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    // --- THIS IS THE CORRECTED FUNCTION ---
    private fun observeViewModel() {
        // Observe the list of transactions for the RecyclerView
        viewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            transactionAdapter.updateData(transactions ?: emptyList())
        }

        // Observe the total income
        viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
            val totalIncome = income ?: 0.0
            binding.tvTotalIncome.text = formatCurrency(totalIncome)
            updateTotalBalance()
        }

        // Observe the total expense
        viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
            val totalExpense = expense ?: 0.0
            binding.tvTotalExpense.text = formatCurrency(totalExpense)
            updateTotalBalance()
        }
    }

    // --- THIS IS A NEW HELPER FUNCTION ---
    private fun updateTotalBalance() {
        val income = viewModel.totalIncome.value ?: 0.0
        val expense = viewModel.totalExpense.value ?: 0.0
        val balance = income - expense
        binding.tvTotalBalance.text = formatCurrency(balance)
    }

    // --- THIS IS A NEW FORMATTING FUNCTION ---
    private fun formatCurrency(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        format.maximumFractionDigits = 0 // To remove decimals like ",00"
        format.currency = Currency.getInstance("IDR")
        return format.format(amount).replace("IDR", "IDR ").trim()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}