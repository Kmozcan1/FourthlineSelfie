package com.fourthline.assignment.presentation.viewstate

import android.net.Uri
import com.fourthline.assignment.domain.model.CameraProviderModel

/**
 * Created by Kadir Mert Özcan on 20-Sep-21.
 */
sealed class SelfieResultsViewState {
    class Error(val e: Throwable) : SelfieResultsViewState()
    object Loading : SelfieResultsViewState()
}
