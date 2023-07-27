package com.jonkoit.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class NoteEditorActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private var username: String = ""
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)

        username = intent.getStringExtra("username") ?: ""
        val userEmail = intent.getStringExtra("EMAIL")?: "noEmail"

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextContent = findViewById(R.id.editTextContent)

        val noteId = intent.getLongExtra("noteId", -1)

        if (noteId != -1L) {
            // Editing existing note
            val noteDao = AppDatabase.getDatabase(applicationContext).noteDao()
            val note = runBlocking {
                withContext(Dispatchers.IO) {
                    noteDao.getNoteById(noteId)
                }
            }
            if (note != null) {
                editTextTitle.setText(note.title)
                editTextContent.setText(note.content)
            }
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationView.setup(bottomNavigationView = bottomNavigationView, this, email = userEmail)

        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            val title = editTextTitle.text.toString().trim()
            val content = editTextContent.text.toString().trim()


            if (title.isNotEmpty() && content.isNotEmpty()) {
                val noteDao = AppDatabase.getDatabase(applicationContext).noteDao()

                val userDao = AppDatabase.getDatabase(applicationContext).userDao()
                lifecycleScope.launch {
                    val user = withContext(Dispatchers.IO) {
                        userDao.getUserByEmail(userEmail)
                    }

                    if (user != null) {
                        val newNote = Note(
                            title = title,
                            content = content,
                            username = user.username,
                            email = user.email
                        )

                        // Insert the new Note into the database
                        withContext(Dispatchers.IO) {
                            noteDao.insert(newNote)
                        }
                        // Return to the main notes activity after saving
                        val intent = Intent(this@NoteEditorActivity, NotesActivity::class.java)
                        startActivity(intent)
                        finish()

//                if (noteId != -1L) {
                        // Editing existing note
//                    val note = Note(title = title, content = content, username = user.username, email = userEmail)
//                    runBlocking {
//                        withContext(Dispatchers.IO) {
//                            noteDao.update(note)
//                        }
//                    }

                    } else {
                        // Creating a new note
                        Toast.makeText(
                            this@NoteEditorActivity,
                            "User not found",
                            Toast.LENGTH_SHORT
                        ).show()
//                    val note = Note(title = title, content = content, username = user.username, email = userEmail)
//                    runBlocking {
//                        withContext(Dispatchers.IO) {
//                            noteDao.insert(note)
//                        }
//                    }

                    }
                }
                    // Return to the main notes activity after saving
//                val intent = Intent(this, NotesActivity::class.java)
//                startActivity(intent)
//                finish()
                }
                else {
                    Toast.makeText(this, "Please enter title and content", Toast.LENGTH_SHORT).show()
                }
            }

            val btnCancel = findViewById<Button>(R.id.btnCancel)
            btnCancel.setOnClickListener {
                finish() // Go back to the main notes activity without saving
            }
        }
    }
