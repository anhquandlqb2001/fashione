/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.data.network.models.NetworkNotificationTypeFirestore

class NotificationServiceImpl : NotificationService {
    override suspend fun getNotificationTypes(): List<NetworkNotificationTypeFirestore> {
        val db = FirebaseFirestore.getInstance()
        return db.collection("notification_types").get()
            .await().documents.mapNotNull { it.toObject(NetworkNotificationTypeFirestore::class.java) }
    }
}