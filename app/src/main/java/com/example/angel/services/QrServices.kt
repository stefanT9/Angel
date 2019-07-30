package com.example.angel.services

import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class QrServices {

    fun getQRCodeDetails(bitmap: Bitmap) {

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
                    if (firebaseBarcode.rawValue != null) {

                        Log.d("[QRServices]", firebaseBarcode.rawValue)
                    }
                    SystemClock.sleep(1000)

                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                }
    }

    val options = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
            FirebaseVisionBarcode.FORMAT_QR_CODE
        )
        .build()

}