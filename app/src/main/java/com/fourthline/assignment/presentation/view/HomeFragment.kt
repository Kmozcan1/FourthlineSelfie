package com.fourthline.assignment.presentation.view

import android.view.View
import com.fourthline.assignment.R
import com.fourthline.assignment.databinding.HomeFragmentBinding
import com.fourthline.assignment.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int = R.layout.home_fragment

    override val viewModelClass: Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewBound() {
        binding.homeFragment = this
    }

    override fun observeLiveData() {
        //viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
    }

    fun onSelfieButtonClick(v: View) {
        FragmentNavigation().navigateFromHomeToSelfieFragment()
    }

}