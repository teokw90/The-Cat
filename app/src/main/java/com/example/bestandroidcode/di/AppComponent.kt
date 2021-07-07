package com.example.bestandroidcode.di

import com.example.bestandroidcode.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityModule::class,
    ViewModelModule::class,
    InteractorModule::class,
    RepositoryModule::class,
    WebServiceModule::class,
    SharedPreferenceModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MainApplication): Builder

        fun build(): AppComponent
    }

    fun inject(application: MainApplication)
}