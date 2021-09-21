package com.fourthline.assignment.domain.helper

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.fourthline.assignment.domain.model.CameraProviderModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Helper class is used for initializing the parameters required by CameraProviderModel
 */
class CameraProviderHelper @Inject constructor(@ApplicationContext val context: Context) {

    // Returns a CameraProviderModel with the CameraProvider, front facing CameraSelector
    // as well as preview and imageCapture CameraX UseCases
    fun getFrontCameraProvider(surfaceProvider: Preview.SurfaceProvider): CameraProviderModel {

        // can't bind the provider here because it needs to be aware of the fragment lifecycle
        val cameraProvider = ProcessCameraProvider.getInstance(context).get()

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        // ImageCapture use case
        val imageCapture = ImageCapture.Builder()
            .build()

        // Preview use case
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(surfaceProvider)
            }

        return CameraProviderModel(cameraProvider, cameraSelector, preview, imageCapture)
    }


}