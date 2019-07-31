package com.example.angel.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.camerakit.CameraKitView
import com.example.angel.R
import com.example.angel.services.CameraServices
import com.example.angel.services.QrServices
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.*
import kotlinx.android.synthetic.main.activity_camera.*


class CameraActivity : AppCompatActivity() {

    lateinit var cameraKitView: CameraKitView
    lateinit var fotoAparat: Fotoapparat
    var qrServices = QrServices(this)
    var cameraServices = CameraServices(this)

    val cameraConfiguration = CameraConfiguration(
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
        sensorSensitivity = lowestSensorSensitivity(), // (optional) we want to have the lowest sensor sensitivity (ISO)
        frameProcessor = { frame -> }            // (optional) receives each frame from preview stream
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        fotoAparat = Fotoapparat(
            context = this,
            view = camera_cameraView_camera,                   // view which will draw the camera preview
            scaleType = ScaleType.CenterCrop,    // (optional) we want the preview to fill the view
            cameraConfiguration = cameraConfiguration, // (optional) define an advanced configuration
            lensPosition = back()           // (optional) we want back camera

        )
        fotoAparat.start()

        scanQr_button_camera.setOnClickListener()
        {
            val photoResult = fotoAparat.takePicture().toBitmap().whenAvailable {
                if (it != null) {
                    Toast.makeText(this, "scanning", Toast.LENGTH_SHORT).show()
                    qrServices.getQRCodeDetails(it.bitmap)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        fotoAparat.stop()
    }

}
