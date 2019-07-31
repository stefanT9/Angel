package com.example.angel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Script
import android.util.Log
import android.widget.Toast
import com.example.angel.R
import com.example.angel.services.EmergencyServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var auth = FirebaseAuth.getInstance()
    var emergencyService = EmergencyServices(this)
    var db = FirebaseFirestore.getInstance()

    lateinit var email: String
    lateinit var password: String

    ///when loginClicks reach 5 the phone enters panic mode
    var loginClicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        email = email_editText_login.text.toString()
        password = password_editText_login.text.toString()

        if (auth.currentUser != null) {
            auth.signOut()
        }

        login_button_login.setOnClickListener()
        {
            updateCredentials()
            if (validCredentials()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener()
                {
                    if (it.isSuccessful) {
                        Log.d("[Login]", "Login completed by user ${auth.uid}")
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("[Login]", "Login failed")
                    }
                }
            }
        }
        register_button_login.setOnClickListener()
        {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validCredentials(): Boolean {

        if (password.isEmpty()) {
            loginClicks++
            if (loginClicks == 4) {
                ///TODO: Make async
                emergencyService.sendAlert()
                emergencyService.callEmergency()
            }
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false
        }
        return true
    }

    fun updateCredentials() {
        email = email_editText_login.text.toString()
        password = password_editText_login.text.toString()
    }
}
