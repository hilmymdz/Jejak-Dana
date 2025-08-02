package com.example.jejakdana.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.jejakdana.databinding.FragmentSettingsBinding
import com.example.jejakdana.ui.MainViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDeleteTrans.setOnClickListener {
            showConfirmationDialog("Delete All Transactions?") {
                viewModel.deleteAllTransactions()
                Toast.makeText(context, "All transactions deleted", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteBdgts.setOnClickListener {
            showConfirmationDialog("Delete All Budgets?") {
                viewModel.deleteAllBudgets()
                Toast.makeText(context, "All budgets deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showConfirmationDialog(message: String, onConfirm: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("Are you sure?")
            .setMessage(message)
            .setPositiveButton("Delete") { dialog, _ ->
                onConfirm()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
