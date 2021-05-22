/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.retrofits.NotificationService
import vn.quanprolazer.fashione.domain.models.NotificationOverview
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationService: NotificationService,
    private val userRepository: UserRepository
) :
    NotificationRepository {
    override suspend fun getNotificationTypes(): Resource<List<NotificationOverview>> {
        val token = withContext(Dispatchers.Default) {
            userRepository.getToken()
        } ?: return Resource.Error(Exception("Token not found"))

        return try {
            val response = withContext(Dispatchers.Default) {
                notificationService.getNotificationOverview(token)
            }
            Resource.Success(response.map { it.toDomainModel() })
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}