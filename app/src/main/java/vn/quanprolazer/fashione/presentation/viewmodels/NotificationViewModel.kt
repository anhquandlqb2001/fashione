/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import vn.quanprolazer.fashione.domain.models.NotificationOverview
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository

class NotificationViewModel @AssistedInject constructor(
    private val notificationRepositoryFirestore: NotificationRepository,
    @Assisted val notificationOverviews: List<NotificationOverview>
) :
    ViewModel() {

//    private val _notificationType: MutableLiveData<Resource<List<NotificationOverview>>> by lazy {
//        val liveData = MutableLiveData<Resource<List<NotificationOverview>>>()
//        viewModelScope.launch {
//            liveData.value = notificationRepositoryFirestore.getNotificationTypes()
//        }
//        return@lazy liveData
//    }
//
//    val notificationType: LiveData<Resource<List<NotificationOverview>>> get() = _notificationType

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