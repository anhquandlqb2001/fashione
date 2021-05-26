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
import vn.quanprolazer.fashione.domain.models.NotificationOrderStatus
import vn.quanprolazer.fashione.domain.models.NotificationOverviewResponse
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationService: NotificationService,
    private val notificationServiceFirestore: vn.quanprolazer.fashione.data.network.services.firestores.NotificationService,
    private val userRepository: UserRepository
) :
    NotificationRepository {
    override suspend fun getNotificationTypes(): Resource<NotificationOverviewResponse> {
        return try {
            val token = withContext(Dispatchers.Default) {
                userRepository.getToken()
            }
            if (token.isNullOrBlank()) {
                return Resource.Error(Exception("Token is null or blank"))
            }
            val response = withContext(Dispatchers.Default) {
                notificationService.getNotificationOverview(token)
            }
            Resource.Success(
                NotificationOverviewResponse(
                    notifications = response.notifications.map { it.toDomainModel() },
                    total = response.total
                )
            )
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getNotifications(notificationTypeId: String): Resource<List<NotificationOrderStatus>> {
        val user = userRepository.getUser().value ?: return Resource.Error(Exception("Not login yet"))

        return try {
            val response = withContext(Dispatchers.Default) {
                notificationServiceFirestore.getNotifications(
                    user.uid,
                    notificationTypeId = notificationTypeId
                ).map { it.toDomainModel() }
            }
            Timber.i(response.toString())
            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}