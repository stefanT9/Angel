package com.example.angel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.angel.R
import com.example.angel.models.User
import com.example.angel.services.GpsServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val JOB_ID = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askForPermissions()
        ///stopLocationUpdate()
        startLocationUpdate()

        Toast.makeText(this, "welcome ${auth.uid}", Toast.LENGTH_LONG).show()

        getAngel_button_main.setOnClickListener()
        {
            val intent=Intent(this,QrActivity::class.java)
            startActivity(intent)
        }

        becomeAngel_button_main.setOnClickListener()
        {
            intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun askForPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 121)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 121)
        }
    }

    private fun startLocationUpdate() {
        val componentName = ComponentName(this, GpsServices::class.java)
        val jobInfo = JobInfo.Builder(JOB_ID, componentName)
            .setPersisted(true)
            .setPeriodic(15 * 60 * 1000)    ///Android does not support this lower than 15 min
            .build()

        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(jobInfo)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.e("[Main]", "Task Scheduling Succeded")
        } else {
            Log.e("[Main]", "Task Scheduling failed")
        }

    }

    private fun stopLocationUpdate() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(JOB_ID)
        Log.e("[Main]", "Task Canceled")
    }
}

