package com.jonkoit.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDatabase.getDatabase(this)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Only add the LoginView if it's the first time the activity is created
            val loginView = LoginActivity(this, null)
            findViewById<FrameLayout>(R.id.fragment_login).addView(loginView)
        }
    }
}

    // Add functions to handle navigation between fragments as needed

