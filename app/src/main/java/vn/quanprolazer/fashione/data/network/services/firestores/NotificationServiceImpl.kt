/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.data.network.models.NetworkNotificationOrderStatus
import vn.quanprolazer.fashione.data.network.models.NetworkNotificationExtend
import vn.quanprolazer.fashione.data.network.models.NetworkNotificationType

class NotificationServiceImpl : NotificationService {
    override suspend fun getNotificationTypes(): List<NetworkNotificationType> {
        val db = FirebaseFirestore.getInstance()
        return db.collection("notification_types").get()
            .await().documents.mapNotNull { it.toObject(NetworkNotificationType::class.java) }
    }

    override suspend fun getNotificationsOfOrderStatus(
        recipientId: String,
        notificationTypeId: String
    ): List<NetworkNotificationOrderStatus> {
        val db = FirebaseFirestore.getInstance()
        return db.collection("notifications").whereEqualTo("recipient_id", recipientId)
            .whereEqualTo("type_id", notificationTypeId)
            .orderBy("created_at", Query.Direction.DESCENDING).get()
            .await().documents.mapNotNull { it.toObject(NetworkNotificationOrderStatus::class.java) }
    }

    override suspend fun getNotificationsExtend(
        recipientId: String,
        notificationTypeId: String
    ): List<NetworkNotificationExtend> {
        val db = FirebaseFirestore.getInstance()
        return db.collection("notifications").whereEqualTo("recipient_id", recipientId)
            .whereEqualTo("type_id", notificationTypeId)
            .orderBy("created_at", Query.Direction.DESCENDING).get()
            .await().documents.mapNotNull { it.toObject(NetworkNotificationExtend::class.java) }
    }
}