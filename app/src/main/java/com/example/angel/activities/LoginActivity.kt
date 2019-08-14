package com.example.angel.activities

import android.app.job.JobScheduler
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.angel.R
import com.example.angel.services.EmergencyServices
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var auth = FirebaseAuth.getInstance()
    private var emergencyService = EmergencyServices()
    private var JOB_ID = 123

    private lateinit var email: String
    private lateinit var password: String

    ///when loginClicks reach 5 the phone enters panic mode
    private var loginClicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stopLocationUpdate()

        setContentView(R.layout.activity_login)

        email = email_editText_login.text.toString()
        password = password_editText_login.text.toString()

        if (auth.currentUser != null) {
            //intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            //finish()
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
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun updateCredentials() {
        email = email_editText_login.text.toString()
        password = password_editText_login.text.toString()
    }

    private fun stopLocationUpdate() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(JOB_ID)
        Log.e("[Main]", "Task Canceled")
    }
}
