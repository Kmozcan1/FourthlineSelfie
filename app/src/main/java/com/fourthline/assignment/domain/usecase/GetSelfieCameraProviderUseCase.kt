package com.fourthline.assignment.domain.usecase

import androidx.camera.view.PreviewView
import com.fourthline.assignment.domain.helper.CameraProviderHelper
import com.fourthline.assignment.domain.model.CameraProviderModel
import com.fourthline.assignment.domain.usecase.base.UseCase
import javax.inject.Inject

/**
 * Use case class that returns CameraProviderModel
 */
class GetSelfieCameraProviderUseCase @Inject constructor(
    private val cameraProviderHelper: CameraProviderHelper
): UseCase<PreviewView, CameraProviderModel>() {
    override fun execute(parameters: PreviewView): CameraProviderModel {
        return cameraProviderHelper.getFrontCameraProvider(parameters.surfaceProvider)
    }
}