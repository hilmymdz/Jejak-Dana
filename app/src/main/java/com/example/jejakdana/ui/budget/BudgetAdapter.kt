package com.example.jejakdana.ui.budget


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.jejakdana.R
import com.example.jejakdana.data.BudgetWithSpending
import com.example.jejakdana.databinding.ItemBudgetBinding
import java.text.NumberFormat
import java.util.*

// 1. Tambahkan parameter baru: lambda function untuk menangani delete
class BudgetAdapter(
    private var budgetsWithSpending: List<BudgetWithSpending>,
    private val onDeleteClicked: (BudgetWithSpending) -> Unit
) : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = ItemBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(budgetsWithSpending[position])
    }

    override fun getItemCount() = budgetsWithSpending.size

    fun updateData(newBudgets: List<BudgetWithSpending>) {
        this.budgetsWithSpending = newBudgets
        notifyDataSetChanged()
    }

    inner class BudgetViewHolder(private val binding: ItemBudgetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(budgetData: BudgetWithSpending) {
            // ... (kode bind yang sudah ada tetap sama) ...
            val budget = budgetData.budget
            val spending = budgetData.totalSpending

            binding.tvBudgetName.text = budget.name
            binding.tvBudgetCategory.text = budget.category
            // ... (sisa kode bind lainnya) ...
            binding.tvBudgetPeriod.text = budget.period
            binding.tvBudgetAmount.text = formatCurrency(budget.amount)

            if (budget.description.isNullOrEmpty()) {
                binding.tvBudgetDescription.visibility = View.GONE
            } else {
                binding.tvBudgetDescription.visibility = View.VISIBLE
                binding.tvBudgetDescription.text = budget.description
            }

            if (budget.amount > 0) {
                val progress = (spending / budget.amount * 100).toInt()
                binding.pbBudgetProgress.progress = progress
                binding.tvBudgetProgressPercent.text = "$progress%"
            } else {
                binding.pbBudgetProgress.progress = 0
                binding.tvBudgetProgressPercent.text = "0%"
            }


            // 2. Tambahkan click listener untuk tombol titik tiga
            binding.btnBudgetOptions.setOnClickListener { view ->
                showPopupMenu(view, budgetData)
            }
        }

        // 3. Fungsi baru untuk menampilkan popup menu
        private fun showPopupMenu(view: View, budgetData: BudgetWithSpending) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.budget_item_menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_delete_budget -> {
                        // Panggil lambda function saat item delete diklik
                        onDeleteClicked(budgetData)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    private fun formatCurrency(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")
        return format.format(amount).replace("IDR", "IDR ").trim()
    }
}

