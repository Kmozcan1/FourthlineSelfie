package com.fourthline.assignment.presentation.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fourthline.assignment.R
import com.fourthline.assignment.databinding.SelfieFragmentBinding
import com.fourthline.assignment.presentation.viewmodel.SelfieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelfieFragment : BaseFragment<SelfieFragmentBinding, SelfieViewModel>() {

    companion object {
        fun newInstance() = SelfieFragment()
    }

    override val layoutId: Int = R.layout.selfie_fragment

    override val viewModelClass: Class<SelfieViewModel> = SelfieViewModel::class.java

    override fun onViewBound() {

    }

    override fun observeLiveData() {

    }


}