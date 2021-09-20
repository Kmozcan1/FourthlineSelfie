package com.fourthline.assignment.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Sep-21.
 */
@HiltViewModel
class SelfieViewModel @Inject constructor() : BaseViewModel<HomeViewModel>() {
    override fun onError(t: Throwable) {
        TODO("Not yet implemented")
    }
}