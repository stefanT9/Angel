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
    val options = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
            FirebaseVisionBarcode.FORMAT_QR_CODE
        )
        .build()

    fun getQRCodeDetails(bitmap: Bitmap) {

        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        ///TODO: Refactor code
        ///TODO: Add a popup to confirm becoming an angel
        detector.detectInImage(image)
            .addOnSuccessListener {
                for (firebaseBarcode in it) {
                    if (firebaseBarcode.rawValue != null) {
                        if(userServices.isValidId(firebaseBarcode.rawValue.toString())) {
                            userServices.becomeAngel(auth.currentUser!!.uid,firebaseBarcode.rawValue.toString())
                            Log.d("[QRServices]", firebaseBarcode.rawValue.toString())
                        }
                    }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                }
    }


}