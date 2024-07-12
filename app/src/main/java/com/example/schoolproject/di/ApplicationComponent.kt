package com.example.schoolproject.di

import android.app.Application
import com.example.schoolproject.presentation.login.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ViewModelModule::class,
        DataModule::class,
        ContextModule::class
    ]
)

interface ApplicationComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}