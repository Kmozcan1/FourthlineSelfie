package com.fourthline.assignment.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 20-Sep-21.
 */
sealed class SelfieViewState {
    class Error(val e: Throwable) : SelfieViewState()
    object Loading : SelfieViewState()
}
