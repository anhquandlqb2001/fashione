/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import kotlinx.coroutines.flow.Flow
import vn.quanprolazer.fashione.data.network.models.NetworkNotificationExtend
import vn.quanprolazer.fashione.data.network.models.NetworkNotificationOrderStatus
import vn.quanprolazer.fashione.data.network.models.NetworkNotificationType

interface NotificationService {
    suspend fun getNotificationCount(userId: String): Flow<Int>

    suspend fun getNotificationTypes(): List<NetworkNotificationType>

    suspend fun getNotificationsOfOrderStatus(
        recipientId: String,
        notificationTypeId: String
    ): List<NetworkNotificationOrderStatus>

    suspend fun getNotificationsExtend(
        recipientId: String,
        notificationTypeId: String
    ): List<NetworkNotificationExtend>
}