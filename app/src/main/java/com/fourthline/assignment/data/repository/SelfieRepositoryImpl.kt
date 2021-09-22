package com.fourthline.assignment.data.repository

import com.fourthline.assignment.data.helper.IoHelper
import com.fourthline.assignment.domain.repository.SelfieRepository
import java.io.File
import javax.inject.Inject

class SelfieRepositoryImpl @Inject constructor(
    private val ioHelper: IoHelper
) : SelfieRepository {
    override fun getSelfiePhotoFile(): File {
        return ioHelper.getSelfiePhotoFile()
    }
}