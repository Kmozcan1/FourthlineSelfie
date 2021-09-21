package com.fourthline.assignment.presentation.viewstate

import com.fourthline.assignment.domain.model.CameraProviderModel

/**
 * Created by Kadir Mert Özcan on 20-Sep-21.
 */
sealed class SelfieViewState {
    class Error(val e: Throwable) : SelfieViewState()
    object Loading : SelfieViewState()
    class CameraProviderResult(val cameraProviderModel: CameraProviderModel) : SelfieViewState()
}
