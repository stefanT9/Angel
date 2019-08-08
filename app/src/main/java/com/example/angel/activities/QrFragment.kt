package com.example.angel.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.example.angel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_qr.*
import androidx.annotation.Nullable


class QrFragment : Fragment() {

    val auth=FirebaseAuth.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_qr, container, false)
    }


    private fun setQr(v: AppCompatImageView, text: String?)
    {
        if (text == null) {
            Log.e("[QR]", "user wasn't logged in")
            ///throw NullPointerException()
            ///text="mota"
        }
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            v.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {

        val img = view.findViewById(R.id.qrCode_imageView_qr) as ImageView
        setQr(qrCode_imageView_qr, auth.currentUser?.uid)
    }

}