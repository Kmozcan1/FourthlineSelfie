package com.fourthline.assignment.domain.usecase

import android.net.Uri
import androidx.camera.core.ImageCapture
import com.fourthline.assignment.application.di.IoDispatcher
import com.fourthline.assignment.domain.helper.CaptureSelfieHelper
import com.fourthline.assignment.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * UseCase class that takes a photo, saves it and and emits its Uri
 * */
class CaptureSelfieUseCase @Inject constructor(
    private val captureSelfieHelper: CaptureSelfieHelper,
    @IoDispatcher dispatcher: CoroutineDispatcher
): UseCase<ImageCapture, Uri>(dispatcher) {
    override suspend fun execute(parameters: ImageCapture): Uri {
        return captureSelfieHelper.captureSelfie(parameters)
    }
}