package com.fourthline.assignment.domain.helper

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import com.fourthline.assignment.R
import com.fourthline.assignment.presentation.view.SelfieFragment
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Helper class for IO related operations.
 * I considered making these functions static (companion) but the calling class may not have
 * the application context
 * */
class IoHelper @Inject constructor(@ApplicationContext val context: Context) {

    // Create time-stamped output file to hold the image
    fun getSelfiePhotoFile(): File {
        return File(
            getSelfieOutputDirectory(),
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.ENGLISH
            ).format(System.currentTimeMillis()) + ".jpg")
    }

    // Returns external media if it is available, or app's file directory otherwise
    private fun getSelfieOutputDirectory(): File {
        val appContext = context.applicationContext
        // externalMediaDirs is deprecated
        val mediaDir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            MediaStore.getExternalVolumeNames(context).firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() } }
        } else {
            context.externalMediaDirs.firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() } }
        }

        return if (mediaDir != null && mediaDir.exists())
            mediaDir else appContext.filesDir
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}