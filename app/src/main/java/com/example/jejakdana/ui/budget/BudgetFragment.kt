package com.example.jejakdana.ui.budget

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jejakdana.R
import com.example.jejakdana.databinding.FragmentBudgetBinding
import com.example.jejakdana.ui.MainViewModel

class BudgetFragment : Fragment() {

    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var budgetAdapter: BudgetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.fabAddBudget.setOnClickListener {
            findNavController().navigate(R.id.action_budgetFragment_to_addBudgetFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshBudgetsWithSpending()
    }

    private fun setupRecyclerView() {
        // 1. Saat membuat adapter, teruskan lambda untuk menangani delete
        budgetAdapter = BudgetAdapter(emptyList()) { budgetData ->
            // Tampilkan dialog konfirmasi sebelum menghapus
            showDeleteConfirmationDialog(budgetData.budget)
        }
        binding.rvBudgets.apply {
            adapter = budgetAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewModel.budgetsWithSpending.observe(viewLifecycleOwner) { budgets ->
            if (budgets.isEmpty()) {
                binding.rvBudgets.visibility = View.GONE
                binding.tvNoBudgets.visibility = View.VISIBLE
            } else {
                binding.rvBudgets.visibility = View.VISIBLE
                binding.tvNoBudgets.visibility = View.GONE
                budgetAdapter.updateData(budgets)
            }
        }
    }

    // 2. Fungsi baru untuk menampilkan dialog konfirmasi
    private fun showDeleteConfirmationDialog(budget: com.example.jejakdana.data.Budget) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Budget")
            .setMessage("Are you sure you want to delete the budget '${budget.name}'?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteBudget(budget)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
