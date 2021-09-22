package com.fourthline.assignment.presentation.view

import android.view.View
import androidx.lifecycle.Observer
import com.fourthline.assignment.R
import com.fourthline.assignment.databinding.SelfieFragmentBinding
import com.fourthline.assignment.domain.model.CameraProviderModel
import com.fourthline.assignment.presentation.viewmodel.SelfieViewModel
import com.fourthline.assignment.presentation.viewstate.SelfieViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.selfie_fragment.*
import timber.log.Timber

@AndroidEntryPoint
class SelfieFragment : BaseFragment<SelfieFragmentBinding, SelfieViewModel>() {

    companion object {
        fun newInstance() = SelfieFragment()
    }

    private var selfieButtonClicked = false

    override val layoutId: Int = R.layout.selfie_fragment

    override val viewModelClass: Class<SelfieViewModel> = SelfieViewModel::class.java

    override val isActionBarVisible = false

    override fun onViewBound() {
        binding.selfieFragment = this

    }

    override fun observeLiveData() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getCameraProvider(viewFinder)
    }

    private fun viewStateObserver() = Observer<SelfieViewState> { viewState ->
        when (viewState) {
            // Bind CameraProvider to the fragment's lifeCycle
            is SelfieViewState.CameraProviderResult -> {
                viewState.cameraProviderModel?.let {
                    startCamera(it)
                    setProgressBarVisibility(false)
                }
            }
            // Navigate to SelfieResultFragment
            is SelfieViewState.CaptureSelfieResult -> {
                viewState.selfieUri?.let {
                    FragmentNavigation().navigateFromSelfieToSelfieResultsFragment(it)
                }
            }
            // Navigate to SelfieErrorFragment
            SelfieViewState.TimerFinished -> {
                FragmentNavigation().navigateFromSelfieToSelfieErrorFragment()
            }
            is SelfieViewState.Error -> {}
            is SelfieViewState.Loading -> {
                if (viewState.loadingState is SelfieViewState.CameraProviderResult ||
                        viewState.loadingState is SelfieViewState.CaptureSelfieResult) {
                    setProgressBarVisibility(true)
                }
            }
        }
    }

    // Called after CameraProvider is retrieved from the domain layer
    // to bind it to the Fragment's lifecycle
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

    private fun setProgressBarVisibility(isVisible: Boolean) {
        if (isVisible) {
            captureSelfieButton.visibility = View.GONE
            captureSelfieButton.isEnabled = false
            selfieProgressBar.visibility = View.VISIBLE
        } else {
            captureSelfieButton.visibility = View.VISIBLE
            captureSelfieButton.isEnabled = true
            selfieProgressBar.visibility = View.GONE
        }
    }

    // Called when selfie capture button is clicked
    fun onCaptureSelfieButtonClicked(v: View) {
        if (!selfieButtonClicked) {
            selfieButtonClicked = true
            viewModel.captureSelfie()
        }
    }

    // Called when the close button on the top right is clicked
    fun onCloseSelfieButtonClicked(v: View) {
        FragmentNavigation().navigateToBack()
    }
}