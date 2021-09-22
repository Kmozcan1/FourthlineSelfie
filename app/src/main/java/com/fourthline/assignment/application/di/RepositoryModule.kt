package com.fourthline.assignment.application.di

import com.fourthline.assignment.data.helper.IoHelper
import com.fourthline.assignment.data.repository.SelfieRepositoryImpl
import com.fourthline.assignment.domain.repository.SelfieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideSelfieRepository(ioHelper: IoHelper) :
            SelfieRepository = SelfieRepositoryImpl(ioHelper)
}