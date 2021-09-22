package com.fourthline.assignment.presentation.viewstate

import android.net.Uri
import com.fourthline.assignment.domain.model.CameraProviderModel

/**
 * Created by Kadir Mert Özcan on 20-Sep-21.
 */
sealed class SelfieErrorViewState {
    class Error(val e: Throwable) : SelfieErrorViewState()
    object Loading : SelfieErrorViewState()
}
