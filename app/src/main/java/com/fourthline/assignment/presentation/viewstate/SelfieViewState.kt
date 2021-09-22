package com.fourthline.assignment.presentation.viewstate

import android.net.Uri
import com.fourthline.assignment.domain.model.CameraProviderModel

/**
 * Created by Kadir Mert Özcan on 20-Sep-21.
 */
sealed class SelfieViewState {
    class Error(val e: Throwable) : SelfieViewState()
    class Loading(val loadingState: SelfieViewState) : SelfieViewState()
    class CameraProviderResult(val cameraProviderModel: CameraProviderModel? = null) : SelfieViewState()
    class CaptureSelfieResult(val selfieUri: Uri? = null): SelfieViewState()
    object TimerFinished : SelfieViewState()
}
