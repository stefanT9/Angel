package com.example.angel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.angel.R
import com.example.angel.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "welcome ${auth.uid}", Toast.LENGTH_LONG).show()

        getAngel_button_main.setOnClickListener()
        {

        }

        becomeAngel_button_main.setOnClickListener()
        {
            intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}
