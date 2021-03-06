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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private val _navigateToNotification: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val navigateToNotification: LiveData<Boolean> get() = _navigateToNotification


    fun onClickNavigateToNotification() {
        _navigateToNotification.value = true
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

    private val _cartItemCount: MutableLiveData<Resource<Int>> by lazy { MutableLiveData<Resource<Int>>() }
    val cartItemCount: LiveData<Resource<Int>> get() = _cartItemCount

    fun fetchCartItemCount() {
        _cartItemCount.value = Resource.Loading
        viewModelScope.launch {
            cartRepository.getCartItemCount().catch { e -> Timber.e(e) }
                .collect { _cartItemCount.value = it }
        }
    }

    private val _notificationCount: MutableLiveData<Resource<Int>> by lazy { MutableLiveData<Resource<Int>>() }
    val notificationCount: LiveData<Resource<Int>> get() = _notificationCount

    fun fetchNotificationCount() {
        _notificationCount.value = Resource.Loading
        viewModelScope.launch {
            notificationRepository.getNotificationCount().catch { e -> Timber.e(e) }
                .collect { _notificationCount.value = it }
        }
    }
}