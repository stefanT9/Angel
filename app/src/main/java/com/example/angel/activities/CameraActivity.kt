package com.example.angel.activities

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.camerakit.CameraKitView
import com.example.angel.R
import com.example.angel.services.QrServices
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileOutputStream


class CameraActivity : AppCompatActivity() {

    lateinit var cameraKitView: CameraKitView
    var qrServices= QrServices()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        cameraKitView = camera_cameraView_camera
        scanQr_button_camera.setOnClickListener()
        {
            Log.d("[Camera]", "scan button pressed")

        }
    }

    override fun onStart() {
        super.onStart()
        cameraKitView.onStart()
    }

    override fun onResume() {
        super.onResume()
        cameraKitView.onResume()
    }

    override fun onPause() {
        cameraKitView.onPause()
        super.onPause()
    }

    override fun onStop() {
        cameraKitView.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



}
