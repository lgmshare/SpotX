package com.firstpoli.spotx

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.firstpoli.spotx.extensions.getCurrentProcessName
import com.firstpoli.spotx.ui.SplashActivity

class App : Application(), LifecycleEventObserver {

    var startCount: Int = 0
    var isForeground: Boolean = false

    companion object {
        lateinit var INSTANCE: App

    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        if (getCurrentProcessName() == BuildConfig.APPLICATION_ID) {
            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
            registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                    AppActivityManager.pushActivity(p0)
                }

                override fun onActivityStarted(activity: Activity) {
                    startCount++
                    if (startCount == 1) {
                        if (activity !is SplashActivity) {
                            activity.startActivity(Intent(activity, SplashActivity::class.java))
                        }
                    }
                }

                override fun onActivityResumed(p0: Activity) {
                }

                override fun onActivityPaused(p0: Activity) {
                }

                override fun onActivityStopped(p0: Activity) {
                    startCount--
                }

                override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                }

                override fun onActivityDestroyed(p0: Activity) {
                    AppActivityManager.popActivity(p0)
                }
            })
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                isForeground = true
            }

            Lifecycle.Event.ON_STOP -> {
                isForeground = false
            }

            else -> {
            }
        }
    }

}