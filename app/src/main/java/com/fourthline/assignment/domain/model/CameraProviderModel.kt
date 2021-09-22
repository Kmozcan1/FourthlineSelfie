package com.fourthline.assignment.domain.model

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider

data class CameraProviderModel(
    var cameraProvider: ProcessCameraProvider,
    var cameraSelector: CameraSelector,
    var preview: Preview,
    var imageCapture: ImageCapture
)
