package com.jonkoit.notes

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.jonkoit.notes.LoginActivity
import com.jonkoit.notes.NoteEditorActivity
import com.jonkoit.notes.NotesActivity

object NavigationView {

    fun setup(bottomNavigationView: BottomNavigationView, context: Context, email: String) {
        bottomNavigationView.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_home -> {
                        // Handle Home item selection
                        // You can perform any navigation or UI updates related to "Home" here
                        return true
                    }
                    R.id.action_add_note -> {
                        // Handle Add Note item selection
                        val intent = Intent(context, NoteEditorActivity::class.java)
                        intent.putExtra("EMAIL", email)
                        context.startActivity(intent)
                        return true
                    }
                    R.id.action_back -> {
                        // Handle Back item selection
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                        // If needed, you can also finish the current activity
                        // (uncomment the following line)
                        // (context as? AppCompatActivity)?.finish()
                        return true
                    }
                    else -> return false
                }
            }
        })
    }
}
