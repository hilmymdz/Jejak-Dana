package com.example.jejakdana.ui.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.jejakdana.data.Transaction
import com.example.jejakdana.databinding.FragmentAddMoneyRecordBinding
import com.example.jejakdana.ui.MainViewModel
import java.util.Calendar
import java.util.Date

class AddMoneyRecordFragment : Fragment() {

    private var _binding: FragmentAddMoneyRecordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var selectedDate: Date = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMoneyRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategorySpinner()
        setupDatePicker()

        binding.btnSave.setOnClickListener {
            saveTransaction()
        }
    }

    private fun setupCategorySpinner() {
        // You can create more extensive category lists
        val categories = listOf("Food", "Transport", "Shopping", "Salary", "Bills", "Entertainment", "Other")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
    }

    private fun setupDatePicker() {
        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                    selectedDate = selectedCalendar.time
                    binding.etDate.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
                },
                year, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun saveTransaction() {
        val name = binding.etName.text.toString().trim()
        val amountText = binding.etAmount.text.toString().trim()
        val type = if (binding.rbIncome.isChecked) "Income" else "Expense"
        val category = binding.spinnerCategory.selectedItem.toString()
        val description = binding.etDescription.text.toString().trim()

        if (name.isEmpty() || amountText.isEmpty()) {
            Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val transaction = Transaction(
            name = name,
            type = type,
            date = selectedDate,
            amount = amount,
            category = category,
            description = description.ifEmpty { null }
        )

        viewModel.insertTransaction(transaction)
        Toast.makeText(context, "Transaction Saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack() // Go back to the previous screen

        viewModel.refreshBudgetsWithSpending()
        Toast.makeText(context, "Transaction Saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}