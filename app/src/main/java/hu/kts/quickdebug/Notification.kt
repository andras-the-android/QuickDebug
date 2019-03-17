package hu.kts.quickdebug

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notification(private val context: Context) {

    private val notificationManager: NotificationManager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

    fun show() {

        val intent = Intent(context, BackgroundService::class.java)
        val pendingIntent = PendingIntent.getService(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_ONE_SHOT)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle(context.getString(R.string.notification_title))
            setContentText(context.getString(R.string.notification_text))
            setContentIntent(pendingIntent)
            setSmallIcon(R.drawable.ic_adb_black_24dp)
            setOngoing(true)
            setShowWhen(false)
        }

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID,  builder.build())
    }

    fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, context.getString(R.string.notification_channel_name), NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }

    fun cancel() {
        notificationManager.cancelAll()
    }

    companion object {
        const val CHANNEL_ID = "quickDebugChannelId"
        const val NOTIFICATION_ID = 52627

    }

}