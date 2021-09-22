package com.fourthline.assignment.presentation.view

import android.view.View
import com.fourthline.assignment.R
import com.fourthline.assignment.databinding.SelfieErrorFragmentBinding
import com.fourthline.assignment.presentation.viewmodel.SelfieResultsViewModel

class SelfieErrorFragment : BaseFragment<SelfieErrorFragmentBinding, SelfieResultsViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int = R.layout.selfie_error_fragment

    override val viewModelClass: Class<SelfieResultsViewModel> = SelfieResultsViewModel::class.java

    override val isActionBarVisible = true

    override fun onViewBound() {
        binding.selfieErrorFragment = this
    }

    fun onCancelSelfieButtonClick(v: View) {
        FragmentNavigation().navigateFromSelfieErrorToHomeFragment()
    }

    fun onErrorRetrySelfieButtonClick(v: View) {
        FragmentNavigation().navigateFromSelfieErrorToSelfieFragment()
    }

}