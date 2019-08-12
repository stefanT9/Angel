package com.example.angel.activities


import android.os.Bundle
import android.transition.Scene
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.angel.BuildConfig
import com.example.angel.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.microsoft.maps.*
import kotlinx.android.synthetic.main.activity_gps.*
import com.example.angel.R

class GpsActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var mapView: MapView
    lateinit var locations: Geolocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)
        val userId = intent.getStringExtra("userId")

        try {
            db.collection("users").document(userId!!).get().addOnSuccessListener {
                val lastLocations = User(it).locations
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
                mapView.layers.add(pinLayer)

                val locationPin = MapIcon()
                locationPin.location = geolocation
                locationPin.title = "Last Location"
                pinLayer.elements.add(locationPin)

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
