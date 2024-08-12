package com.atm.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atm.android.data.Note
import com.atm.android.databinding.ItemNoteBinding

class NoteAdapter(private val notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            val denomination = note.denomination
            val count = note.count

            binding.txtAmount.text = "Rs. $denomination"
            // Reset all TextViews
            binding.txtCount2000.text = "0"
            binding.txtCount500.text = "0"
            binding.txtCount200.text = "0"
            binding.txtCount100.text = "0"

            // Set the count based on denomination
            when (denomination) {
                2000 -> binding.txtCount2000.text = count.toString()
                500 -> binding.txtCount500.text = count.toString()
                200 -> binding.txtCount200.text = count.toString()
                100 -> binding.txtCount100.text = count.toString()
            }

        }
    }
}
