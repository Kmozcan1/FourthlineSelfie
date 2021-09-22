package com.fourthline.assignment.presentation.view

import android.net.Uri
import android.view.View
import androidx.navigation.fragment.navArgs
import com.fourthline.assignment.R
import com.fourthline.assignment.databinding.SelfieResultsFragmentBinding
import com.fourthline.assignment.presentation.viewmodel.SelfieResultsViewModel

class SelfieResultsFragment : BaseFragment<SelfieResultsFragmentBinding, SelfieResultsViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int = R.layout.selfie_results_fragment

    override val viewModelClass: Class<SelfieResultsViewModel> = SelfieResultsViewModel::class.java

    override val isActionBarVisible = true

    private val args: SelfieResultsFragmentArgs by navArgs()

    override fun onViewBound() {
        binding.selfieResultsFragment = this
        binding.selfieResultsImageView.setImageURI(Uri.parse(args.selfieUri))
    }

    // Called when Confirm button is clicked; kills the application
    fun onConfirmSelfieButtonClick() {
        finishApplication()
    }

    // Called when Retry button is clicked; navigates to SelfieFragment
    fun onResultsRetrySelfieButtonClick() {
        FragmentNavigation().navigateFromSelfieResultToSelfieFragment()
    }


}