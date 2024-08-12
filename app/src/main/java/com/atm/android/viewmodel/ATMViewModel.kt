package com.atm.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atm.android.data.Note
import com.atm.android.data.Transaction

class ATMViewModel : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    private var availableNotes = mutableListOf(
        Note(2000, 10),
        Note(500, 10),
        Note(200, 10),
        Note(100, 10)
    )

    init {
        _notes.value = availableNotes
        _transactions.value = emptyList()
    }

    fun withdrawAmount(amount: Int) {
        if (amount % 100 != 0) {
            // Amount must be a multiple of 100
            return
        }

        val notesUsed = mutableListOf<Note>()
        var remainingAmount = amount

        for (note in availableNotes) {
            val count = remainingAmount / note.denomination
            val notesToUse = minOf(count, note.count)
            if (notesToUse > 0) {
                remainingAmount -= notesToUse * note.denomination
                note.count -= notesToUse
                notesUsed.add(Note(note.denomination, notesToUse))
            }
        }

        if (remainingAmount == 0) {
            availableNotes = availableNotes.map { note ->
                notesUsed.find { it.denomination == note.denomination }?.let {
                    note.copy(count = note.count - it.count)
                } ?: note
            }.toMutableList()
            _notes.value = availableNotes

            val transaction = Transaction(
                amount = "Rs. $amount",
                count_100 = notesUsed.filter { it.denomination == 100 }.sumOf { it.count },
                count_200 = notesUsed.filter { it.denomination == 200 }.sumOf { it.count },
                count_500 = notesUsed.filter { it.denomination == 500 }.sumOf { it.count },
                count_2000 = notesUsed.filter { it.denomination == 2000 }.sumOf { it.count }
            )
            _transactions.value = listOf(transaction) + (_transactions.value ?: emptyList())
        }
    }
}
