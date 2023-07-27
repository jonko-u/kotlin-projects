package com.jonkoit.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etUsername: EditText = findViewById(R.id.etUsername)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val etRepeatPassword: EditText = findViewById(R.id.etRepeatPassword)
        val btnSignUp: Button = findViewById(R.id.btnSignUp)



        btnSignUp.setOnClickListener {
            // Handle sign-up button click here
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val repeatPassword = etRepeatPassword.text.toString()

            // Validate input fields (e.g., check if passwords match)
            if (password == repeatPassword) {
                // Perform sign-up process with username, email, and password
                // Use Firebase createUserWithEmailAndPassword or your preferred authentication method
                Log.d("NotesActivity", "Received email: $email")
                signUpUser(username, email, password)

            } else {
                // Passwords don't match, show an error message
                showToast("Passwords do not match")
            }
        }
    }

    private fun signUpUser(username: String, email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-up success, handle the newly signed-up user
                    val user = FirebaseAuth.getInstance().currentUser
                    showToast("Sign-up successful! Welcome, ${user?.displayName}")

                    // You can also update the user's display name if you want to use it later
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                // Display name update successful
                                // You can now use user.displayName to get the username
                                // Display name update successful
                                // Save the user's name and email to the Room database
                                val userDao = AppDatabase.getDatabase(applicationContext).userDao()
                                val userEntity = User(username = username, email = email, hashedPassword = password)
                                lifecycleScope.launch {
                                    withContext(Dispatchers.IO) {
                                        userDao.insertUser(userEntity)
                                    }
                                }

                                // Proceed to NotesActivity
                                Log.d("NotesActivity", "Received email: $email")
                                val intent = Intent(this@SignUpActivity, NotesActivity::class.java)
                                intent.putExtra("email", email)
                                startActivity(intent)
                                finish()
                            } else {
                                // Display name update failed
                                // Handle the error if necessary
                            }
                        }

                    Log.d("SignUpActivity", "Received Email: $email")
                    // Proceed to the notes activity (activity_notes.xml)
                    saveUserToDatabase(username = username, email = email, hashedPassword = password)
                } else {
                    // Sign-up failed, handle the error (e.g., show error message)
                    // You can check task.exception for specific error details
                    val errorMessage = task.exception?.message ?: "Sign-up failed"
                    showToast(errorMessage)
                }
            }
    }
    private fun saveUserToDatabase(username: String, email: String, hashedPassword: String) {
        val userDao = AppDatabase.getDatabase(applicationContext).userDao()
        val user = User(username = username, email = email, hashedPassword = hashedPassword)

        // Use a coroutine to perform the database operation on a background thread
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                userDao.insertUser(user)
            }
            showToast("User successfully registered!")
            // You can navigate to the next screen or perform other actions here
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}