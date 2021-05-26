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
import timber.log.Timber
import vn.quanprolazer.fashione.domain.models.NotificationOrderStatus
import vn.quanprolazer.fashione.domain.models.NotificationOverview
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository

class NotificationViewModel @AssistedInject constructor(
    private val notificationRepositoryFirestore: NotificationRepository,
    @Assisted val notificationOverviews: List<NotificationOverview>
) :
    ViewModel() {

    private val _notificationOrderStatusType:  MutableLiveData<Resource<List<NotificationOrderStatus>>> by lazy {
        val liveData = MutableLiveData<Resource<List<NotificationOrderStatus>>>()
        viewModelScope.launch {
            liveData.value = notificationRepositoryFirestore.getNotifications("H9eDDgrDU61q9zZr49TS")
        }
        return@lazy liveData
    }

    val notificationOrderStatusType: LiveData<Resource<List<NotificationOrderStatus>>> get() = _notificationOrderStatusType

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