package com.weyyam.tierfood

import android.app.Application
import com.google.firebase.FirebaseApp

class TierFoodApplicationClass : Application(){

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

}