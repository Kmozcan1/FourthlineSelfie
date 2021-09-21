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
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService

@AndroidEntryPoint
class SelfieFragment : BaseFragment<SelfieFragmentBinding, SelfieViewModel>() {

    companion object {
        fun newInstance() = SelfieFragment()
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override val layoutId: Int = R.layout.selfie_fragment

    override val viewModelClass: Class<SelfieViewModel> = SelfieViewModel::class.java

    override fun onViewBound() {

    }

    override fun observeLiveData() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getCameraProvider(viewFinder)
    }

    private fun viewStateObserver() = Observer<SelfieViewState> { viewState ->
        when (viewState) {
            is SelfieViewState.CameraProviderResult -> startCamera(viewState.cameraProviderModel)
            is SelfieViewState.Error -> TODO()
            SelfieViewState.Loading -> TODO()
        }
    }

    private fun startCamera(cameraProviderModel: CameraProviderModel) {
        /*val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(cameraProviderListener(cameraProviderFuture),
            ContextCompat.getMainExecutor(context))*/
        try {
            cameraProviderModel.run {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture)
            }
        } catch (e: Exception) {
            Timber.e("CameraX use case binding failed: ${e.localizedMessage}")
        }
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.ENGLISH
            ).format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })
    }


    private fun cameraProviderListener(cameraProviderFuture:
                                       ListenableFuture<ProcessCameraProvider>) = Runnable()
    {
        /*// Used to bind the lifecycle of cameras to the lifecycle owner
        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

        // Preview
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

        imageCapture = ImageCapture.Builder()
            .build()

        // Select front camera as a default
        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        try {
            // Unbind use cases before rebinding
            cameraProvider.unbindAll()

            // Bind use cases to camera
            cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageCapture
            )

        } catch (exc: Exception) {
            Timber.e("CameraX use case binding failed: ${exc.localizedMessage}")
        }      */

    }
}