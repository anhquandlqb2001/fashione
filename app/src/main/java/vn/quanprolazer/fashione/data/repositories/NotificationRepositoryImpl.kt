/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.data.database.dao.NotificationOverviewDao
import vn.quanprolazer.fashione.data.database.models.NotificationOverviewEntity
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.retrofits.NotificationService
import vn.quanprolazer.fashione.domain.models.NotificationExtend
import vn.quanprolazer.fashione.domain.models.NotificationOrderStatus
import vn.quanprolazer.fashione.domain.models.NotificationOverviewResponse
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationServiceRetrofit: NotificationService,
    private val notificationServiceFirestore: vn.quanprolazer.fashione.data.network.services.firestores.NotificationService,
    private val userRepository: UserRepository,
    private val notificationOverviewDao: NotificationOverviewDao
) :
    NotificationRepository {
    override suspend fun getNotificationCount(): Flow<Resource<Int>> =
        notificationServiceFirestore.getNotificationCount(userRepository.getUser().value?.uid!!)
            .map { Resource.Success(it) }

    override suspend fun getNotificationTypes(): Resource<NotificationOverviewResponse> {
        if (userRepository.getUser().value == null) return Resource.Error(Exception("NOT_LOGIN"))
        try {
            refreshNotificationTypes()
        } catch (e: Exception) {
            Timber.e(e)
        }

        val notification = withContext(Dispatchers.Default) {
            notificationOverviewDao.loadNotificationOverviews()
        }
        return Resource.Success(
            NotificationOverviewResponse(
                notifications = notification.notifications, total = notification.total
            )
        )
    }

    override suspend fun refreshNotificationTypes() {
        val user = userRepository.getUser().value
        try {
            withContext(Dispatchers.Default) {
                val freshNotifications =
                    user?.let { notificationServiceRetrofit.getNotificationOverview(it.uid) }
                if (freshNotifications != null) {
                    notificationOverviewDao.save(
                        NotificationOverviewEntity(
                            id = Long.MAX_VALUE,
                            notifications = freshNotifications.notifications.map { it.toDomainModel() },
                            total = freshNotifications.total
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getNotificationsOfOrderStatus(notificationTypeId: String): Resource<List<NotificationOrderStatus>> {
        val user =
            userRepository.getUser().value ?: return Resource.Error(Exception("NOT_LOGIN"))

        return try {
            val response = withContext(Dispatchers.Default) {
                notificationServiceFirestore.getNotificationsOfOrderStatus(
                    user.uid,
                    notificationTypeId = notificationTypeId
                ).map { it.toDomainModel() }
            }
            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getNotificationsExtend(notificationTypeId: String): Resource<List<NotificationExtend>> {
        val user =
            userRepository.getUser().value ?: return Resource.Error(Exception("NOT_LOGIN"))

        return try {
            val response = withContext(Dispatchers.Default) {
                notificationServiceFirestore.getNotificationsExtend(
                    user.uid,
                    notificationTypeId = notificationTypeId
                ).map { it.toDomainModel() }
            }
            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}