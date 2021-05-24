/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.helpers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.presentation.ui.MainActivity
import java.util.concurrent.atomic.AtomicInteger

object FashioneNotification {

    fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {


        // TODO: Step 1.11 create intent
        val contentIntent = Intent(applicationContext, MainActivity::class.java)

        // TODO: Step 1.12 create PendingIntent
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NotificationID.iD,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // TODO: You can add style here


        // TODO: Step 1.2 get an instance of NotificationCompat.Builder

        // Build the notification
        val builder = NotificationCompat.Builder(
            applicationContext,
//             TODO: Step 1.8 use a notification channel
            applicationContext.getString(R.string.app_notification_channel_id)
        )
//             TODO: Step 1.3 set title, text and icon to builder
            .setSmallIcon(R.drawable.baseline_notifications_none_black_24dp)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(messageBody)
            // TODO: Step 1.13 set content intent
            .setContentIntent(contentPendingIntent)

            // TODO: Step 2.5 set priority
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // TODO Step 1.4 call notify
        // Deliver the notification
        notify(NotificationID.iD, builder.build())
    }

// TODO: Step 1.14 Cancel all notifications
    /**
     * Cancels all notifications.
     *
     */
    fun NotificationManager.cancelNotifications() {
        cancelAll()
    }
}

object NotificationID {
    private val c: AtomicInteger = AtomicInteger(0)
    val iD: Int
        get() = c.incrementAndGet()
}