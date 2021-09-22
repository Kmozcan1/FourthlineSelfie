package com.fourthline.assignment.domain.helper

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.fourthline.assignment.data.helper.IoHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Helper class for capturing photo with CameraX's ImageCapture UseCase
 */
class CaptureSelfieHelper @Inject constructor (
    private val ioHelper: IoHelper,
    @ApplicationContext val context: Context
) {

    suspend fun captureSelfie(imageCapture: ImageCapture, photoFile: File): Uri {
        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Returns a callbackFlow which will emit the photo URI after callback
        return suspendCoroutine { cont ->
            // Set up image capture listener, which is triggered after photo has been taken
            val onImageSavedCallback = object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    Timber.e("Photo capture failed: ${e.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    Timber.d("Photo capture succeeded: $savedUri")
                    cont.resume(savedUri)
                }
            }

            imageCapture.takePicture(outputOptions,
                ContextCompat.getMainExecutor(context), onImageSavedCallback)

        }
    }
}