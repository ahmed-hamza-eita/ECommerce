package com.hamza.ecommerce

import android.annotation.SuppressLint
import android.app.Application
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        listenNetwork()

    }

    @SuppressLint("CheckResult")
    private fun listenNetwork() {
        ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe { isConnected: Boolean ->
                FirebaseCrashlytics.getInstance().setCustomKey("connected_to_internet", isConnected)
            }
    }
}