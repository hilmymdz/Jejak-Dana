package com.example.jejakdana.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.jejakdana.R
import com.example.jejakdana.data.Transaction
import com.example.jejakdana.databinding.ItemTransactionBinding
import java.text.NumberFormat
import java.util.*

class TransactionAdapter(private var transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount() = transactions.size

    fun updateData(newTransactions: List<Transaction>) {
        this.transactions = newTransactions
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.tvTransactionName.text = transaction.name
            binding.tvTransactionCategory.text = transaction.category

            // Menentukan dan mengatur ikon berdasarkan kategori
            val iconResId = getIconForCategory(transaction.category)
            binding.ivCategoryIcon.setImageResource(iconResId)

            val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            val formattedAmount = format.format(transaction.amount)

            if (transaction.type == "Income") {
                binding.tvTransactionAmount.text = "+ $formattedAmount"
                binding.tvTransactionAmount.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.green_income)
                )
            } else {
                binding.tvTransactionAmount.text = "- $formattedAmount"
                binding.tvTransactionAmount.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.red_expense)
                )
            }
        }
    }
    private fun getIconForCategory(category: String): Int {
        return when (category) {
            "Food" -> R.drawable.ic_food
            "Transport" -> R.drawable.ic_transport
            "Shopping" -> R.drawable.ic_shopping_bag
            "Salary" -> R.drawable.ic_salary
            "Bills" -> R.drawable.ic_bills
            "Entertainment" -> R.drawable.ic_entertainment
            else -> R.drawable.ic_other // Ikon default
        }
    }
}
