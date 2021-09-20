package com.fourthline.assignment.presentation.viewmodel

import com.fourthline.assignment.presentation.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 15-Sep-21.
 */
@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainViewState>() {

    override fun onError(t: Throwable) {

    }

}