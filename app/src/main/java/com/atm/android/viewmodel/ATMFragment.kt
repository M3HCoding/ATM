package com.atm.android.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.atm.android.adapter.NoteAdapter
import com.atm.android.adapter.TransactionAdapter
import com.atm.android.databinding.FragmentAtmBinding

class ATMFragment : Fragment() {
    private var _binding: FragmentAtmBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ATMViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var transactionAdapter: TransactionAdapter
    private val initialBalance: Int = 50000
    private var remainingBalance: Int = initialBalance


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAtmBinding.inflate(inflater, container, false)
        binding.tvTotalBalance.text = "Rs. $initialBalance"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ATMViewModel::class.java)

        noteAdapter = NoteAdapter(emptyList())
        binding.recyclerViewLastTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewLastTransactions.adapter = noteAdapter

        transactionAdapter = TransactionAdapter(emptyList())
        binding.recyclerViewYourTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewYourTransactions.adapter = transactionAdapter

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            noteAdapter = NoteAdapter(notes)
            binding.recyclerViewLastTransactions.adapter = noteAdapter
        }

        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            transactionAdapter = TransactionAdapter(transactions)
            binding.recyclerViewYourTransactions.adapter = transactionAdapter
        }

        binding.btnWithdraw.setOnClickListener {
            val amountStr = binding.edtWithdrawalAmount.text.toString()
            val amount = amountStr.toIntOrNull()

            when {
                amount == null -> {
                    // Show error if the amount is not a number
                    Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                }
                amount % 100 != 0 -> {
                    // Show error if the amount is not a multiple of 100
                    Toast.makeText(requireContext(), "Amount must be a multiple of 100", Toast.LENGTH_SHORT).show()
                }
                amount > remainingBalance -> {
                    // Show error if the amount exceeds the remaining balance
                    Toast.makeText(requireContext(), "Insufficient balance", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Proceed with withdrawal
                    viewModel.withdrawAmount(amount)
                    remainingBalance -= amount  // Deduct the amount from remaining balance
                    binding.tvRemainingBalance.text = "Rs. $remainingBalance"  // Update UI with new balance
                    binding.edtWithdrawalAmount.text.clear()  // Clear the EditText field
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
