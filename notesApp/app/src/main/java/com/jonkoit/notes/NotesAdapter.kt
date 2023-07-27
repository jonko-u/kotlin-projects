package com.jonkoit.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// NotesAdapter.kt
class NotesAdapter : ListAdapter<Note, NotesAdapter.NoteViewHolder>(NoteDiffCallback()) {
    private var notes: List<Note> = emptyList()

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val contentTextView: TextView = itemView.findViewById(R.id.textViewContent)
        private val userTextView: TextView = itemView.findViewById(R.id.textViewUser)
        private val emailTextView: TextView = itemView.findViewById(R.id.textViewEmail)

        fun bind(note: Note) {
            titleTextView.text = "Title: ${note.title}"
            contentTextView.text = "Content: ${note.content}"
            userTextView.text = "Username: ${note.username}"
            emailTextView.text = "Email: ${note.email}"
        }
    }

    // Add a DiffUtil.ItemCallback to handle comparing items in the list
    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}

