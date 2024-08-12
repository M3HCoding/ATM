package com.atm.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atm.android.data.Transaction
import com.atm.android.databinding.ItemTransactionBinding

class TransactionAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int = transactions.size

    class TransactionViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.txtAmount.text = transaction.amount
            binding.txtCount100.text = transaction.count_100.toString()
            binding.txtCount200.text = transaction.count_200.toString()
            binding.txtCount500.text = transaction.count_500.toString()
            binding.txtCount2000.text = transaction.count_2000.toString()
        }
    }
}
