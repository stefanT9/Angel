package com.example.angel.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.location.Location
import android.util.Log
import com.example.angel.models.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import java.lang.Thread.sleep

class GpsServices : JobService() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    lateinit var fusedLocationClient: FusedLocationProviderClient

    var jobCancelled = false

    override fun onStartJob(params: JobParameters?): Boolean {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        Log.e("[GPS Service]", "succesfull")
        updateLocation(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        jobCancelled = true
        Log.d("[GPS Service]", "Job cancceled")
        return false
    }


    fun updateLocation(params: JobParameters?) {
        var coordinates: GeoPoint? = null

        fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->
            Log.e(
                "[TEST]",
                "longitude:${location?.longitude.toString()} latitude:${location?.latitude.toString()}"
            )
            if (location != null) {
                coordinates = GeoPoint(location.latitude, location.longitude)
                var locations = mutableListOf<GeoPoint?>()
                db.collection("users")
                    .document(currentUser?.uid.toString())
                    .get().addOnSuccessListener {
                        val user = User(it)
                        user.locations.add(coordinates!!)

                        db.collection("users")
                            .document(currentUser?.uid.toString())
                            .set(user)
                            .addOnCompleteListener()
                            {
                                jobFinished(params, false)
                            }

                    }

            }
        }
    }
}
