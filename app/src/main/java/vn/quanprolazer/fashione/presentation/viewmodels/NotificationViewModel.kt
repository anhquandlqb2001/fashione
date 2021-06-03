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
import vn.quanprolazer.fashione.domain.models.NotificationOrderStatus
import vn.quanprolazer.fashione.domain.models.NotificationOverviewResponse
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) :
    ViewModel() {

    private val _notificationOrderStatus: MutableLiveData<Resource<List<NotificationOrderStatus>>> by lazy { MutableLiveData() }
    val notificationOrderStatus: LiveData<Resource<List<NotificationOrderStatus>>> get() = _notificationOrderStatus

    fun fetchNotificationsOrderStatus(id: String) {
        viewModelScope.launch {
            _notificationOrderStatus.value =
                notificationRepository.getNotificationsOfOrderStatus(id)
        }
    }

    private val _notificationOverview: MutableLiveData<Resource<NotificationOverviewResponse>> by lazy {
        val liveData =
            MutableLiveData<Resource<NotificationOverviewResponse>>(Resource.Loading)
        viewModelScope.launch {
            liveData.value = notificationRepository.getNotificationTypes()
        }
        return@lazy liveData
    }
    val notificationOverview: LiveData<Resource<NotificationOverviewResponse>> get() = _notificationOverview

    private val _navigateToExtendNotification: MutableLiveData<String> by lazy { MutableLiveData() }
    val navigateToExtendNotification: LiveData<String> get() = _navigateToExtendNotification

    fun onNavigateToExtendNotification(typeId: String) {
        _navigateToExtendNotification.value = typeId
    }

    fun doneNavigateToExtendNotification() {
        _navigateToExtendNotification.value = null
    }
}