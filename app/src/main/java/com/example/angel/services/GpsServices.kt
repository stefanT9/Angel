package com.example.angel.services

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.*
import java.lang.Thread.sleep

class GpsServices : JobService() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    override fun onStartJob(p0: JobParameters?): Boolean {

        updateLocation(60000)
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }

    fun updateLocation(timer: Long) {
        var coordinates: GeoPoint? = null

        GlobalScope.launch {
            while (true) {
                sleep(timer)
                fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->
                    Log.d(
                        "[TEST]",
                        "longitude:${location?.longitude.toString()} latitude:${location?.latitude.toString()}"
                    )
                    if (location == null) {

                    } else {
                        coordinates = GeoPoint(location.latitude, location.longitude)
                        db.collection("users").document(currentUser?.uid.toString())
                            .update("lastPosition", coordinates)
                    }
                }
            }
        }
    }
}
