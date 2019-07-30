package com.example.angel.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.angel.R
import kotlinx.android.synthetic.main.activity_qr.*

class QrActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        back_button_qr.setOnClickListener()
        {
            finish()
        }
    }


}
