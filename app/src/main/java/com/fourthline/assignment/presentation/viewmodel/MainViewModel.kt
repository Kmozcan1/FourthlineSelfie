package com.fourthline.assignment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.fourthline.assignment.presentation.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 15-Sep-21.
 */
@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainViewState>() {

    // LiveData for navigation
    val fragmentNavigationEvent: MutableLiveData<Event<NavDirections>>
        get() = _fragmentNavigationEvent
    private val _fragmentNavigationEvent = MutableLiveData<Event<NavDirections>>()
    internal fun setFragmentNavigationEvent(value: Event<NavDirections>) {
        _fragmentNavigationEvent.postValue(value)
    }

    override fun onError(t: Throwable) {

    }

}