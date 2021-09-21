package com.fourthline.assignment.presentation.view

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.fourthline.assignment.R
import com.fourthline.assignment.databinding.SelfieFragmentBinding
import com.fourthline.assignment.domain.model.CameraProviderModel
import com.fourthline.assignment.presentation.viewmodel.SelfieViewModel
import com.fourthline.assignment.presentation.viewstate.SelfieViewState
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.selfie_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService

@AndroidEntryPoint
class SelfieFragment : BaseFragment<SelfieFragmentBinding, SelfieViewModel>() {

    companion object {
        fun newInstance() = SelfieFragment()
    }

    private var selfieButtonClicked = false

    override val layoutId: Int = R.layout.selfie_fragment

    override val viewModelClass: Class<SelfieViewModel> = SelfieViewModel::class.java

    override fun onViewBound() {
        binding.selfieFragment = this
    }

    override fun observeLiveData() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getCameraProvider(viewFinder)
    }

    private fun viewStateObserver() = Observer<SelfieViewState> { viewState ->
        when (viewState) {
            is SelfieViewState.CameraProviderResult -> {
                viewState.cameraProviderModel?.let { startCamera(it) }
            }
            is SelfieViewState.CaptureSelfieResult -> {
                var uri = viewState.selfieUri
            }
            SelfieViewState.TimerFinished -> { Toast.makeText(context, "finito", Toast.LENGTH_LONG).show()}
            is SelfieViewState.Error -> {}
            is SelfieViewState.Loading -> {}
        }
    }

    private fun startCamera(cameraProviderModel: CameraProviderModel) {
        try {
            cameraProviderModel.run {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture)
            }
            viewModel.startCountdownTimer()
        } catch (e: Exception) {
            Timber.e("CameraX use case binding failed: ${e.localizedMessage}")
        }
    }

    // Called on selfie button click
    fun onCaptureSelfieButtonClicked(v: View) {
        if (!selfieButtonClicked) {
            selfieButtonClicked = true
            viewModel.captureSelfie()
        }
    }
}