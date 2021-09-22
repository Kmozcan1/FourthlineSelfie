package com.fourthline.assignment.presentation.viewmodel

import com.fourthline.assignment.presentation.viewstate.SelfieErrorViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 22-Sep-21.
 */
@HiltViewModel
class SelfieErrorViewModel @Inject constructor() : BaseViewModel<SelfieErrorViewState>() {
    override fun onError(t: Throwable) {
    }
}