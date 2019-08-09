package com.example.angel.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.angel.R
import com.example.angel.services.QrServices
import com.example.angel.services.UserServices
import com.google.firebase.auth.FirebaseAuth
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.*
import kotlinx.android.synthetic.main.fragment_camera.*

class CameraFragment : Fragment() {

    private lateinit var fotoApparat: Fotoapparat
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private var userServices = UserServices()
    private var qrServices = QrServices()

    private val cameraConfiguration = CameraConfiguration(
        pictureResolution = highestResolution(), // (optional) we want to have the highest possible photo resolution
        previewResolution = highestResolution(), // (optional) we want to have the highest possible preview resolution
        previewFpsRange = highestFps(),          // (optional) we want to have the best frame rate
        focusMode = firstAvailable(              // (optional) use the first focus mode which is supported by device
            continuousFocusPicture(),
            autoFocus(),                       // if continuous focus is not available on device, auto focus will be used
            fixed()                            // if even auto focus is not available - fixed focus mode will be used
        ),
        flashMode = firstAvailable(              // (optional) similar to how it is done for focus mode, this time for flash
            autoRedEye(),
            autoFlash(),
            torch(),
            off()
        ),
        antiBandingMode = firstAvailable(       // (optional) similar to how it is done for focus mode & flash, now for anti banding
            auto(),
            hz50(),
            hz60(),
            none()
        ),
        jpegQuality = manualJpegQuality(90),     // (optional) select a jpeg quality of 90 (out of 0-100) values
        sensorSensitivity = lowestSensorSensitivity() // (optional) we want to have the lowest sensor sensitivity (ISO)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        fotoApparat = Fotoapparat(
            context = context!!,
            view = camera_cameraView_camera,                   // view which will draw the camera preview
            scaleType = ScaleType.CenterCrop,    // (optional) we want the preview to fill the view
            cameraConfiguration = cameraConfiguration, // (optional) define an advanced configuration
            lensPosition = back()           // (optional) we want back camera

        )
        fotoApparat.start()

        scanQr_button_camera.setOnClickListener()
        {
            fotoApparat.takePicture().toBitmap().whenAvailable {
                if (it != null) {
                    val codes = qrServices.getQRCodeDetails(it.bitmap)
                    if (codes.isEmpty()) {
                    } else {
                        for (code in codes) {
                            userServices.becomeAngel(currentUser!!.uid, code)
                        }
                    }
                }
            }
        }
    }

}
