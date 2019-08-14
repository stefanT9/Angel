package com.example.angel.activities


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.angel.BuildConfig
import com.example.angel.R
import com.example.angel.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.microsoft.maps.*
import kotlinx.android.synthetic.main.activity_gps.*

class GpsActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var mapView: MapView
    private lateinit var locations: Geolocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)
        val userId = intent.getStringExtra("userId")

        try {
            db.collection("users").document(userId!!).get().addOnSuccessListener {
                val user = User(it)
                val lastLocations = user.locations
                val geolocation = Geolocation(lastLocations.last().latitude, lastLocations.last().longitude)

                locations = geolocation

                mapView = MapView(this, MapRenderMode.VECTOR)
                mapView.setCredentialsKey(BuildConfig.CREDENTIALS_KEY)
                mapView.setScene(
                    MapScene.createFromLocationAndZoomLevel
                        (geolocation, 14.0),
                    MapAnimationKind.BOW
                )
                mapLayout_Gps.addView(mapView)
                val pinLayer = MapElementLayer()
                mapView.layers.add(pinLayer as MapLayer?)

                var len = 1
                for ((idx, location) in user.locations.withIndex()) {
                    if (idx == user.locations.size - 1 || location != user.locations[idx + 1]) {
                        val locationPin = MapIcon()
                        locationPin.location = Geolocation(location.latitude, location.longitude)
                        locationPin.title = "$idx : $len"
                        pinLayer.elements.add(locationPin)

                        Log.e("[Gps]", "pin placed with name $idx :$len")
                        len = 1
                    } else {
                        len++
                        Log.e("[Gps]", "$idx $len")
                    }
                }
                onResume()
            }
        } catch (e: NullPointerException) {
            Log.e("[GPS]", "userId is null")
        }

    }

    override fun onResume() {
        super.onResume()
        if (::mapView.isInitialized)
            mapView.resume()
    }

    override fun onPause() {
        super.onPause()
        if (::mapView.isInitialized)
            mapView.suspend()
    }

    override fun onStart() {
        super.onStart()
        if (::mapView.isInitialized) {
            if (::locations.isInitialized) {
                mapView.setScene(
                    MapScene.createFromLocationAndZoomLevel
                        (locations, 10.0),
                    MapAnimationKind.BOW
                )

            }
        }
    }
}
