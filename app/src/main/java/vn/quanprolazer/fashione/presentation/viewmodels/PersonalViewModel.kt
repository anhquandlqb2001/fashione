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
import vn.quanprolazer.fashione.domain.models.DeliveryStatus
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.FirebaseUserLiveData
import vn.quanprolazer.fashione.domain.repositories.OrderRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository
) :
    ViewModel() {

    val user: FirebaseUserLiveData by lazy {
        userRepository.getUser()
    }

    private val _navigateToPurchaseMenu: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val navigateToPurchaseMenu: LiveData<Boolean> get() = _navigateToPurchaseMenu

    fun onNavigateToPurchaseMenu() {
        _navigateToPurchaseMenu.value = true
    }

    fun doneNavigateToPurchaseMenu() {
        _navigateToPurchaseMenu.value = null
    }

    private val _deliveryStatus: MutableLiveData<Resource<List<DeliveryStatus>>> by lazy {
        val liveData = MutableLiveData<Resource<List<DeliveryStatus>>>(Resource.Loading(null))
        viewModelScope.launch {
            liveData.value = orderRepository.getDeliveryStatus()
        }
        return@lazy liveData
    }

    val deliveryStatus: LiveData<Resource<List<DeliveryStatus>>> get() = _deliveryStatus
}