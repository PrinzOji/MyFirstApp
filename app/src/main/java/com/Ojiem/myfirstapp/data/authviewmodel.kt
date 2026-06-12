package com.Ojiem.myfirstapp.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.Ojiem.myfirstapp.models.User
import com.Ojiem.myfirstapp.navigation.ROUTE_DASHBOARD
import com.Ojiem.myfirstapp.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuthViewModel(var navController: NavHostController, var context: Context) {
    private var mAuth: FirebaseAuth? = null
    var isLoading by mutableStateOf(false)
        private set

    init {
        try {
            mAuth = FirebaseAuth.getInstance()
        } catch (e: Exception) {
            // Handle case where Firebase is not initialized (e.g. in Previews)
        }
    }

    fun signup(name: String, username: String, email: String, password: String, confirmPassword: String) {
        val auth = mAuth ?: return
        if (name.isBlank() || username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
            return
        } else if (password != confirmPassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
            return
        } else {
            isLoading = true
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    val userData = User(name, username, email, password, confirmPassword, userId)
                    val regRef = FirebaseDatabase.getInstance().getReference().child("Users/$userId")
                    
                    regRef.setValue(userData).addOnCompleteListener { dbTask ->
                        isLoading = false
                        if (dbTask.isSuccessful) {
                            Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_DASHBOARD) {
                                popUpTo(0) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Failed to save user data: ${dbTask.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    isLoading = false
                    Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun login(emailOrUsername: String, password: String) {
        if (mAuth == null) return
        if (emailOrUsername.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
            return
        }
        
        isLoading = true
        if (emailOrUsername.contains("@")) {
            // Login using email
            performFirebaseLogin(emailOrUsername, password)
        } else {
            // Login using username - find email first
            val userRef = FirebaseDatabase.getInstance().getReference().child("Users")
            userRef.orderByChild("username").equalTo(emailOrUsername)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            var emailFound = ""
                            for (userSnap in snapshot.children) {
                                val user = userSnap.getValue(User::class.java)
                                emailFound = user?.email ?: ""
                            }
                            if (emailFound.isNotEmpty()) {
                                performFirebaseLogin(emailFound, password)
                            } else {
                                isLoading = false
                                Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            isLoading = false
                            Toast.makeText(context, "Username not found", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        isLoading = false
                        Toast.makeText(context, "Database Error: ${error.message}", Toast.LENGTH_LONG).show()
                    }
                })
        }
    }

    private fun performFirebaseLogin(email: String, pass: String) {
        mAuth?.signInWithEmailAndPassword(email, pass)?.addOnCompleteListener { task ->
            isLoading = false
            if (task.isSuccessful) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_DASHBOARD) {
                    popUpTo(0) { inclusive = true }
                }
            } else {
                Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun logout() {
        mAuth?.signOut()
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(0) { inclusive = true }
        }
    }

    fun isLogged(): Boolean {
        return mAuth?.currentUser != null
    }
}
