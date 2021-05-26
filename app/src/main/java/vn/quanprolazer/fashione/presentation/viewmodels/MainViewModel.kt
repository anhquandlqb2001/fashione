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
import vn.quanprolazer.fashione.domain.repositories.CartRepository
import vn.quanprolazer.fashione.domain.repositories.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val cartRepository: CartRepository
) :
    ViewModel() {

    private val _navigateToNotification: MutableLiveData<List<NotificationOverview>> by lazy { MutableLiveData() }
    val navigateToNotification: LiveData<List<NotificationOverview>> get() = _navigateToNotification

    private val _cartItemCount: MutableLiveData<Resource<Int>> by lazy { MutableLiveData<Resource<Int>>() }
    val cartItemCount: LiveData<Resource<Int>> get() = _cartItemCount

    fun onClickNavigateToNotification() {
        if (_notificationOverview.value is Resource.Success) {
            _navigateToNotification.value =
                (_notificationOverview.value as Resource.Success<NotificationOverviewResponse>).data.notifications
        }
    }

    fun doneNavigateToNotification() {
        _navigateToNotification.value = null
    }

    private val _navigateToCart: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val navigateToCart: LiveData<Boolean> get() = _navigateToCart

    fun onClickNavigateToCart() {
        _navigateToCart.value = true
    }

    fun doneNavigateToCart() {
        _navigateToCart.value = null
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

    fun fetchCartItemCount() {
        _cartItemCount.value = Resource.Loading(null)
        viewModelScope.launch {
            _cartItemCount.value = cartRepository.getCartItemCount()
        }
    }
}