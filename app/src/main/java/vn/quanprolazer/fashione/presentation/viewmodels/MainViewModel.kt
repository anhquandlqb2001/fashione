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
import vn.quanprolazer.fashione.domain.models.NotificationOverviewResponse
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) :
    ViewModel() {

    private val _navigateToNotification: MutableLiveData<List<NotificationOverview>> by lazy { MutableLiveData() }
    val navigateToNotification: LiveData<List<NotificationOverview>> get() = _navigateToNotification

    fun onClickNavigateToNotification() {
        if (_notificationOverview.value is Resource.Success) {
            _navigateToNotification.value = (_notificationOverview.value as Resource.Success<NotificationOverviewResponse>).data.notifications
        }
    }

    fun doneNavigateToNotification() {
        _navigateToNotification.value = null
    }

    private val _notificationOverview: MutableLiveData<Resource<NotificationOverviewResponse>> by lazy {
        MutableLiveData(
            Resource.Loading(null)
        )
    }
    val notificationOverview: LiveData<Resource<NotificationOverviewResponse>> get() = _notificationOverview

    fun fetchNotification() {
        viewModelScope.launch {
            _notificationOverview.value = notificationRepository.getNotificationTypes()
        }
    }

}