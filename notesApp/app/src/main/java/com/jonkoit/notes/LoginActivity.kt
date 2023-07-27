package com.jonkoit.notes

import android.content.Context
import android.content.Intent

import android.util.AttributeSet
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class LoginActivity(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    // ... (Other code in LoginView.kt)

    init {
        inflate(context, R.layout.activity_login, this)

        // Initialize and handle UI elements here
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val tvSignUp: TextView = findViewById(R.id.tvSignUp)

        // Set click listeners or any other interactions
        btnLogin.setOnClickListener {
            // Handle login button click here
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            loginUser(email, password)
        }

        // Set click listener for the "Sign Up" TextView
        tvSignUp.setOnClickListener {
            // Handle sign-up link click here
            // Navigate to the sign-up screen (activity_signup.xml)
            // You can use an Intent to launch the new activity
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }
    private fun loginUser(email: String, password: String) {
        // Perform the login process with email and password using Firebase
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login success, handle the logged-in user

                    val user = FirebaseAuth.getInstance().currentUser
                    showToast("Login successful User $user!") // Show a success message

                    // Proceed to NotesActivity after successful login
                    val intent = Intent(context, NotesActivity::class.java)
                    intent.putExtra("EMAIL",email)
                    context.startActivity(intent)
                     // Finish the current activity to prevent going back to the login screen

                } else {
                    // Login failed, handle the error (e.g., show error message)
                    // You can check task.exception for specific error details
                    showToast("Login failed: ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    // ... (Other code in LoginView.kt)
}