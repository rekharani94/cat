package me.intuit.cat

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.work.Constraints
import androidx.work.NetworkType
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import androidx.work.*
import me.intuit.cat.presentation.synchonization.CustomWorkerFactory
import java.time.Duration
import javax.inject.Inject

@HiltAndroidApp
class Application() : Application(),Configuration.Provider {

    @Inject
    lateinit var workerFactory : CustomWorkerFactory


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

       WorkManager.initialize(applicationContext, workManagerConfiguration)
    }


}