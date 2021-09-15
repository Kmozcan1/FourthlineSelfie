package com.fourthline.assignment.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 15-Sep-21.
 */
sealed class MainViewState {
    class Error(val e: Throwable) : MainViewState()
    object Loading : MainViewState()
}
