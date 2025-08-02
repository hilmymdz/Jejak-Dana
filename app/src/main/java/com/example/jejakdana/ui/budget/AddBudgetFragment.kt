package com.example.jejakdana.ui.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.jejakdana.data.Budget
import com.example.jejakdana.databinding.FragmentAddBudgetBinding
import com.example.jejakdana.ui.MainViewModel

class AddBudgetFragment : Fragment() {

    private var _binding: FragmentAddBudgetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()

        binding.btnSaveBudget.setOnClickListener() {
            saveBudget()
        }
    }

    private fun setupSpinners() {
        // Setup Period Spinner
        val periods = listOf("Weekly", "Monthly", "Yearly", "One Time")
        val periodAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, periods)
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPeriod.adapter = periodAdapter

        // Setup Category Spinner
        val categories = listOf("Food", "Transport", "Shopping", "Bills", "Entertainment", "Other")
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategoryBudget.adapter = categoryAdapter
    }

    private fun saveBudget() {
        val name = binding.budgetName.text.toString().trim()
        val amountText = binding.budgetAmount.text.toString().trim()
        val period = binding.spinnerPeriod.selectedItem.toString()
        val category = binding.spinnerCategoryBudget.selectedItem.toString()
        val description = binding.budgetDesc.text.toString().trim()

        if (name.isEmpty() || amountText.isEmpty()) {
            Toast.makeText(context, "Please fill in name and amount", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val budget = Budget(
            name = name,
            amount = amount,
            period = period,
            category = category,
            description = description.ifEmpty { null }
        )

        viewModel.insertBudget(budget)
        Toast.makeText(context, "Budget Saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}