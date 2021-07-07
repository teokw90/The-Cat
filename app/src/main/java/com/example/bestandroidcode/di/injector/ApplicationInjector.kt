package com.example.bestandroidcode.di.injector

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.bestandroidcode.MainApplication
import com.example.bestandroidcode.di.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/

object ApplicationInjector {
    fun init(application: MainApplication) {
        DaggerAppComponent.builder()
            .application(application)
            .build()
            .inject(application)

        application.registerActivityLifecycleCallbacks(object: Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = handleActivity(activity)

            override fun onActivityStarted(activity: Activity) = Unit
            override fun onActivityResumed(activity: Activity) = Unit
            override fun onActivityPaused(activity: Activity) = Unit
            override fun onActivityStopped(activity: Activity) = Unit
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
            override fun onActivityDestroyed(activity: Activity) = Unit

        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasAndroidInjector) AndroidInjection.inject(activity)
    }
}