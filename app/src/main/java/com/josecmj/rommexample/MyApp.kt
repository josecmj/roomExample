package com.josecmj.rommexample

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("ZECA","MyApp.onCreate()")
        FirebaseApp.initializeApp(this)
    }
}