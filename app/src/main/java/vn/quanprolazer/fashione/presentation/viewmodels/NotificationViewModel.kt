/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.NotificationOverview
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val notificationRepositoryFirestore: NotificationRepository) :
    ViewModel() {

    private val _notificationType: MutableLiveData<Resource<List<NotificationOverview>>> by lazy {
        val liveData = MutableLiveData<Resource<List<NotificationOverview>>>()
        viewModelScope.launch {
            liveData.value = notificationRepositoryFirestore.getNotificationTypes()
        }
        return@lazy liveData
    }

    val notificationType: LiveData<Resource<List<NotificationOverview>>> get() = _notificationType
}