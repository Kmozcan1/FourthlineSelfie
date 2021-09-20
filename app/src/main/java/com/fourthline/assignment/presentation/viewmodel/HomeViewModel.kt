package com.fourthline.assignment.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeViewModel>() {
    override fun onError(t: Throwable) {
        TODO("Not yet implemented")
    }
}