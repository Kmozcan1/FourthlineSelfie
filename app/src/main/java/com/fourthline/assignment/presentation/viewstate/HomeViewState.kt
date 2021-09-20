package com.fourthline.assignment.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 15-Sep-21.
 */
sealed class HomeViewState {
    class Error(val e: Throwable) : HomeViewState()
    object Loading : HomeViewState()
}
