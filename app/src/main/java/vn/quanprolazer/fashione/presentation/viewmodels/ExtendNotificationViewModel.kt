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
import vn.quanprolazer.fashione.domain.models.NotificationExtend
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository

class ExtendNotificationViewModel @AssistedInject constructor(
    private val notificationRepositoryFirestore: NotificationRepository,
    @Assisted private val notificationTypeId: String
) :
    ViewModel() {

    private val _notificationsOfPromotion: MutableLiveData<Resource<List<NotificationExtend>>> by lazy {
        val liveData = MutableLiveData<Resource<List<NotificationExtend>>>()
        viewModelScope.launch {
            liveData.value =
                notificationRepositoryFirestore.getNotificationsExtend(
                    notificationTypeId
                )
        }
        return@lazy liveData
    }
    val notificationsOfPromotion: LiveData<Resource<List<NotificationExtend>>> get() = _notificationsOfPromotion

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(notificationTypeId: String): ExtendNotificationViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, notificationTypeId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(notificationTypeId) as T
            }
        }
    }
}