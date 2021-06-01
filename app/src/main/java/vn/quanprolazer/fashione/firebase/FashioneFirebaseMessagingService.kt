/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.firebase

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import vn.quanprolazer.fashione.helpers.FashioneNotification.sendNotification

class FashioneFirebaseMessagingService : FirebaseMessagingService() {

    //this is called when a message is received
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Check if message contains a notification payload, send notification
        remoteMessage.notification?.let {
            it.body?.let { it1 -> sendNotification(it1) }
        }

    }

    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcmToken", token).apply();
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcmToken", "empty")
    }
}