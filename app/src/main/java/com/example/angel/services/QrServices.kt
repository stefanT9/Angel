package com.example.angel.services

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class QrServices(val context: Context) {
    val options = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
            FirebaseVisionBarcode.FORMAT_QR_CODE
        )
        .build()

    fun getQRCodeDetails(bitmap: Bitmap) {

        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        detector.detectInImage(image)
            .addOnSuccessListener {
                for (firebaseBarcode in it) {
                    if (firebaseBarcode.rawValue != null) {
                        Toast.makeText(context, firebaseBarcode.rawValue, Toast.LENGTH_SHORT).show()
                        Log.d("[QRServices]", firebaseBarcode.rawValue)
                    }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                }
    }


}