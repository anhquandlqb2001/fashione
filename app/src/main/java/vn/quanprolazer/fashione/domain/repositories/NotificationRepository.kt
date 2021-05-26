/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import vn.quanprolazer.fashione.domain.models.NotificationOrderStatus
import vn.quanprolazer.fashione.domain.models.NotificationOverviewResponse
import vn.quanprolazer.fashione.domain.models.Resource

interface NotificationRepository {
    suspend fun getNotificationTypes(): Resource<NotificationOverviewResponse>

    suspend fun getNotifications(notificationTypeId: String): Resource<List<NotificationOrderStatus>>
}