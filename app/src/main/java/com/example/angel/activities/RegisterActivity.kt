package com.example.angel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.angel.R
import com.example.angel.models.User
import com.example.angel.services.UserServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {


    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val userServices = UserServices(this)

    lateinit var email: String
    lateinit var name: String
    lateinit var surename: String
    lateinit var password: String
    lateinit var cnp: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = email_editText_register.text.toString()
        name = name_editText_register.text.toString()
        surename = surename_editText_register.text.toString()
        password = password_editText_register.text.toString()
        cnp = cnp_editText_register.text.toString()


        register_button_register.setOnClickListener()
        {
            updateCredentials()
            if (validCredentials()) {
                val user = HashMap<String, Any>()
                user["id"] = ""
                user["email"] = email
                user["name"] = name
                user["surename"] = surename
                user["cnp"] = cnp
                user["angelsId"] = mutableListOf<String>()
                user["guardedId"] = mutableListOf<String>()
                user["locations"] = mutableListOf<GeoPoint>()


                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener()
                {
                    if (it.isSuccessful) {
                        Log.d("[RegisterActivity]", "account created successfully")
                        Toast.makeText(this, "account created successfully", Toast.LENGTH_SHORT).show()

                        user["id"] = it.result!!.user.uid
                        Log.d("[test]", user.toString())

                        userServices.addToDb(User(user))
                        auth.signInWithEmailAndPassword(email, password)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.d("[RegisterActivity]", "account creation failed")
                        Toast.makeText(this, "unable to create account", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun updateCredentials() {
        email = email_editText_register.text.toString()
        name = name_editText_register.text.toString()
        surename = surename_editText_register.text.toString()
        password = password_editText_register.text.toString()
        cnp = cnp_editText_register.text.toString()
    }

    fun validCredentials(): Boolean {
        if (!validCnp()) {
            return false
        }
        if (!validEmail()) {
            return false
        }
        if (!validPassword()) {
            return false
        }
        if (surename_editText_register.text!!.isEmpty()) {
            return false
        }
        if (name_editText_register.text!!.isEmpty()) {
            return false
        }
        return true
    }

    private fun validCnp(): Boolean {
        return true
    }

    private fun validEmail(): Boolean {
        return true
    }

    private fun validPassword(): Boolean {
        return true
    }
}
