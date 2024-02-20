package com.example.composetutorial

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlin.math.abs

class SensorActivity(private val context: Context, private val sensorManager: SensorManager) : SensorEventListener {
    private var oldYaw: Float = 0f
    private var oldPitch: Float = 0f
    private var oldRoll: Float = 0f

    init {
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR){
            val mRotationMatrix = FloatArray(9)
            val mRotationMatrixFromVector = FloatArray(9)
            val orientationValues = FloatArray(3)
            SensorManager.getRotationMatrixFromVector(mRotationMatrixFromVector, event.values)
            SensorManager.remapCoordinateSystem(mRotationMatrixFromVector,
                    SensorManager.AXIS_X, SensorManager.AXIS_Z,
                    mRotationMatrix)
            SensorManager.getOrientation(mRotationMatrix, orientationValues)

            orientationValues[0] = Math.toDegrees(orientationValues[0].toDouble()).toFloat()
            orientationValues[1] = Math.toDegrees(orientationValues[1].toDouble()).toFloat()
            orientationValues[2] = Math.toDegrees(orientationValues[2].toDouble()).toFloat()

            if (abs(orientationValues[0] - oldYaw) > 30){
                oldYaw = orientationValues[0]
                notification(context, "Yaw: " + orientationValues[0])
            }
            if (abs(orientationValues[1] - oldPitch) > 30){
                oldPitch = orientationValues[1]
                notification(context, "Pitch: " + orientationValues[0])
            }
            if (abs(orientationValues[2] - oldRoll) > 30){
                oldRoll = orientationValues[2]
                notification(context, "Roll: " + orientationValues[0])
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    private fun notification(context: Context, string: String){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val builder = NotificationCompat.Builder(context, "channelId")
                .setContentTitle("Rotating...")
                .setContentText(string)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            notificationManager.notify(1, builder)
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }
}