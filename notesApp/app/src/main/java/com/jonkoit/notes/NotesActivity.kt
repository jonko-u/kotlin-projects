package com.jonkoit.notes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
//import com.jonkoit.notes.databinding.ActivityNotesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class NotesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter

    private var username: String = ""
    private var email: String? = null

    private lateinit var editTextFilter: EditText
    private lateinit var btnFilter: Button
    private lateinit var originalNotes: List<Note>
    private lateinit var filteredNotes: List<Note> // Store the filtered list of notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        filteredNotes = emptyList()

        username = intent.getStringExtra("username") ?: ""
        val email = intent.getStringExtra("EMAIL") ?: ""


        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        tvWelcome.text = "Welcome! $email"

        recyclerView = findViewById(R.id.recyclerViewNotes)
        recyclerView.layoutManager = LinearLayoutManager(this)


        notesAdapter = NotesAdapter()
        recyclerView.adapter = notesAdapter

        val noteDao = AppDatabase.getDatabase(applicationContext).noteDao()
        lifecycleScope.launch {
            val notes = withContext(Dispatchers.IO) {
                noteDao.getAllNotes()
            }
            originalNotes = notes
            notesAdapter.submitList(notes)
        }

        // Find the BottomNavigationView from the layout
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        Log.d("NotesActivity", "=====================Received email: $email")
        NavigationView.setup(bottomNavigationView = bottomNavigationView, this, email = email)

        // Find views
        editTextFilter = findViewById(R.id.editTextFilter)
        btnFilter = findViewById(R.id.btnFilter)

        // Set click listener for the filter button
        btnFilter.setOnClickListener {
            val filter = editTextFilter.text.toString().trim()
            filterNotes(filter)
        }
    }

    private fun filterNotes(filter: String) {
        Log.d("NotesAcitivity", " FILTERED NOTES: ${filteredNotes.toString()}")
        if (filter.isBlank()) {
            // If the filter is empty or contains only spaces, show all notes
            notesAdapter.submitList(originalNotes)
        } else {
            // Filter the original list of notes and update the filteredNotes list
            filteredNotes = originalNotes.filter { it.title.contains(filter, ignoreCase = true) }
            notesAdapter.submitList(filteredNotes)
        }
    }
}
