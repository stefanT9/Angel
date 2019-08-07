package com.example.angel.services

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class QrServices(val context: Context) {

    val auth=FirebaseAuth.getInstance()
    val userServices=UserServices(context)

    fun getQRCodeDetails(bitmap: Bitmap): MutableList<String> {
        val results = mutableListOf<String>()
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_QR_CODE
            )
            .build()
        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

        val ok = detector.detectInImage(image)
            .addOnSuccessListener {
                for (firebaseBarcode in it) {
                    if (firebaseBarcode.displayValue != null) {
                        if (userServices.isValidId(firebaseBarcode.displayValue!!)) {
                            results.add(firebaseBarcode.displayValue!!)
                            Toast.makeText(context, firebaseBarcode.displayValue, Toast.LENGTH_SHORT).show()
                            Log.d("[QRServices]", firebaseBarcode.displayValue!!)
                        }
                    }
                }
            }
        while (!ok.isComplete)
            continue
        return results
    }


}