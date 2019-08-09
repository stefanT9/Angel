package com.example.angel.services

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class QrServices {

    private val userServices = UserServices()

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
                for (barcode in it) {
                    if (barcode.displayValue != null) {
                        if (userServices.isValidId(barcode.displayValue!!)) {
                            results.add(barcode.displayValue!!)
                            Log.d("[QRServices]", barcode.displayValue!!)
                        }
                    }
                }
            }
        while (!ok.isComplete)
            continue
        return results
    }


}