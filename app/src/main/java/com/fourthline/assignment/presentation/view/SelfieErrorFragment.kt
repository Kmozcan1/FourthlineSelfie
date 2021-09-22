package com.fourthline.assignment.presentation.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fourthline.assignment.R
import com.fourthline.assignment.databinding.HomeFragmentBinding
import com.fourthline.assignment.databinding.SelfieErrorFragmentBinding
import com.fourthline.assignment.presentation.viewmodel.HomeViewModel
import com.fourthline.assignment.presentation.viewmodel.SelfieErrorViewModel

class SelfieErrorFragment : BaseFragment<SelfieErrorFragmentBinding, SelfieErrorViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int = R.layout.selfie_error_fragment

    override val viewModelClass: Class<SelfieErrorViewModel> = SelfieErrorViewModel::class.java

    override val appBarVisible = true

    override fun onViewBound() {
        binding.selfieErrorFragment = this
    }

    override fun observeLiveData() {

    }

    fun onCancelSelfieButtonClick(v: View) {
        FragmentNavigation().navigateFromSelfieErrorToHomeFragment()
    }

    fun onErrorRetrySelfieButtonClick(v: View) {
        FragmentNavigation().navigateFromSelfieErrorToSelfieFragment()
    }

}