package com.example.composetutorial

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.composetutorial.ui.theme.ComposeTutorialTheme


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var db: ContactDataBase
    private lateinit var sensorActivity: SensorActivity
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channelId",
                "Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        db = Room.databaseBuilder(
            applicationContext,
            ContactDataBase::class.java, "database-name"
        ).build()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorActivity = SensorActivity(this, sensorManager)

        setContent {
            ComposeTutorialTheme {
                navController = rememberNavController()
                Navigation(navController = navController, dataBase = db)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorActivity.unregister()
    }
}