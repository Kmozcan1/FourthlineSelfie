package com.fourthline.assignment.domain.repository

import java.io.File

interface SelfieRepository {
    fun getSelfiePhotoFile(): File
}