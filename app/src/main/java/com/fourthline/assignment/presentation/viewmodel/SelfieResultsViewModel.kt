package com.fourthline.assignment.presentation.viewmodel

import com.fourthline.assignment.presentation.viewstate.SelfieResultsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 22-Sep-21.
 */
@HiltViewModel
class SelfieResultsViewModel @Inject constructor() : BaseViewModel<SelfieResultsViewState>() {
    override fun onError(t: Throwable) {
    }
}