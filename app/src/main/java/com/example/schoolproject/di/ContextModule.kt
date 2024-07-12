package com.example.schoolproject.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
interface ContextModule {
    companion object {
        @[ApplicationScope Provides]
        fun provideContext(
            application: Application
        ): Context {
            return application.applicationContext
        }
    }
}