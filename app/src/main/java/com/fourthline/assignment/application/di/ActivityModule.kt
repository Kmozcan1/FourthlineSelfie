package com.fourthline.assignment.application.di

import android.app.Activity
import com.fourthline.assignment.presentation.view.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by Kadir Mert Özcan on 20-Sep-21.
 */
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun providesMainActivity(
        activity: Activity
    ): MainActivity {
        return activity as MainActivity
    }
}