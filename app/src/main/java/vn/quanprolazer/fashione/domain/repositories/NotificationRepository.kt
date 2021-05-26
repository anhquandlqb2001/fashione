/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import vn.quanprolazer.fashione.domain.models.NotificationOrderStatus
import vn.quanprolazer.fashione.domain.models.NotificationOverviewResponse
import vn.quanprolazer.fashione.domain.models.NotificationExtend
import vn.quanprolazer.fashione.domain.models.Resource

interface NotificationRepository {
    suspend fun getNotificationTypes(): Resource<NotificationOverviewResponse>
    
    suspend fun refreshNotificationTypes()

    suspend fun getNotificationsOfOrderStatus(notificationTypeId: String): Resource<List<NotificationOrderStatus>>

    suspend fun getNotificationsExtend(notificationTypeId: String): Resource<List<NotificationExtend>>
}