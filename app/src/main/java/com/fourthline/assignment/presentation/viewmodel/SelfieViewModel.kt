package com.fourthline.assignment.presentation.viewmodel

import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.lifecycle.viewModelScope
import com.fourthline.assignment.domain.model.UseCaseResult
import com.fourthline.assignment.domain.model.data
import com.fourthline.assignment.domain.usecase.CaptureSelfieUseCase
import com.fourthline.assignment.domain.usecase.GetSelfieCameraProviderUseCase
import com.fourthline.assignment.presentation.viewstate.SelfieViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit

/**
 * Created by Kadir Mert Özcan on 20-Sep-21.
 */
@HiltViewModel
class SelfieViewModel @Inject constructor(
    private val getSelfieCameraProviderUseCase: GetSelfieCameraProviderUseCase,
    private val captureSelfieUseCase: CaptureSelfieUseCase
) : BaseViewModel<SelfieViewState>() {

    companion object {
        private const val COUNTDOWN_TIMER_TOTAL_SECONDS = 10
    }

    private var imageCapture: ImageCapture? = null

    fun getCameraProvider(previewView: PreviewView) {
        viewModelScope.launch {
            when (val result = getSelfieCameraProviderUseCase(previewView)) {
                is UseCaseResult.Success -> {
                    val cameraProviderModel = result.data
                    imageCapture = cameraProviderModel.imageCapture
                    setViewState(SelfieViewState.CameraProviderResult(cameraProviderModel))
                }
                is UseCaseResult.Error -> {
                    onError(result.exception)
                }
                UseCaseResult.Loading ->
                    onLoading(SelfieViewState.CameraProviderResult(null))
            }
        }
    }

    fun captureSelfie() {
        viewModelScope.launch {
            when (val result = captureSelfieUseCase(imageCapture!!)) {
                is UseCaseResult.Success ->
                    setViewState(SelfieViewState.CaptureSelfieResult(result.data))
                is UseCaseResult.Error -> onError(result.exception)
                UseCaseResult.Loading -> onLoading(SelfieViewState.CaptureSelfieResult(null))
            }
        }
    }


    fun startCountdownTimer() {
        viewModelScope.launch(Dispatchers.Main) {
            for (second in COUNTDOWN_TIMER_TOTAL_SECONDS downTo 1) {
                delay(1000)
            }
            setViewState(SelfieViewState.TimerFinished)
        }
    }

    override fun onError(t: Throwable) {
        setViewState(SelfieViewState.Error(t))
    }

    private fun onLoading(viewState: SelfieViewState) {
        setViewState(viewState)
    }
}