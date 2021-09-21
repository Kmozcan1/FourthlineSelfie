package com.fourthline.assignment.presentation.viewmodel

import androidx.camera.view.PreviewView
import com.fourthline.assignment.domain.usecase.GetSelfieCameraProviderUseCase
import com.fourthline.assignment.presentation.viewstate.SelfieViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Sep-21.
 */
@HiltViewModel
class SelfieViewModel @Inject constructor(
    private val getSelfieCameraProviderUseCase: GetSelfieCameraProviderUseCase
) : BaseViewModel<SelfieViewState>() {
    override fun onError(t: Throwable) {
        TODO("Not yet implemented")
    }

    fun getCameraProvider(previewView: PreviewView) {
        val cameraProviderModel = getSelfieCameraProviderUseCase(previewView)
        setViewState(SelfieViewState.CameraProviderResult(cameraProviderModel))
    }
}