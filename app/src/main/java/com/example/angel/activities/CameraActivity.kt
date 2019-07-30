package com.example.angel.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock.sleep
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.camerakit.CameraKitView
import com.example.angel.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_camera.*


class CameraActivity : AppCompatActivity() {

    lateinit var cameraKitView: CameraKitView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        cameraKitView = camera_cameraView_camera
        scanQr_button_camera.setOnClickListener()
        {
            Log.d("[Camera]", "scan button pressed")
            cameraKitView.captureImage { cameraKitView, photo ->
                val bmp = BitmapFactory.decodeByteArray(photo, 0, photo.size)
                getQRCodeDetails(bmp)
            }
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

    private fun getQRCodeDetails(bitmap: Bitmap) {

        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_QR_CODE
            )
            .build()
        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        detector.detectInImage(image)
            .addOnSuccessListener {
                for (firebaseBarcode in it) {
                    Toast.makeText(this, firebaseBarcode.rawValue, Toast.LENGTH_SHORT).show()
                    sleep(1000)
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
            }
    }

}
