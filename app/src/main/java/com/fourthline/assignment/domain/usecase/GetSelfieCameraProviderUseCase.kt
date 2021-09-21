package com.fourthline.assignment.domain.usecase

import androidx.camera.view.PreviewView
import com.fourthline.assignment.application.di.MainDispatcher
import com.fourthline.assignment.domain.helper.CameraProviderHelper
import com.fourthline.assignment.domain.model.CameraProviderModel
import com.fourthline.assignment.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Use case class that returns CameraProviderModel
 */
class GetSelfieCameraProviderUseCase @Inject constructor(
    private val cameraProviderHelper: CameraProviderHelper,
    @MainDispatcher dispatcher: CoroutineDispatcher
): UseCase<PreviewView, CameraProviderModel>(dispatcher) {
    override suspend fun execute(parameters: PreviewView): CameraProviderModel {
        return cameraProviderHelper.getFrontCameraProvider(parameters.surfaceProvider)
    }
}