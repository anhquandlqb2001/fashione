/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.NetworkNotificationType

interface NotificationService {
    suspend fun getNotificationTypes(): List<NetworkNotificationType>
}