/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.NotificationOrderStatus
import vn.quanprolazer.fashione.domain.models.NotificationOverview
import vn.quanprolazer.fashione.domain.models.NotificationTypeEnum
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository

class NotificationViewModel @AssistedInject constructor(
    private val notificationRepositoryFirestore: NotificationRepository,
    @Assisted val notificationOverviews: List<NotificationOverview>
) :
    ViewModel() {

    private val _notificationOrderStatus: MutableLiveData<Resource<List<NotificationOrderStatus>>> by lazy {
        val liveData = MutableLiveData<Resource<List<NotificationOrderStatus>>>()
        val notificationOrderStatusType =
            notificationOverviews.filter { it.type.name == NotificationTypeEnum.ORDER_STATUS }[0].type
        viewModelScope.launch {
            liveData.value =
                notificationRepositoryFirestore.getNotifications(notificationOrderStatusType.id)
        }
        return@lazy liveData
    }

    val notificationOrderStatus: LiveData<Resource<List<NotificationOrderStatus>>> get() = _notificationOrderStatus

    private val _navigateToExtendNotification: MutableLiveData<NotificationTypeEnum> by lazy { MutableLiveData() }
    val navigateToExtendNotification: LiveData<NotificationTypeEnum> get() = _navigateToExtendNotification

    fun onNavigateToExtendNotification(type: NotificationTypeEnum) {
        _navigateToExtendNotification.value = type
    }

    fun doneNavigateToExtendNotification() {
        _navigateToExtendNotification.value = null
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(notificationOverviews: List<NotificationOverview>): NotificationViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, notificationOverviews: List<NotificationOverview>
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(notificationOverviews) as T
            }
        }
    }
}