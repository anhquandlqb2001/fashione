/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.NotificationService
import vn.quanprolazer.fashione.domain.models.NotificationType
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val notificationServiceFirestore: NotificationService) :
    NotificationRepository {
    override suspend fun getNotificationTypes(): Resource<List<NotificationType>> {
        return try {
            val response = notificationServiceFirestore.getNotificationTypes()
            return Resource.Success(response.map { it.toDomainModel() })
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}